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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

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
        HashMap daysMap = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = openFileInput(FILENAME);
            ois = new ObjectInputStream(fis);
            daysMap = (HashMap) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    assert ois != null;
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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

        AlertDialog alertDialog = new AlertDialog.Builder(com.henktech.maskup.activities.FlatActivity.this).create();
        alertDialog.setTitle("Alert");
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