package com.azkari.app;
import android.app.*;
import android.content.*;
import java.util.*;
public class ReminderScheduler {
 public static void schedule(Context c, int hour, int minute, String session, int id) {
   AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
   Intent in=new Intent(c,ReminderReceiver.class).putExtra("session",session);
   PendingIntent pi=PendingIntent.getBroadcast(c,id,in,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
   Calendar cal=Calendar.getInstance(); cal.set(Calendar.HOUR_OF_DAY,hour); cal.set(Calendar.MINUTE,minute); cal.set(Calendar.SECOND,0); cal.set(Calendar.MILLISECOND,0);
   if(cal.before(Calendar.getInstance())) cal.add(Calendar.DAY_OF_YEAR,1);
   if(android.os.Build.VERSION.SDK_INT>=23) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
   else am.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
 }
 public static void scheduleAll(Context c){
   schedule(c,7,0,"morning",1001); schedule(c,22,0,"evening",1002);
 }
}
