package com.inhuman.scanner.stokopname;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.inhuman.scanner.stokopname.Model.StokProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity {

    public static TextView resultTextView ;

    public static TextView textViewIdRuangan ;
    FloatingActionButton scan_btn;
    public static FloatingActionButton save_button;
    public static RecyclerView recyclerView;
    private ArrayList<StokProduk> data;
    private StokProdukAdapter adapter;
    public static FragmentManager fragmentManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Intent intent = getIntent();
        int idRuangan = intent.getIntExtra(MainActivity.EXTRA_ID_RUANGAN,0);

        resultTextView = (TextView)findViewById(R.id.result_text);
        textViewIdRuangan = (TextView)findViewById(R.id.text_view_idRuangan);
//        textViewIdRuangan.setText(idRuangan);

        scan_btn = (FloatingActionButton) findViewById(R.id.btn_scan);
        save_button = (FloatingActionButton) findViewById(R.id.btn_save);
        fragmentManager = getSupportFragmentManager();
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });
        initViews();
        String idR= String.valueOf(idRuangan);
        textViewIdRuangan.setText(idR);

    }
    public void loadListRecycle(String barcode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Call<List<StokProduk>> call = serviceApi.getStokProdukSo(textViewIdRuangan.getText().toString(),barcode);
        call.enqueue(new Callback<List<StokProduk>>() {
            @Override
            public void onResponse(Call<List<StokProduk>> call, Response<List<StokProduk>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<StokProduk> arrays =  response.body();

                for (StokProduk array : arrays){
//                   array.setKodeProduk("Kode : " + array.getKodeProduk());
                }

                adapter = new StokProdukAdapter(arrays);
                ScannerActivity.recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<StokProduk>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Data Tidak ditemukan", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view_produk);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

//        loadJSON();
    }
}
