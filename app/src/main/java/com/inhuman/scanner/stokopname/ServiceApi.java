package com.inhuman.scanner.stokopname;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.inhuman.scanner.stokopname.Model.LoginUser;
import com.inhuman.scanner.stokopname.Model.LogoutUser;
import com.inhuman.scanner.stokopname.Model.Produk;
import com.inhuman.scanner.stokopname.Model.SpinnerRuangan;
import com.inhuman.scanner.stokopname.Model.StokOpnamePost;
import com.inhuman.scanner.stokopname.Model.StokProduk;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


import android.preference.PreferenceManager;

public interface ServiceApi {


    public static String baseUrlApi = "http://103.228.236.74:8888/service/";

    @GET("transaksi/logistik-stok/get-stok-ruangan-so-andro?")
    Call<List<StokProduk>> getStokProdukSo(
            @HeaderMap Map<String, String> headers,
            @Query("ruanganfk") String ruanganfk, @Query("produkfk") String produkfk);


    @POST("transaksi/logistik-stok/save-stock-opname")
    Call<StokOpnamePost> postStokOpname(
            @HeaderMap Map<String, String> headers, @Body StokOpnamePost stokOpnamePost);


    @GET("transaksi/logistik-stok/get-combo-ruangan-andro")
    Call<List<SpinnerRuangan>> getComboRuangan(@HeaderMap Map<String, String> headers);


    @GET("transaksi/andro/get-master-produk?")
    Call<List<Produk>> getMasterProduk(@HeaderMap Map<String, String> headers, @Query("namaproduk") String namaproduk);

    @POST("transaksi/andro/update-barcode-produk")
    Call<Produk> updateBarcodeProduk(@HeaderMap Map<String, String> headers, @Body Produk produk);


    @Headers({
            "Content-Type: application/json"
    })
    @POST("auth/sign-in-andro")
    Call<LoginUser> loginUser(@Body LoginUser loginUser);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("auth/sign-out")
    Call<LogoutUser> logoutUser(@Body LogoutUser logoutUser);

    @Headers({
            "Content-Type: application/json"
    })
    @POST("auth/sign-in")
    Call<ResponseBody> loginUserNew(@Body RequestBody body);

    @GET("transaksi/logging/save-log-all?")
    Call <ResponseBody> saveLogSO(
            @HeaderMap Map<String, String> headers,
            @Query("jenislog") String jenislog,
            @Query("referensi") String referensi,
            @Query("noreff") String noreff,
            @Query("keterangan") String keterangan);

}
