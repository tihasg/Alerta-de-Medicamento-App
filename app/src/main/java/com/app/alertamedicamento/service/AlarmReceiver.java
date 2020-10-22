package com.app.alertamedicamento.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.alertamedicamento.business.Dispositivo;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("AlarmReceiver", "-> onReceive");

        Log.i("AlarmReceiver", "-> onReceive : _initApp = " + Dispositivo._initApp);

        if (Dispositivo._initApp)
        {
            Intent onAlarmReceiverServiceIntent = new Intent(context, AlertaService.class);
            onAlarmReceiverServiceIntent.putExtras(intent.getExtras());
            context.startService(onAlarmReceiverServiceIntent);
        }

    }

}
