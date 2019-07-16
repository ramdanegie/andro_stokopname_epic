package com.inhuman.scanner.stokopname;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.inhuman.scanner.stokopname.Class.DialogPilihRuangan;
import com.inhuman.scanner.stokopname.Model.SpinnerRuangan;
import com.inhuman.scanner.stokopname.Model.StokProduk;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView homeCard,profileCard,stokCard,settingCard,scannerCard;
    public static Spinner spinner_ruangan;
    AlertDialog alertDialogRuangan;
    TextInputLayout etRuangan;
    public static final String EXTRA_ID_RUANGAN  = "com.inhuman.scanner.stokopname.EXTRA_ID_RUANGAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()){
            case R.id.home_card : i = new Intent(this,HomeActivity.class) ;startActivity(i); break;
            case R.id.profile_card : i = new Intent(this,ProfileActivity.class) ; startActivity(i) ;break;
            case R.id.stok_ruangan_card : i = new Intent(this,StokActivity.class) ;startActivity(i); break;
            case R.id.setting_card : i = new Intent(this,SettingActivity.class) ;startActivity(i); break;
//            case R.id.scanner_card : i = new Intent(this,ScannerActivity.class) ;
//                startActivity(i);
//                showDialogRuangan();
//                break;
            case R.id.scanner_card :
                showDialogRuangan();
                break;
            default:break;
        }
    }
    private  void showDialogRuangan ( ){
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
        spinner_ruangan =  view.findViewById(R.id.spinner_ruangan);
        fetchJSON();
//          builder.create();
        AlertDialog alertDialog = builder.create();
        // show it
        alertDialog.show();

    }
    public void getSelectedRuangan (View v){
        SpinnerRuangan ruangan = (SpinnerRuangan) spinner_ruangan.getSelectedItem();
        Toasty.info(this, "Ruangan " + ruangan.getNamaruangan().toString() +" dipilih",
                Toast.LENGTH_SHORT, true).show();
//        Toast.makeText(this,"Ruangan " + ruangan.getNamaruangan().toString() +" dipilih" ,Toast.LENGTH_SHORT).show();
        openScannerActivity(ruangan.getId());
    }

    private void openScannerActivity(int idRuangan) {
        Intent intent = new Intent(this,ScannerActivity.class);
        intent.putExtra(EXTRA_ID_RUANGAN,idRuangan);
        startActivity(intent);
    }

    public void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Call<List<SpinnerRuangan>> call = serviceApi.getComboRuangan();
        call.enqueue(new Callback<List<SpinnerRuangan>>() {
            @Override
            public void onResponse(Call<List<SpinnerRuangan>> call, Response<List<SpinnerRuangan>> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(getApplicationContext(), "Data Tidak ditemukan",
                            Toast.LENGTH_SHORT, true).show();
                    return;
                }

                List<SpinnerRuangan> listRuangan = new ArrayList<>();
                List<SpinnerRuangan> arrays =  response.body();

                for (SpinnerRuangan array : arrays){
                    SpinnerRuangan ruangan = new SpinnerRuangan(array.getId(),array.getNamaruangan());
                    listRuangan.add(ruangan);
                }

                ArrayAdapter<SpinnerRuangan> adapter = new ArrayAdapter<SpinnerRuangan>(MainActivity.this  ,android.R.layout.simple_spinner_item,listRuangan);
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
}
