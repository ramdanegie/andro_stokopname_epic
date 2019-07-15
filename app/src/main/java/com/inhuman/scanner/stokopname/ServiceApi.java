package com.inhuman.scanner.stokopname;

import com.google.gson.JsonObject;
import com.inhuman.scanner.stokopname.Model.SpinnerRuangan;
import com.inhuman.scanner.stokopname.Model.StokOpnamePost;
import com.inhuman.scanner.stokopname.Model.StokProduk;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {
    String  token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbi5hcG90aWsifQ.3SPSaKTrOnKdr5sVnynYGTe14dKz9QN-z35tSaX_5YwLVHt_ZWyN0tlVuSg1dgeRC5Sg3cVwdbnYYtRgoHO86A";
    public static String baseUrlApi = "http://103.228.236.74:8888/service/";

    @Headers({
            "X-AUTH-TOKEN: " + token,
            "Content-Type: application/json"
    })
    @GET("transaksi/logistik-stok/get-stok-ruangan-so-andro?")
    Call<List<StokProduk>> getStokProdukSo(@Query("ruanganfk") String ruanganfk, @Query("produkfk") String produkfk);

    @Headers({
            "X-AUTH-TOKEN: " + token,
            "Content-Type: application/json"
    })
    @POST("transaksi/logistik-stok/save-stock-opname")
    Call<StokOpnamePost> postStokOpname(@Body StokOpnamePost stokOpnamePost);

    @Headers({
            "X-AUTH-TOKEN: " + token,
            "Content-Type: application/json"
    })
    @GET("transaksi/logistik-stok/get-combo-ruangan-andro")
    Call<List<SpinnerRuangan>> getComboRuangan();
}
