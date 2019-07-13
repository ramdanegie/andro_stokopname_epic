package com.inhuman.scanner.dashboad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView homeCard,profileCard,stokCard,settingCard,scannerCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeCard = (CardView) findViewById(R.id.home_card);
        profileCard = (CardView) findViewById(R.id.profile_card);
        stokCard = (CardView) findViewById(R.id.stok_ruangan_card);
        settingCard = (CardView) findViewById(R.id.setting_card);
        scannerCard = (CardView) findViewById(R.id.scanner_card);

        homeCard.setOnClickListener(this);
        profileCard.setOnClickListener(this);
        stokCard.setOnClickListener(this);
        settingCard.setOnClickListener(this);
        scannerCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()){
            case R.id.home_card : i = new Intent(this,HomeActivity.class) ;startActivity(i); break;
            case R.id.profile_card : i = new Intent(this,ProfileActivity.class) ; startActivity(i) ;break;
            case R.id.stok_ruangan_card : i = new Intent(this,StokActivity.class) ;startActivity(i); break;
            case R.id.setting_card : i = new Intent(this,SettingActivity.class) ;startActivity(i); break;
            case R.id.scanner_card : i = new Intent(this,ScannerActivity.class) ;startActivity(i); break;
            default:break;
        }
    }
}
