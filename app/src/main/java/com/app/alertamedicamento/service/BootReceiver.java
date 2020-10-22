package com.app.alertamedicamento.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.alertamedicamento.business.Dispositivo;

public class BootReceiver extends BroadcastReceiver {

    public static final String TAG = BootReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "AlertaMedicamento - onReceive: " + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)
                || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                || intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.d(TAG, "Init Service...");

            Dispositivo.restartTaskService(context);

        }

    }

}
