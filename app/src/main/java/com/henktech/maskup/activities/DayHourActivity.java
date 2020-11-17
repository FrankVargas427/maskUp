package com.henktech.maskup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;
import com.henktech.maskup.R;
import com.henktech.maskup.controllers.DayHourController;
import com.henktech.maskup.controllers.NotificationController;
import com.henktech.maskup.controllers.SaveLoadController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class DayHourActivity extends AppCompatActivity {
    final HashMap<Integer, Calendar> calListStart = new HashMap<>();
    Calendar sun, mon, tue, wed, thu, fri, sat;
    LinkedHashMap<Integer, Boolean> map = new LinkedHashMap<>();
    int prev = 0;

    public void initializeDayHour(Context context) {
        // make it so that if the days.txt is empty, make these. Else, make these but replace the ones that exist in the txt
        calListStart.put(1, sun = Calendar.getInstance());
        calListStart.put(2, mon = Calendar.getInstance());
        calListStart.put(3, tue = Calendar.getInstance());
        calListStart.put(4, wed = Calendar.getInstance());
        calListStart.put(5, thu = Calendar.getInstance());
        calListStart.put(6, fri = Calendar.getInstance());
        calListStart.put(7, sat = Calendar.getInstance());

        map.put(Calendar.MONDAY, false);
        map.put(Calendar.TUESDAY, false);
        map.put(Calendar.WEDNESDAY, false);
        map.put(Calendar.THURSDAY, false);
        map.put(Calendar.FRIDAY, false);
        map.put(Calendar.SATURDAY, false);
        map.put(Calendar.SUNDAY, false);

        HashMap<Integer, Calendar> loadDayHours = (HashMap<Integer, Calendar>)
                SaveLoadController.loadFile(context, getString(R.string.daysSavefile));
        if (loadDayHours != null) {
            for (Integer key : loadDayHours.keySet()) {
                map.put(key, true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayshours);
        getSupportActionBar().hide();

        initializeDayHour(this);

        if (getIntent().getExtras() != null) {
            prev = Integer.parseInt(getIntent().getStringExtra("prev"));
        }

        final Context context = DayHourActivity.this;
        final WeekdaysPicker widget = findViewById(R.id.weekdays);
        final Button saveDaysBtn = findViewById(R.id.saveDaysButton);

        widget.setCustomDays(map);
        if (prev == 0) {
            saveDaysBtn.setClickable(false);
            saveDaysBtn.setAlpha((float) 0.25);
        }

        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                if (selectedDays.contains(clickedDayOfWeek)) {
                    calListStart.put(clickedDayOfWeek, DayHourController.getDayHour(context, clickedDayOfWeek));
                }

                if (widget.noDaySelected()) {
                    saveDaysBtn.setClickable(false);
                    saveDaysBtn.setAlpha((float) 0.25);
                } else {
                    saveDaysBtn.setClickable(true);
                    saveDaysBtn.setAlpha((float) 1.0);
                }
            }
        });
    }

    public void saveDays(View v) {
        final WeekdaysPicker widget = findViewById(R.id.weekdays);
        List<Integer> selectedDaysInt = widget.getSelectedDays();
        HashMap<Integer, Calendar> saveDays = new HashMap<>();

        for (int i = 0; i < selectedDaysInt.size(); i++) {
            saveDays.put(selectedDaysInt.get(i), calListStart.get(selectedDaysInt.get(i)));
        }

        SaveLoadController.saveFile(saveDays, this.getApplicationContext(), getString(R.string.daysSavefile));

        NotificationController.scheduleNotification(this, saveDays);

        Toast toast = Toast.makeText(getApplicationContext(), "Days and hours saved!", Toast.LENGTH_SHORT);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = null;
                if (prev == 0) {
                    mainIntent = new Intent(DayHourActivity.this, PlacesActivity.class);
                } else {
                    mainIntent = new Intent(DayHourActivity.this, HomeActivity.class);
                }
                DayHourActivity.this.startActivity(mainIntent);
                DayHourActivity.this.finish();
            }
        }, 100);
    }
}