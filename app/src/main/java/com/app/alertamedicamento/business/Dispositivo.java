package com.app.alertamedicamento.business;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.alertamedicamento.Activities.MenuActivity;
import com.app.alertamedicamento.service.MonitorService;
import com.app.alertamedicamento.service.TaskService;

public class Dispositivo extends Application {

    public static final String APP_TAG = "com.app.alertamedicamento.WAKE_APP";

    public static boolean _initApp = false;
    public static boolean _initAlerta = false;

    private static Intent serviceTaskIntent = null;
    private static Intent serviceMonitorIntent = null;
    public static boolean _minimize = false;
    public static String PdfFileName = "";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("dispositivo", "Inicializando app!");

        _initApp = false;
        _initAlerta = false;
        _minimize = false;

        restartTaskService(getApplicationContext());

    }

    public static void restartTaskService(Context context) {
        if (serviceTaskIntent != null)
            context.stopService(serviceTaskIntent);
        serviceTaskIntent = new Intent(context, TaskService.class);
        context.startService(serviceTaskIntent);
        if (serviceMonitorIntent == null) {
            serviceMonitorIntent = new Intent(context, MonitorService.class);
            context.startService(serviceMonitorIntent);
        }
    }

    public static void initMenuApp(Context context) {
        Dispositivo._minimize = false;
        Intent i = new Intent(context, MenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
