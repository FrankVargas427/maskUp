package com.henktech.maskup.controllers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;

public class DayHourController {

    public static Calendar getDayHour(Context context, int day) {
        final Calendar cal = Calendar.getInstance();

        /*
        Se crea un calendario de acuerdo al dia especificado.
         */
        cal.set(Calendar.DAY_OF_WEEK, day);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        /*
                        Dado que se selecciona una hora y se le da OK, es consigue la hora
                        y el minuto y se insertan en el calendario.
                         */
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
        timePickerDialog.show();

        return cal;
    }

    public static String dayNumToString(int dayNum) {
        // Esto regresa una version String del dia indicado.

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
