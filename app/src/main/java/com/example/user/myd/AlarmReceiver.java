package com.example.user.myd;

/**
 * This class created for 'orders set alarm'
 */

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Toast.makeText(context, "תזכורת! הגעת הזמנה", Toast.LENGTH_LONG).show();
        String title="תזכורת! הגעת הזמנה ";
        String msg = "קבלת הזמנה חדשה";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(msg);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(1, mBuilder.build());

    }
}
