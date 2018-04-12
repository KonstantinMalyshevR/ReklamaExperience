package com.reklamar.reklamaexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onClickIntersitial(View v){
        startActivity(new Intent(MainActivity.this, IntersitialActivity.class));
    }

    public void onClickBanner(View v){
        startActivity(new Intent(MainActivity.this, BannerActivity.class));
    }

    public void onClickNative(View v){
        startActivity(new Intent(MainActivity.this, NativeActivity.class));
    }
}