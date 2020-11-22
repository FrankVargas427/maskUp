package com.henktech.maskup.tools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.henktech.maskup.activities.FindMaskActivity;

public class NotificationReceiver extends BroadcastReceiver {

    /*
    Este es el recividor que, al ser llamado, lanza la notificacion al telefono para que si
    el usuario le da click, lo manda a la ventana de Encontrar su Cubrebocas.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent = new Intent(context, FindMaskActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyMaskUp")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("MaskUp!")
                .setContentText("¿Tienes tu cubrebocas?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}