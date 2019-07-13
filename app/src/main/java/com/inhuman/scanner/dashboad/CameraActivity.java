package com.inhuman.scanner.dashboad;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.google.zxing.Result;
import com.inhuman.scanner.dashboad.Model.StokProduk;
import com.inhuman.scanner.dashboad.Response.JsonResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private RecyclerView mList;


    ZXingScannerView scannerView;

    private RecyclerView recyclerView;
    private ArrayList<StokProduk> data;
    private StokProdukAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView =  new ZXingScannerView(this);
        setContentView(scannerView);

        //DECLARE VAR
    }



    @Override
    public void handleResult(Result result) {
//        ScannerActivity.resultTextView.setVisibility(View.VISIBLE);
        ScannerActivity.resultTextView.setText(result.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Call<List<StokProduk>> call = serviceApi.getStokProdukSo("556",result.getText());
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
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

}
