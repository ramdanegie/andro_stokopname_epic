package com.inhuman.scanner.stokopname;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.inhuman.scanner.stokopname.Model.StokProduk;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity {
    public static StokProdukAdapter adapter;
    public static TextView resultTextView ;
    public static TextView textViewIdRuangan ;
    FloatingActionButton scan_btn;
    public static FloatingActionButton btnAdd;
    public static RecyclerView recyclerView;
    private ArrayList<StokProduk> data;
    List<StokProduk> listProduk = new ArrayList<StokProduk>();
    public static FragmentManager fragmentManager ;
    public static ProgressBar progressBar;
    TextInputLayout lbm_etBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Intent intent = getIntent();
        int idRuangan = intent.getIntExtra(MainActivity.EXTRA_ID_RUANGAN,0);
        setTitle("Daftar Produk");
        resultTextView = (TextView)findViewById(R.id.result_text);
        textViewIdRuangan = (TextView)findViewById(R.id.text_view_idRuangan);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        textViewIdRuangan.setText(idRuangan);

        scan_btn = (FloatingActionButton) findViewById(R.id.btn_scan);
        btnAdd = (FloatingActionButton) findViewById(R.id.btn_add);
        fragmentManager = getSupportFragmentManager();
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKode();
            }
        });
        initViews();
        String idR= String.valueOf(idRuangan);
        textViewIdRuangan.setText(idR);

    }

    private void showDialogKode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_barcode_manual, null);

        builder.setView(view)
                .setTitle("Masukan Barcode")
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Cari", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultTextView.setText(lbm_etBarcode.getEditText().getText().toString());
                        loadListRecycle(lbm_etBarcode.getEditText().getText().toString());
                    }
                });
        lbm_etBarcode =  view.findViewById(R.id.lbm_etBarcode);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void loadListRecycle(String barcode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Call<List<StokProduk>> call = serviceApi.getStokProdukSo(textViewIdRuangan.getText().toString(),barcode);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        call.enqueue(new Callback<List<StokProduk>>() {
            @Override
            public void onResponse(Call<List<StokProduk>> call, Response<List<StokProduk>> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(getApplicationContext(), "Data Tidak ditemukan",
                            Toast.LENGTH_SHORT, true).show();
//                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<StokProduk> arrays =  response.body();

                for (StokProduk array : arrays){
//                   array.setKodeProduk("Kode : " + array.getKodeProduk());
                }
                if(arrays.size() == 0){
                    Toasty.warning(getApplicationContext(), "Data Tidak ditemukan",
                            Toast.LENGTH_SHORT, true).show();

                }
                adapter = new StokProdukAdapter(arrays);
                ScannerActivity.recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<StokProduk>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view_produk);
        recyclerView.setHasFixedSize(true);
        adapter = new StokProdukAdapter(listProduk);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
//        loadJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scanner_menu,menu);

        MenuItem searchNamaProduk = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchNamaProduk.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
