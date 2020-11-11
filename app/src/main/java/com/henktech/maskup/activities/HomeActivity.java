package com.henktech.maskup.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.henktech.maskup.R;
import com.henktech.maskup.controllers.DayHourController;
import com.henktech.maskup.controllers.SaveLoadController;
import com.henktech.maskup.pojos.Place;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem = item.getItemId();
        switch (idItem) {
            case R.id.reviewHours:
                reviewHours(item.getActionView());
                break;
            case R.id.editHours:
                editHours(item.getActionView());
                break;
            case R.id.reviewPlaces:
                reviewPlaces(item.getActionView());
                break;
            case R.id.editPlaces:
                editPlaces(item.getActionView());
                break;
        }
        return true;
    }

    public void editHours(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(HomeActivity.this, DayHourActivity.class);
                mainIntent.putExtra("prev", "1");
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        }, 100);
    }

    public void editPlaces(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(HomeActivity.this, PlacesActivity.class);
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        }, 100);
    }

    public void reviewHours(View v) {
        HashMap<Integer, Calendar> daysMap = (HashMap<Integer, Calendar>)
                SaveLoadController.loadFile(this.getApplicationContext(), getString(R.string.daysSavefile));

        StringBuilder days = new StringBuilder();

        assert daysMap != null;
        ArrayList employeeByKey = new ArrayList<>(daysMap.keySet());
        Collections.sort(employeeByKey);

        for (Object intKey : employeeByKey) {
            Calendar entry = daysMap.get(intKey);

            assert entry != null;
            @SuppressLint("DefaultLocale")
            String lineSet = DayHourController.dayNumToString(
                    entry.get(Calendar.DAY_OF_WEEK)) +
                    " at " +
                    entry.get(Calendar.HOUR) +
                    ":" +
                    String.format("%02d", entry.get(Calendar.MINUTE));

            days.append(lineSet).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
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

    public void reviewPlaces(View v) {
        ArrayList<Place> housePlaces = (ArrayList<Place>)
                SaveLoadController.loadFile(this.getApplicationContext(), getString(R.string.placesSavefile));

        StringBuilder places = new StringBuilder();

        assert housePlaces != null;

        for (Place place : housePlaces) {
            String lineSet = place.getName() + ": " + place.getProbability();

            places.append(lineSet).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle("Frecuencias");
        alertDialog.setMessage(places.toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void findFacemask(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(HomeActivity.this, FindMaskActivity.class);
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        }, 100);
    }
}