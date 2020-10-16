package com.henktech.maskup.activities;

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
import com.henktech.maskup.managers.DayHourManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String FILENAME = "days.txt";
    final HashMap<Integer, Calendar> calListStart = new HashMap<>();
    final Calendar sun, mon, tue, wed, thu, fri, sat;
    LinkedHashMap<Integer, Boolean> map = new LinkedHashMap<>();

    public HomeActivity() {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final WeekdaysPicker widget = (WeekdaysPicker) findViewById(R.id.weekdays);
        final Button saveDaysBtn = (Button) findViewById(R.id.saveButton);

        widget.setCustomDays(map);
        saveDaysBtn.setClickable(false);
        saveDaysBtn.setAlpha((float) 0.25);

        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                if (selectedDays.contains(clickedDayOfWeek)) {
                    calListStart.put(clickedDayOfWeek, DayHourManager.getDayHour(com.henktech.maskup.activities.HomeActivity.this, clickedDayOfWeek));
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
        final WeekdaysPicker widget = (WeekdaysPicker) findViewById(R.id.weekdays);
        List<Integer> selectedDaysInt = widget.getSelectedDays();
        HashMap<Integer, Calendar> saveDays = new HashMap<>();

        for (int i = 0; i < selectedDaysInt.size(); i++) {
            saveDays.put(selectedDaysInt.get(i), calListStart.get(selectedDaysInt.get(i)));
        }

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(FILENAME, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(saveDays);

            Toast.makeText(this, "Saved days!", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(com.henktech.maskup.activities.HomeActivity.this, com.henktech.maskup.activities.FlatActivity.class);
                com.henktech.maskup.activities.HomeActivity.this.startActivity(mainIntent);
                com.henktech.maskup.activities.HomeActivity.this.finish();
            }
        }, 100);
    }
}