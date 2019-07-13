package com.inhuman.scanner.dashboad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.inhuman.scanner.dashboad.Model.StokProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class ScannerActivity extends AppCompatActivity {

    public static TextView resultTextView ;
    public static TextView resultTextViewArray ;
    FloatingActionButton scan_btn;
    public static FloatingActionButton save_button;
    public static RecyclerView recyclerView;
    private ArrayList<StokProduk> data;
    private StokProdukAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        resultTextView = (TextView)findViewById(R.id.result_text);
//        resultTextViewArray = (TextView)findViewById(R.id.result_text_view);
        scan_btn = (FloatingActionButton) findViewById(R.id.btn_scan);
        save_button = (FloatingActionButton) findViewById(R.id.btn_save);

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


    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.recycle_view_produk);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
//        loadJSON();
    }
}
