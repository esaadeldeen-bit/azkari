package com.azkari.app;

import android.app.*;
import android.content.*;
import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c, Intent i) {
        String session = i.getStringExtra("session");
        String title = session.equals("morning") ? "حان وقت أذكار الصباح" : "حان وقت أذكار المساء";
        Intent open = new Intent(c, MainActivity.class).putExtra("session", session);
        PendingIntent pi = PendingIntent.getActivity(c, session.hashCode(), open,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationManager nm = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        String channel="azkari";
        if (android.os.Build.VERSION.SDK_INT >= 26)
            nm.createNotificationChannel(new NotificationChannel(channel,"أذكار",NotificationManager.IMPORTANCE_HIGH));
        Notification n = new NotificationCompat.Builder(c,channel)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(title).setContentText("افتح التطبيق واقرأ وردك الآن.")
            .setAutoCancel(true).setContentIntent(pi).build();
        nm.notify(session.equals("morning")?1001:1002,n);
    }
}
