package com.app.alertamedicamento.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.service.AlarmReceiver;

public class AlarmeUtils {

    public static void schedule(Context ctx, Alarme alarme) {

        Bundle extras = new Bundle();

        extras.putString("_id", "" + alarme.getId());

        PendingIntent mAlarmSender = pendingIntent(ctx, alarme.getId(), extras);

        setExactAlarm(ctx, alarme.getCalendar().getTimeInMillis(), mAlarmSender);

        Log.i("schedule:alarm", alarme.toString());
    }

    private static void setExactAlarm(Context ctx, long millis, PendingIntent pendingIntent) {
        try {
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                if (Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
                }
            }
        } catch (Exception e) {
            Log.e("setExactAlarm", e.getMessage());
        }
    }

    private static void cancelAlarm(Context ctx, int alarmId)
    {
        try {
            PendingIntent routinePendingIntent = pendingIntent(ctx, alarmId, null);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.cancel(routinePendingIntent);
            }
        } catch (Exception e) {
            Log.e("setExactAlarm", e.getMessage());
        }
    }

    private static PendingIntent pendingIntent(Context ctx, int alarmId, Bundle extras) {
        Intent intent = new Intent(ctx, AlarmReceiver.class);
        if (extras != null) intent.putExtras(extras);
        return PendingIntent.getBroadcast(ctx, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static String getFormattedTime(Integer hour, Integer minute) {
        return AlarmeUtils.pad(hour) + ":" + AlarmeUtils.pad(minute);
    }

    //remove a Intencao pendente
    public static void unschedule(Context ctx, Alarme alarme) {
        cancelAlarm(ctx, alarme.getId());
        Log.i("unschedule:alarm", "alarmId = " + alarme.toString());
    }

    public static String pad(int c) {
        if (c >= 10) //se for menor que 10 add o 0 na frente
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public enum TipoIntervalo {
        HORA, MINUTO;
    }

}
