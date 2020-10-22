package com.app.alertamedicamento.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.alertamedicamento.R;
import com.app.alertamedicamento.dao.AlarmeDB;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorService extends Service {

    public MonitorService() {
    }

    private static long UPDATE_INTERVAL = 1000 * 60 * 30;
    private static Calendar calendar = Calendar.getInstance();
    private static int count = 0;
    private static Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _startService();

    }

    private void _startService() {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();
                    }
                }, 1000, UPDATE_INTERVAL);

        Log.i(getClass().getSimpleName(), "MonitorService Timer started....");
    }

    private void doServiceWork() {

        Log.e("MonitorService", "time: " + calendar.getTime());
        //do something wotever you want
        //like reading file or getting data from network

        try {

            if (new AlarmeDB(getApplicationContext()).getQtdRegistros() > 0) {
                addNotification();
            }
        } catch (Exception e) {}

    }

    private void _shutdownService() {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    private void addNotification() {
        // Clear all notification
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Alerta De Medicamentos")
                        .setContentText("Monitoramento Ativado");

        int id = count++;

        /*Intent notificationIntent = new Intent(this, MenuActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, id, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);*/

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _shutdownService();
    }

}
