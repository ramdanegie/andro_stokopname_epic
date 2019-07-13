package com.inhuman.scanner.dashboad;

import android.widget.TextView;

import com.inhuman.scanner.dashboad.Model.StokProduk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {
    String  token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbi5pdCJ9.yA2GELoqmY5D9XcBeQxStR2-G_VzTZLw71iQTX_WKTa3cNQ5SwmpjstC6E0NbDZF8IENQFMy_u23ya2vv7lG-g";
    public static String baseUrlApi = "http://36.89.61.226:8888/service/";

    @Headers({
            "X-AUTH-TOKEN: " + token,
            "Content-Type: application/json"
    })
    @GET("transaksi/logistik-stok/get-stok-ruangan-so-andro?")
    Call<List<StokProduk>> getStokProdukSo(@Query("ruanganfk") String ruanganfk, @Query("produkfk") String produkfk);
}
