package com.inhuman.scanner.stokopname;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.inhuman.scanner.stokopname.Model.Produk;
import com.inhuman.scanner.stokopname.Model.StokOpnameDetail;
import com.inhuman.scanner.stokopname.Model.StokOpnamePost;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProdukActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_PRODUK_KDPRODUK  = "com.inhuman.scanner.stokopname.EXTRA_EDIT_PRODUK_KDPRODUK";
    public static final String EXTRA_EDIT_PRODUK_NMPRODUK  = "com.inhuman.scanner.stokopname.EXTRA_EDIT_PRODUK_NMPRODUK";
    public static final String EXTRA_EDIT_PRODUK_BARCODE  = "com.inhuman.scanner.stokopname.EXTRA_EDIT_PRODUK_BARCODE";
    public static final String EXTRA_EDIT_PRODUK_SATUAN = "com.inhuman.scanner.stokopname.EXTRA_EDIT_PRODUK_SATUAN";
    public static final String EXTRA_EDIT_PRODUK_DETAIL  = "com.inhuman.scanner.stokopname.EXTRA_EDIT_PRODUK_DETAIL";


    TextInputLayout etKodeProduk;
    TextInputLayout etNamaProduk;
    TextInputLayout etSatuan;
    TextInputLayout etDetail;
    public static TextInputLayout etBarcode;
    ImageView imgScanBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        setTitle("Edit Barcode Produk");

        etKodeProduk = findViewById(R.id.etKodeProduk);
        etNamaProduk = findViewById(R.id.etNamaProduk);
        etSatuan = findViewById(R.id.etSatuan);
        etDetail = findViewById(R.id.etDetailJenis);
        etBarcode = findViewById(R.id.etBarcode);
        imgScanBarcode= findViewById(R.id.img_scan_edit) ;
        imgScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CameraProdukActivity.class));
            }
        });

        Intent intent = getIntent();
        etKodeProduk.getEditText().setText(intent.getStringExtra(EXTRA_EDIT_PRODUK_KDPRODUK));
        etNamaProduk.getEditText().setText(intent.getStringExtra(EXTRA_EDIT_PRODUK_NMPRODUK));
        etBarcode.getEditText().setText(intent.getStringExtra(EXTRA_EDIT_PRODUK_BARCODE));
        etDetail.getEditText().setText(intent.getStringExtra(EXTRA_EDIT_PRODUK_DETAIL));
        etSatuan.getEditText().setText(intent.getStringExtra(EXTRA_EDIT_PRODUK_SATUAN));
        etBarcode.requestFocus();


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_master_produk_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_master_produk:
                saveProduk();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProduk() {
        String kdProduk = etKodeProduk.getEditText().getText().toString();
        String barcode = etBarcode.getEditText().getText().toString();

        if (barcode.trim().isEmpty() ) {
            Toasty.warning(getApplicationContext(), "Barcode belum di isi",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }


        Produk data_send = new Produk(
                kdProduk,
                "",
                "",
                barcode,
                ""
        );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = Preferences.getTokenLogin(getApplicationContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-AUTH-TOKEN", token);

        Log.d("json",data_send.toString() );
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<Produk> call = serviceApi.updateBarcodeProduk(headers,data_send);
        call.enqueue(new Callback<Produk>() {
            @Override
            public void onResponse(Call<Produk> call, Response<Produk> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(EditProdukActivity.this, "Gagal Menyimpan Data",
                            Toast.LENGTH_SHORT, true).show();
                    return;
                }

                Toasty.success(EditProdukActivity.this, "Sukses",
                        Toast.LENGTH_SHORT, true).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<Produk> call, Throwable t) {
                Toasty.error(EditProdukActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
            }
        });



    }
}
