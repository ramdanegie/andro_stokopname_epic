package com.inhuman.scanner.stokopname;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inhuman.scanner.stokopname.Adapter.ProdukAdapter;
import com.inhuman.scanner.stokopname.Model.Produk;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

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

public class StokActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    public static final int EDIT_MASTER_PRODUK = 1;
    public static RecyclerView recyclerViewProduk;
    ProdukAdapter apaterProduk;
    List<Produk> listProduk = new ArrayList<Produk>();
    public static ProgressBar produkProgressBarProduk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        setTitle("Master Produk");
        produkProgressBarProduk = (ProgressBar)findViewById(R.id.produkProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadDataProduk("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });
        loadRecycler();
        loadDataProduk("");
        apaterProduk.setOnItemClickListener(new ProdukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produk produk) {
                Intent intent = new Intent( StokActivity.this, EditProdukActivity.class);
                intent.putExtra(EditProdukActivity.EXTRA_EDIT_PRODUK_KDPRODUK,produk.getKodeProduk());
                intent.putExtra(EditProdukActivity.EXTRA_EDIT_PRODUK_NMPRODUK,produk.getNamaProduk());
                intent.putExtra(EditProdukActivity.EXTRA_EDIT_PRODUK_BARCODE,produk.getKdBarcode());
                intent.putExtra(EditProdukActivity.EXTRA_EDIT_PRODUK_SATUAN,produk.getSatuanStandar());
                intent.putExtra(EditProdukActivity.EXTRA_EDIT_PRODUK_DETAIL,produk.getDetailJenisProduk());
                startActivity(intent);
            }
        });

    }

    private void loadRecycler() {
        recyclerViewProduk = (RecyclerView)findViewById(R.id.produkRecyclerView);
        recyclerViewProduk.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProduk.setLayoutManager(layoutManager);

        apaterProduk = new ProdukAdapter(listProduk);
        recyclerViewProduk.setAdapter(apaterProduk);
    }

    private void loadDataProduk(String produk) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        produkProgressBarProduk.setVisibility(View.VISIBLE);
        recyclerViewProduk.setVisibility(View.GONE);

        String token = Preferences.getTokenLogin(getApplicationContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-AUTH-TOKEN", token);

        Call<List<Produk>> call = serviceApi.getMasterProduk(headers,produk);
        call.enqueue(new Callback<List<Produk>>() {
            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(getApplicationContext(), "Data Tidak Ditemukan",
                            Toast.LENGTH_SHORT, true).show();
                    return;
                }

                List<Produk> arrays =  response.body();

                apaterProduk = new ProdukAdapter(arrays);
                recyclerViewProduk.setAdapter(apaterProduk);
                produkProgressBarProduk.setVisibility(View.GONE);
                recyclerViewProduk.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
                produkProgressBarProduk.setVisibility(View.GONE);
                recyclerViewProduk.setVisibility(View.VISIBLE);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.master_produk_menu,menu);

        MenuItem searchNamaProduk = menu.findItem(R.id.master_produk_action_search);
        SearchView searchView = (SearchView) searchNamaProduk.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDataProduk(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
              loadDataProduk("");
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
