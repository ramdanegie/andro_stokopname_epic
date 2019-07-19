package com.inhuman.scanner.stokopname;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.inhuman.scanner.stokopname.Model.LoginUser;
import com.inhuman.scanner.stokopname.Model.Pegawai;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    TextInputLayout etNamaPegawai;
    TextInputLayout etTempatLahir;
    TextInputLayout etTglLahir;
    TextInputLayout etNIK;
    TextInputLayout etJenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        etNamaPegawai = findViewById(R.id.etNamaPegawai);
        etTempatLahir = findViewById(R.id.etTempatLahir);
        etTglLahir = findViewById(R.id.etTglLahir);
        etNIK = findViewById(R.id.etNIK);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);

        String jsonLogin = Preferences.getResultDataLogin(this);
        if(jsonLogin != ""){
            Gson g = new Gson();
            LoginUser p  = g.fromJson(jsonLogin, LoginUser.class);
            etNamaPegawai.getEditText().setText(p.getPegawai().getNamaLengkap());
            etTempatLahir.getEditText().setText(p.getPegawai().getTempatLahir());
            if( p.getPegawai().getTglLahir() != null &&  p.getPegawai().getTglLahir().length()>0){
                String[] parts =   p.getPegawai().getTglLahir().split(" ");
                if(parts.length > 0){
                    etTglLahir.getEditText().setText(parts[0]);
                }
            }
            etNIK.getEditText().setText(p.getPegawai().getNoIdentitas());
            etJenisKelamin.getEditText().setText(p.getPegawai().getJenisKelamin());
        }




    }
}
