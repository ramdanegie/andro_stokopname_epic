package com.inhuman.scanner.stokopname;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inhuman.scanner.stokopname.Model.LoginUser;
import com.inhuman.scanner.stokopname.Model.Pegawai;
import com.inhuman.scanner.stokopname.SharedPreferences.Preferences;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tvl_namaUser;
    TextInputLayout tvl_Password;
    Button btn_login;
    ProgressBar progressBarLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvl_namaUser = findViewById(R.id.tvl_namaUser);
        tvl_Password = findViewById(R.id.tvl_Password);
        btn_login = findViewById(R.id.btn_login);

        progressBarLogin = findViewById(R.id.progressBarLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        if(!validUsername() || !validPassword()){
            return;
        }
        progressBarLogin.setVisibility(View.VISIBLE);
        Pegawai pegawai = new Pegawai("","","","","","");
        LoginUser data_send = new LoginUser(
                tvl_namaUser.getEditText().getText().toString(),
                tvl_Password.getEditText().getText().toString(),
                "",
                "",
                "",
                pegawai

        );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceApi.baseUrlApi )
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Log.d("login",data_send.toString() );
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);
        Call<LoginUser> call = serviceApi.loginUser(data_send);
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if(!response.isSuccessful()){
                    Toasty.warning(LoginActivity.this, "Login gagal, Username atau Password salah",
                            Toast.LENGTH_SHORT, true).show();
                    progressBarLogin.setVisibility(View.GONE);
                    return;
                }
                Toasty.success(LoginActivity.this, "Login Sukses",
                        Toast.LENGTH_SHORT, true).show();

                Log.e("result_login", "response : "+new Gson().toJson(response.body()) );

                Gson gson = new Gson();
                String responseLogin = gson.toJson(response.body());

                Preferences.setResultDataLogin(getBaseContext(), responseLogin);
                Preferences.setLoggedInStatus(getBaseContext(),true);
                Preferences.setTokenLogin(getBaseContext(),response.body().getToken().toString());

                progressBarLogin.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toasty.error(LoginActivity.this, "Koneksi Error",
                        Toast.LENGTH_SHORT, true).show();
                progressBarLogin.setVisibility(View.GONE);
            }
        });



    }
    public boolean validUsername(){
        String namaUser = tvl_namaUser.getEditText().getText().toString().trim();
        if(namaUser.isEmpty()){
            Toasty.warning(this,"Nama Pengguna tidak boleh kosong",  Toast.LENGTH_SHORT, true).show();
            return false;
        }else{
            return true;
        }
    }
    public boolean validPassword(){
        String password = tvl_Password.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            Toasty.warning(this,"Kata Sandi tidak boleh kosong",  Toast.LENGTH_SHORT, true).show();
            return false;
        }else{
            return true;
        }


    }
}
