package com.app.alertamedicamento.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Date;
import com.app.alertamedicamento.R;
import com.app.alertamedicamento.adapter.AlarmeAgAdapter;
import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.business.Dispositivo;
import com.app.alertamedicamento.dao.AlarmeDB;
import com.app.alertamedicamento.util.AlarmeUtils;
import com.app.alertamedicamento.util.BeepManager;
import com.app.alertamedicamento.util.Funcoes;
import com.app.alertamedicamento.util.FuncoesAndroid;
import com.app.alertamedicamento.util.SendTask;
import com.app.alertamedicamento.util.Tipo;
import com.app.alertamedicamento.util.WakeLocker;

import java.util.ArrayList;
import java.util.Calendar;

public class AlertaActivity extends AppCompatActivity {
    public static int id1;
    public static String dat;
    public static String verificar="false";
    private boolean postergar = true;
    private boolean cancel = false;
    private Alarme alarme = null;
    private AlarmeDB alarmeDB = null;
    private AsyncTask<Integer, String, Integer> task = null;
    private BeepManager beep = null;
    private ListView listView = null;
    private AlarmeAgAdapter adapter = null;
    private ArrayList<Object> lista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerta_medical);

        initComponents();

    }

    private void initComponents() {

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("_id") != null) {
            alarmeDB = new AlarmeDB(this);
            alarme = (Alarme) alarmeDB.get(Funcoes.strToInt(bundle.getString("_id")));
        }

        if (alarme != null) {
            listView = (ListView) findViewById(R.id.listView);

            lista = new AlarmeDB(this).getListaAg(alarme.getHora(), alarme.getMinuto());

            adapter = new AlarmeAgAdapter(this, R.layout.row_alarme_ag, lista);
            listView.setAdapter(adapter);

            FuncoesAndroid.setListViewHeightBasedOnChildren(listView);

        } else {
            setResult(RESULT_CANCELED);
            finish();
        }

    }

    private void startAlarm() {

        cancel = false;
        postergar = true;

        task = new SendTask(this) {

            private Thread thread = null;
            private Vibrator v = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                beep = new BeepManager(AlertaActivity.this);
                thread = new Thread();
            }

            @Override
            protected Integer doInBackground(Integer... params) {

                for (int i = 0; i < 100; i++) {

                    if (!cancel) setMsg("");

                    if (!cancel) sleep(1000 * 3);

                }

                if (!cancel) sleep(1000 * 60 * 1);

                return super.doInBackground(params);
            }

            private void sleep(int i) {
                try {
                    thread.sleep(i);
                } catch (Exception e) {
                }
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if (!cancel) {

                    if (v == null) v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(new long[]{1000, 200, 500, 200, 100, 200, 1000}, -1);

                    beep.playBeepSound();
                }
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                close();
            }

        };

        task.execute();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void postergar(View view) {
        postergar = true;
        verificar = "true";
        close();
    }

    private void cancelTask() {
            cancel = true;;
            if (beep != null) beep.stopBeepSound();
            if (task != null) task.cancel(true);
            // Clear all notification
            NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancelAll();
    }

    public void confirmar(View view) {
        postergar = false;
        verificar = "false";
        id1=alarme.getId();
        dat =(Funcoes.getFmtValue(Tipo.DATA, alarme.getDtLimite()));
        close();

    }

    private void close() {

        Dispositivo._minimize = true;

        setResult(RESULT_OK);
        finish();

        Intent i = new Intent(AlertaActivity.this, MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void updateScheduling() {

        for (Object o : lista) {
            alarme = (Alarme) o;
            if (alarme != null) {

                if (postergar) {
                    alarme.setStatus("P");
                } else {
                    alarme.setStatus("A");
                }
                alarme.setHorario(Funcoes.getCurrentTimestamp());
                alarmeDB.gravar(alarme);

                if (alarme.getRepeat().equals("N") && !alarme.getStatus().equals("P")) {
                    alarmeDB.deletar(alarme.getId());
                    AlarmeUtils.unschedule(this, alarme);
                }

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Dispositivo._initAlerta = false;
        cancelTask();
        updateScheduling();
        Dispositivo.restartTaskService(getApplicationContext());
        WakeLocker.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        WakeLocker.acquire(this);
        Dispositivo._initAlerta = true;
        startAlarm();
    }

}
