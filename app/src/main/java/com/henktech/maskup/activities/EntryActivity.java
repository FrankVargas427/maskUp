package com.henktech.maskup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;
import com.henktech.maskup.controllers.NotificationController;
import com.henktech.maskup.controllers.SaveLoadController;
import com.henktech.maskup.pojos.Finding;

import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        final Context context = this.getApplicationContext();
        NotificationController.createNotificationChannel(this);

        getSupportActionBar().hide();

        if (SaveLoadController.loadFile(context, getString(R.string.findingsSavefile)) == null) {
            ArrayList<Finding> emptyFindings = new ArrayList<>();
            SaveLoadController.saveFile(emptyFindings, context, getString(R.string.findingsSavefile));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = null;
                if (SaveLoadController.loadFile(context, getString(R.string.daysSavefile)) == null) {
                    mainIntent = new Intent(getBaseContext(), DayHourActivity.class);
                } else {
                    mainIntent = new Intent(getBaseContext(), HomeActivity.class);
                }
                mainIntent.putExtra("prev", "0");
                EntryActivity.this.startActivity(mainIntent);
                EntryActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}