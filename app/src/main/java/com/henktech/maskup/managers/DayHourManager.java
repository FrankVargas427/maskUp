package com.henktech.maskup.managers;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import com.henktech.maskup.activities.HomeActivity;

import java.util.Calendar;

public class DayHourManager {
    public static Calendar getDayHour(final HomeActivity homeActivity, int day) {
        final Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, day);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        TimePickerDialog timePickerDialog = new TimePickerDialog(homeActivity,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);

                        @SuppressLint("DefaultLocale")
                        String lineSet = "Alarm set for " +
                                dayNumToString(cal.get(Calendar.DAY_OF_WEEK)) + " at " +
                                hourOfDay + ":" + String.format("%02d", minute);
                        Toast.makeText(homeActivity.getApplicationContext(), lineSet, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        timePickerDialog.show();

        return cal;
    }

    public static String dayNumToString(int dayNum) {
        switch (dayNum) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "null";
    }
}
