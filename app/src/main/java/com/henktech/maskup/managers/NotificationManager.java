package com.henktech.maskup.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.henktech.maskup.publishers.NotificationPublisher;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NotificationManager {

    public static void scheduleNotification(Context context, HashMap<Integer, Calendar> saveDays) {
        Intent notifIntent = new Intent(context, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100,
                notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer, Calendar> entry : saveDays.entrySet()) {
            Calendar iterDay = entry.getValue();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, iterDay.getTimeInMillis() - (1000 * 60 * 10),
                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }
}
