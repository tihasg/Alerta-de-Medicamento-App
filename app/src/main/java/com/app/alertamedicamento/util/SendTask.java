package com.app.alertamedicamento.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.app.alertamedicamento.R;

public class SendTask extends AsyncTask<Integer, String, Integer> {

    private Context context = null;
    private ProgressDialog progress = null;
    private String texto = null;

    public SendTask(Context context, String texto) {
        this.context = context;
        this.texto = texto;
    }

    public SendTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        setProgress();
    }

    public void setProgress() {

        if (texto != null) {

            //progress = FuncoesAndroid.createProgressDialog(context, texto);

            progress = new ProgressDialog(context);

            progress.setIcon(R.drawable.process);
            progress.setMessage("Aguarde!\n" + texto);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setInverseBackgroundForced(true);
            progress.setCancelable(false);
            progress.show();

        }

    }

    @Override
    protected Integer doInBackground(Integer... params) {

        synchronized (params) {
            return 1;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        closeProgress();
    }

    protected void closeProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values.length >= 1) {
            if (progress != null) {
                progress.setMessage(values[0]);
            }
        }
    }

    protected Context getContext() {
        return context;
    }

    protected void setMsg(String texto) {
        publishProgress(texto);
    }
}
