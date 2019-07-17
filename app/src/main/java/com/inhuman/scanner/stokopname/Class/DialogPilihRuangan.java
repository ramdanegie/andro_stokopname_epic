package com.inhuman.scanner.stokopname.Class;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.StokProdukAdapter;
import com.inhuman.scanner.stokopname.MainActivity;
import com.inhuman.scanner.stokopname.Model.SpinnerRuangan;
import com.inhuman.scanner.stokopname.Model.StokProduk;
import com.inhuman.scanner.stokopname.R;
import com.inhuman.scanner.stokopname.ScannerActivity;
import com.inhuman.scanner.stokopname.ServiceApi;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogPilihRuangan extends AppCompatDialogFragment {
    public static Spinner spinner_ruangan;
    private List<SpinnerRuangan> listRuangan = new ArrayList<>();
//    private ArrayList<SpinnerRuangan> ruanganArrayList;
//    private ArrayList<String> namaRuangan = new ArrayList<String>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_ruangan, null);

        builder.setView(view)
                .setTitle("Pilih Ruangan")
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        spinner_ruangan =  view.findViewById(R.id.spinner_ruangan);
        fetchJSON();
        return  builder.create();
    }

    private void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        String token = Preferences.getTokenLogin(getContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-AUTH-TOKEN", token);

        Call<List<SpinnerRuangan>> call = serviceApi.getComboRuangan(headers);
        call.enqueue(new Callback<List<SpinnerRuangan>>() {
            @Override
            public void onResponse(Call<List<SpinnerRuangan>> call, Response<List<SpinnerRuangan>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<SpinnerRuangan> arrays =  response.body();

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_ruangan.setAdapter(spinnerArrayAdapter);

            }

            @Override
            public void onFailure(Call<List<SpinnerRuangan>> call, Throwable t) {
                Toast.makeText(getActivity(), "Data Tidak ditemukan", Toast.LENGTH_LONG).show();
            }
        });

    }

}
