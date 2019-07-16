package com.inhuman.scanner.stokopname;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.google.zxing.Result;
import com.inhuman.scanner.stokopname.Model.StokProduk;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private RecyclerView mList;

    private int CAMERA_PERMISSION_CODE = 1;

    ZXingScannerView scannerView;

    private RecyclerView recyclerView;
    private ArrayList<StokProduk> data;
    private StokProdukAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView =  new ZXingScannerView(this);

        if (ContextCompat.checkSelfPermission(CameraActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(CameraActivity.this, "You have already granted this permission!",
//                    Toast.LENGTH_SHORT).show();

            setContentView(scannerView);
        } else {
            requestStoragePermission();
        }

        setTitle("Scan Barcode");

        //DECLARE VAR
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CameraActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toasty.success(getApplicationContext(), "Permission DENIED",
                        Toast.LENGTH_SHORT, true).show();
                scannerView =  new ZXingScannerView(this);
                scannerView.getFlash();
                setContentView(scannerView);
            } else {
                Toasty.warning(getApplicationContext(), "Permission DENIED",
                        Toast.LENGTH_SHORT, true).show();
            }
        }
    }
    @Override
    public void handleResult(Result result) {
//        ScannerActivity.resultTextView.setVisibility(View.VISIBLE);
        ScannerActivity.resultTextView.setText(result.getText());

//        ScannerActivity.resultTextView = (TextView)findViewById(R.id.result_text);
        Toasty.info(getApplicationContext(), "Barcode : " +  result.getText(),
                Toast.LENGTH_SHORT, true).show();

        loadListRecycle(result.getText());
        onBackPressed();
    }

    public void loadListRecycle(String barcode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Call<List<StokProduk>> call = serviceApi.getStokProdukSo(ScannerActivity.textViewIdRuangan.getText().toString(),barcode);
        call.enqueue(new Callback<List<StokProduk>>() {
            @Override
            public void onResponse(Call<List<StokProduk>> call, Response<List<StokProduk>> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(getApplicationContext(), "Data Tidak Ditemukan",
                            Toast.LENGTH_SHORT, true).show();
                    return;
                }

                List<StokProduk> arrays =  response.body();

                for (StokProduk array : arrays){
//                   array.setKodeProduk("Kode : " + array.getKodeProduk());
                }
                ScannerActivity.adapter = new StokProdukAdapter(arrays);
                adapter = new StokProdukAdapter(arrays);
                ScannerActivity.recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<StokProduk>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();

            }
        });
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
