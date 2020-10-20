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
        for (Map.Entry<Integer, Calendar> entry : saveDays.entrySet()) {
            Intent notificationIntent = new Intent(context, NotificationPublisher.class);
            notificationIntent.putExtra("MaskUp!", "Remember your mask!");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, entry.getKey(),
                    notificationIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar iterDay = entry.getValue();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    iterDay.getTimeInMillis() - (1000 * 60 * 10),
                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }
}
