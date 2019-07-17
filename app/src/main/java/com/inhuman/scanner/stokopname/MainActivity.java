package com.inhuman.scanner.stokopname;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarDrawerToggle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inhuman.scanner.stokopname.Class.DialogPilihRuangan;
import com.inhuman.scanner.stokopname.Model.LoginUser;
import com.inhuman.scanner.stokopname.Model.LogoutUser;
import com.inhuman.scanner.stokopname.Model.SpinnerRuangan;
import com.inhuman.scanner.stokopname.Model.StokProduk;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{
    private CardView homeCard, profileCard, stokCard, settingCard, scannerCard;
    public static Spinner spinner_ruangan;
    AlertDialog alertDialogRuangan;
    TextInputLayout etRuangan;
    public static final String EXTRA_ID_RUANGAN = "com.inhuman.scanner.stokopname.EXTRA_ID_RUANGAN";
    private DrawerLayout drawer;

    ProgressBar produkProgressBarHome;
    LinearLayout linearHomeView;

    TextView nav_tvNamaPegawai;
    TextView nav_tvNamaUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean statusLogin = Preferences.getLoggedInStatus(getApplicationContext());
        if (!statusLogin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        setContentView(R.layout.activity_main);


        produkProgressBarHome = findViewById(R.id.produkProgressBarHome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        homeCard = (CardView) findViewById(R.id.home_card);
        profileCard = (CardView) findViewById(R.id.profile_card);
        stokCard = (CardView) findViewById(R.id.stok_ruangan_card);
        settingCard = (CardView) findViewById(R.id.setting_card);
        scannerCard = (CardView) findViewById(R.id.scanner_card);

        homeCard.setOnClickListener(this);
        profileCard.setOnClickListener(this);
        stokCard.setOnClickListener(this);
        settingCard.setOnClickListener(this);
        scannerCard.setOnClickListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_widgets_white);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_tvNamaPegawai = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNamaPegawai);
        nav_tvNamaUser =(TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNamaUser);

        String jsonLogin = Preferences.getResultDataLogin(this);
        if(jsonLogin != ""){
            Gson g = new Gson();
            LoginUser p = g.fromJson(jsonLogin, LoginUser.class);
            nav_tvNamaPegawai.setText(p.getNamaPegawai());
            nav_tvNamaUser.setText(p.getNamaUser());
        }

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
           doLogout();
        } else if (id == R.id.nav_notif) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.home_card:
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.profile_card:
                i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.stok_ruangan_card:
                i = new Intent(this, StokActivity.class);
                startActivity(i);
                break;
            case R.id.setting_card:
                i = new Intent(this, SettingActivity.class);
                startActivity(i);
                break;
//            case R.id.scanner_card : i = new Intent(this,ScannerActivity.class) ;
//                startActivity(i);
//                showDialogRuangan();
//                break;
            case R.id.scanner_card:
                showDialogRuangan();
                break;
            default:
                break;
        }
    }

    private void showDialogRuangan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_ruangan, null);

        builder.setView(view)
                .setTitle("Pilih Ruangan")
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSelectedRuangan(view);
                    }
                });
        spinner_ruangan = view.findViewById(R.id.spinner_ruangan);
        fetchJSON();
//          builder.create();
        AlertDialog alertDialog = builder.create();
        // show it
        alertDialog.show();

    }

    public void getSelectedRuangan(View v) {
        SpinnerRuangan ruangan = (SpinnerRuangan) spinner_ruangan.getSelectedItem();
        Toasty.info(this, "Ruangan " + ruangan.getNamaruangan().toString() + " dipilih",
                Toast.LENGTH_SHORT, true).show();
//        Toast.makeText(this,"Ruangan " + ruangan.getNamaruangan().toString() +" dipilih" ,Toast.LENGTH_SHORT).show();
        openScannerActivity(ruangan.getId());
    }

    private void openScannerActivity(int idRuangan) {
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra(EXTRA_ID_RUANGAN, idRuangan);
        startActivity(intent);
    }

    public void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        String token = Preferences.getTokenLogin(getApplicationContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-AUTH-TOKEN", token);

        Call<List<SpinnerRuangan>> call = serviceApi.getComboRuangan(headers);
        call.enqueue(new Callback<List<SpinnerRuangan>>() {
            @Override
            public void onResponse(Call<List<SpinnerRuangan>> call, Response<List<SpinnerRuangan>> response) {
                if (!response.isSuccessful()) {
                    Toasty.warning(getApplicationContext(), "Data Tidak ditemukan",
                            Toast.LENGTH_SHORT, true).show();
                    return;
                }

                List<SpinnerRuangan> listRuangan = new ArrayList<>();
                List<SpinnerRuangan> arrays = response.body();
                if(arrays.size() == 0){
                    Toasty.warning(getApplicationContext(), "Belum ada mapping ruangan, Hubungi IT",
                            Toast.LENGTH_SHORT, true).show();
                }
                for (SpinnerRuangan array : arrays) {
                    SpinnerRuangan ruangan = new SpinnerRuangan(array.getId(), array.getNamaruangan());
                    listRuangan.add(ruangan);
                }

                ArrayAdapter<SpinnerRuangan> adapter = new ArrayAdapter<SpinnerRuangan>(MainActivity.this, android.R.layout.simple_spinner_item, listRuangan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ruangan.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<SpinnerRuangan>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void doLogout() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        LogoutUser data_send = new LogoutUser(
                Preferences.getLoggedInUser(getBaseContext())

        );
        produkProgressBarHome.setVisibility(View.VISIBLE);
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<LogoutUser> call = serviceApi.logoutUser(data_send);
        call.enqueue(new Callback<LogoutUser>() {
            @Override
            public void onResponse(Call<LogoutUser> call, Response<LogoutUser> response) {
                if (!response.isSuccessful()) {
                    Toasty.warning(MainActivity.this, "Logout Gagal",
                            Toast.LENGTH_SHORT, true).show();
                    produkProgressBarHome.setVisibility(View.GONE);
                    return;
                }
                Toasty.info(MainActivity.this, "Berhasi Logout",
                        Toast.LENGTH_SHORT, true).show();
                produkProgressBarHome.setVisibility(View.GONE);
                Preferences.setLoggedInStatus(getBaseContext(), false);
                Preferences.setResultDataLogin(getBaseContext(), "");
                Preferences.setTokenLogin(getBaseContext(),"");
                startActivity(new
                        Intent(getBaseContext(), LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<LogoutUser> call, Throwable t) {
                produkProgressBarHome.setVisibility(View.GONE);
                Toasty.error(MainActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
            }
        });


    }


}
