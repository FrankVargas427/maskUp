package com.henktech.maskup.controllers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.henktech.maskup.tools.NotificationReceiver;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

public class NotificationController {

    public static void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "MaskUpReminderChannel";
            String description = "Channel for MaskUp Reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyMaskUp", name, importance);
            channel.setDescription(description);

            NotificationManager notifyMan = context.getSystemService(NotificationManager.class);
            notifyMan.createNotificationChannel(channel);
        }
    }

    public static void scheduleNotification(Context context, HashMap<Integer, Calendar> saveDays) {
        for (Map.Entry<Integer, Calendar> entry : saveDays.entrySet()) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                    new Intent(context, NotificationReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Calendar calendar = entry.getValue();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() - (1000 * 60 * 10),
                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }
}
