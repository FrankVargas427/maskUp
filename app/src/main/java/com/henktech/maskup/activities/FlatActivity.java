package com.henktech.maskup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;

public class FlatActivity extends AppCompatActivity {
    private static final String FILENAME = "days.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);
    }

    public void goToHome(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(com.henktech.maskup.activities.FlatActivity.this, HomeActivity.class);
                com.henktech.maskup.activities.FlatActivity.this.startActivity(mainIntent);
                com.henktech.maskup.activities.FlatActivity.this.finish();
            }
        }, 100);
    }

    public void reviewHours(View v) {

    }
}