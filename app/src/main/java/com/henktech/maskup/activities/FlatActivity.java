package com.henktech.maskup.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;
import com.henktech.maskup.managers.DayHourManager;
import com.henktech.maskup.managers.SaveLoadManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class FlatActivity extends AppCompatActivity {
    private static final String FILENAME_DAYS = "days.txt";
    private static final String FILENAME_PLACES = "places.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);
        getSupportActionBar().hide();
    }

    public void goToHome(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(FlatActivity.this, DayHourActivity.class);
                FlatActivity.this.startActivity(mainIntent);
                FlatActivity.this.finish();
            }
        }, 100);
    }

    public void reviewHours(View v) {

        HashMap<Integer, Calendar> daysMap = (HashMap<Integer, Calendar>)
                SaveLoadManager.loadFile(this.getApplicationContext());

        StringBuilder days = new StringBuilder();

        assert daysMap != null;
        ArrayList employeeByKey = new ArrayList<>(daysMap.keySet());
        Collections.sort(employeeByKey);

        for (Object intKey : employeeByKey) {
            Calendar entry = (Calendar) daysMap.get(intKey);

            assert entry != null;
            @SuppressLint("DefaultLocale")
            String lineSet = DayHourManager.dayNumToString(
                    entry.get(Calendar.DAY_OF_WEEK)) +
                    " at " +
                    entry.get(Calendar.HOUR) +
                    ":" +
                    String.format("%02d", entry.get(Calendar.MINUTE));

            days.append(lineSet).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(FlatActivity.this).create();
        alertDialog.setTitle("Alarmas");
        alertDialog.setMessage(days.toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}