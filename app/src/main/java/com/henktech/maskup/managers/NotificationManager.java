package com.henktech.maskup.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.henktech.maskup.publishers.NotificationPublisher;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;

public class NotificationManager {

    public static void scheduleNotification(Context context, HashMap<Integer, Calendar> saveDays) {
        AlarmManager[] alarmManagers = new AlarmManager[saveDays.size()];
        Intent intents[] = new Intent[saveDays.size()];

        for (Map.Entry<Integer, Calendar> entry : saveDays.entrySet()) {
            int i = 0;
            intents[i] = new Intent(context, NotificationPublisher.class);
            intents[i].putExtra("MaskUp!", "Remember your mask!");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, i, intents[i], 0);

            Calendar calendar = entry.getValue();

            alarmManagers[i] = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManagers[i].setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() - (1000 * 60 * 10),
                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }
}
