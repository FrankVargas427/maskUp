package com.henktech.maskup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(com.henktech.maskup.activities.MainActivity.this, HomeActivity.class);
                com.henktech.maskup.activities.MainActivity.this.startActivity(mainIntent);
                com.henktech.maskup.activities.MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}