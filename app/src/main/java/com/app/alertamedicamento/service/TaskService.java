package com.app.alertamedicamento.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.business.Dispositivo;
import com.app.alertamedicamento.dao.AlarmeDB;
import com.app.alertamedicamento.util.AlarmeUtils;
import com.app.alertamedicamento.util.Funcoes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TaskService extends WakeIntentService {

    public TaskService() {
        super("TaskService");
    }

    @Override
    public void doReminderWork(Intent intent) {

        Log.i("TaskService", "Alerta de Medicamentos - Registrando / Atualizando alarmes...");

        atualizarAlarmes(getApplicationContext());

        Dispositivo._initApp = true;

    }

    /***
     *
     * Atualizar lista de Alertas!
     *
     * @param context
     */
    private synchronized void atualizarAlarmes(Context context) {
        Log.i("dispositivo", "Atualizando agendamento de alarmes!");

        Dispositivo._initAlerta = true;

        AlarmeDB alarmeDB = new AlarmeDB(context);

        ArrayList<Object> list = alarmeDB.getLista();

        for (Object o : list) {
            Alarme alarme = (Alarme) o;

            int h = 0, m = 0, p = 0;
            h = alarme.getHora();
            m = alarme.getMinuto();
            p = alarme.getPostergar();

            if ((alarme.getRepeat().equals("S")) && (p > 0) && (p < 24) && (!alarme.getStatus().equals("P"))) {
                Calendar c0 = Calendar.getInstance();
                c0.setTimeInMillis(System.currentTimeMillis());

                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.HOUR_OF_DAY, h);
                c1.set(Calendar.MINUTE, m);

                if ((c1.get(Calendar.HOUR_OF_DAY) <= c0.get(Calendar.HOUR_OF_DAY))) {
                    while ((c1.get(Calendar.HOUR_OF_DAY) <= c0.get(Calendar.HOUR_OF_DAY)) && (h < 24)) {
                        if (c1.get(Calendar.HOUR_OF_DAY) == c0.get(Calendar.HOUR_OF_DAY)) {
                            if (c1.get(Calendar.MINUTE) > c0.get(Calendar.MINUTE)) {
                                break;
                            }
                        }
                        h += p;
                        c1.set(Calendar.HOUR_OF_DAY, h);
                    }

                    if (h >= 24) {
                        h = h - 24;
                    }
                }
            } else
                if (alarme.getStatus().equals("P")) {
                    Calendar c0 = Calendar.getInstance();
                    c0.setTimeInMillis(System.currentTimeMillis() + (1000 * 60 * 1));
                    h = c0.get(Calendar.HOUR_OF_DAY);
                    m = c0.get(Calendar.MINUTE);
                }

            alarme.setHora(h);
            alarme.setMinuto(m);

            AlarmeUtils.unschedule(context, alarme);
            AlarmeUtils.schedule(context, alarme);

            alarmeDB.gravar(alarme);
        }

        Dispositivo._initAlerta = false;

    }

}
