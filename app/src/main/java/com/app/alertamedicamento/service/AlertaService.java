package com.app.alertamedicamento.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.alertamedicamento.Activities.AlertaActivity;
import com.app.alertamedicamento.R;
import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.business.Dispositivo;
import com.app.alertamedicamento.dao.AlarmeDB;
import com.app.alertamedicamento.util.Funcoes;

import java.util.Calendar;

public class AlertaService extends WakeIntentService {

    public AlertaService() {
        super("AlertaService");
    }

    @Override
    public void doReminderWork(Intent intent) {
        if (intent != null) {

            Log.i("AlertaService", "-> doReminderWork");

            Bundle bundle = intent.getExtras();

            int _id = Funcoes.strToInt(bundle.getString("_id"));

            AlarmeDB alarmeDB = new AlarmeDB(getApplicationContext());

            Alarme alarme = (Alarme) alarmeDB.get(_id);

            Calendar dtAtual = Calendar.getInstance();
            dtAtual.setTimeInMillis(System.currentTimeMillis());
            Calendar dtAlarme = Calendar.getInstance();
            dtAlarme.setTimeInMillis(alarme.getDtLimite().getTime());

            int diff = (dtAlarme.get(Calendar.DAY_OF_MONTH)+dtAlarme.get(Calendar.MONTH)+dtAlarme.get(Calendar.YEAR))
                    - (dtAtual.get(Calendar.DAY_OF_MONTH)+dtAtual.get(Calendar.MONTH)+dtAtual.get(Calendar.YEAR));

            if (alarme != null)
            {
                if (diff >= 0) {

                    Dispositivo._initAlerta = true;

                    Log.i("start:alarm", alarme.toString());

                    Intent popup = new Intent(getApplicationContext(), AlertaActivity.class);
                    popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    popup.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    popup.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    popup.putExtras(bundle);

                    gerarNotificacao(getApplicationContext(), popup, "Nova notificação!", alarme.getNomeMedicamento(), alarme.getDosagem());

                    alarme.setContador(alarme.getContador() + 1);
                    alarmeDB.gravar(alarme);

                    getApplicationContext().startActivity(popup);
                } else {
                    alarme.setStatus("I");
                    alarmeDB.gravar(alarme);
                }

            }

        }
    }

    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.ic_calendar);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        builder.setContentIntent(p);
        builder.setPriority(NotificationCompat.DEFAULT_ALL);
        //builder.setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM));
        builder.setVibrate(new long[]{1000, 200, 500, 200, 100, 200, 1000});

        Notification n = builder.build();

        n.defaults |= Notification.DEFAULT_VIBRATE;
        n.flags = Notification.FLAG_AUTO_CANCEL;

        nm.notify(R.drawable.ic_launcher, n);

    }

}
