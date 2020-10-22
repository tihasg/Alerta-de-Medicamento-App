package com.app.alertamedicamento.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

@SuppressLint("NewApi")
public class DroidUtil {

    public static final String DATABASE_NAME = "SYS";

    public static final String PREFS_NAME = "SYS";
    public static final int REQUEST_PERMISSION = 555;
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 789;

    public static void showMessage(final Activity activity, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setNeutralButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Mensagem");
        dialog.show();
    }

    public static void showToastMessage(final Activity activity, String msg) {
        Toast mostrar = Toast.makeText(activity, msg, Toast.LENGTH_LONG);

        mostrar.show();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }

    public static void ShowForm(final Activity activity, final Class<?> classe) {
        Intent novaJanela = new Intent(activity, classe);
        activity.startActivity(novaJanela);
    }

    public static boolean setParametro(final Activity activity, String key, String value) {
        SharedPreferences settings;
        settings = activity.getSharedPreferences(PREFS_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getParametro(final Activity activity, String key) {
        SharedPreferences settings;
        settings = activity.getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(key, "");
    }

    public static void showPermissionRationaleDialog(final Activity activity, final String message, final String permission) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestForPermission(activity, permission);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    public static void requestForPermission(Activity activity, final String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_PERMISSION);
    }

    public static int indexOf(final Adapter adapter, Object value) {
        for (int index = 0, count = adapter.getCount(); index < count; ++index) {
            if (adapter.getItem(index).equals(value)) {
                return index;
            }
        }
        return -1;
    }

    public static int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
            }
        }
        return index;
    }

    public static int getIndex(Spinner spinner, int id) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).hashCode() == id) {
                return i;
            }
        }
        return 0;
    }

    public static String getStrDatePicker(DatePicker edtDate) {
        java.sql.Date date = Funcoes.dateToSQLDate(Funcoes.encodeDate(edtDate.getYear(), edtDate.getMonth() + 1, edtDate.getDayOfMonth()));

        return Funcoes.getFmtValue(Tipo.DATA, date);
    }

    public static Timestamp getTimeStampDatePicker(DatePicker edtDate) {
        java.sql.Date date = Funcoes.dateToSQLDate(Funcoes.encodeDate(edtDate.getYear(), edtDate.getMonth(), edtDate.getDayOfMonth()));

        return new Timestamp(date.getTime());
    }

    public static String getStrTimePicker(TimePicker edtTime) {
        Date time = Funcoes.encodeTime(null, edtTime.getCurrentHour(), edtTime.getCurrentMinute(), 0, 0);

        return Funcoes.getFmtValue(Tipo.HORA, time);
    }

    public static String getVersionSO() {
        StringBuffer buf = new StringBuffer();
        buf.append("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        buf.append("\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
        buf.append("\nVERSION.SDK {" + Build.VERSION.SDK_INT + "}");

        return buf.toString();
    }

    public static String getHardwareSerial() {
        return Build.SERIAL;
    }

    public static String getTypeAndroid(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return "Tablet";
        } else {
            return "Mobile";
        }
    }

    public static String getIMEI(Context context) {
        String devcieId = "0";
        //requisitarAcesso(activity, Manifest.permission.READ_PHONE_STATE);

        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            devcieId = mTelephony.getDeviceId();
        } else {
            devcieId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }

        return devcieId;
    }

    public static String getPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getLine1Number();
    }

    public static String getDeviceId(Activity activity) {
        String devcieId = "0";
        requisitarAcesso(activity, Manifest.permission.READ_PHONE_STATE);

        TelephonyManager mTelephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            devcieId = mTelephony.getDeviceId();
        } else {
            devcieId = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
        }

        return devcieId;
    }

    public static boolean checkMarshMellowPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static boolean isAndroidMOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void discar(Activity activity, String fone) {
        try {
            fone = (fone == null) ? "" : fone;
            if (!fone.equals("")) {
                // Iniciar liga��o direto...
                // Uri u = Uri.parse ("tel: " + fone);
                // Intent i = new Intent(Intent.ACTION_CALL, u);
                // activity.startActivity(i);

                // Pre Discar, deixando que o usuario invoque a liga��o....
                Intent chamada = new Intent(Intent.ACTION_DIAL);
                chamada.setData(Uri.parse("tel:" + fone));
                try {
                    activity.startActivity(chamada);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Funcionalidade n�o suportada.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendMail(Activity activity, String email) {
        try {
            email = (email == null) ? "" : email;
            if (!email.equals("")) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    activity.startActivity(Intent.createChooser(i, "Enviar e-mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Nenhum cliente de e-mail encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWebPage(Activity activity, String url) {
        try {
            url = (url == null) ? "" : url;
            if (!url.equals("")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(browserIntent);
            }
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void startActivity(Activity activity, Intent i, boolean closed) {
        if (closed) {
            activity.finish();
        }
        activity.startActivity(i);
    }

    public static void requisitarAcesso(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    DroidUtil.showPermissionRationaleDialog(activity, "Test", permission);
                } else {
                    DroidUtil.requestForPermission(activity, permission);
                }
            }
        }
    }

    public void exibirMensagem(String titulo, String mensagem, Activity aplicacao) {

        AlertDialog.Builder alertaMensagem = new AlertDialog.Builder(aplicacao);
        alertaMensagem.setTitle(titulo);
        alertaMensagem.setNeutralButton("OK", null);
        alertaMensagem.setMessage(mensagem);
        alertaMensagem.show();

    }

    public void mensagemConfirmacao(String titulo, String mensagem, final Activity aplicacao) {

        AlertDialog.Builder confirmacaoMensagem = new AlertDialog.Builder(aplicacao);
        confirmacaoMensagem.setMessage(mensagem)
                .setTitle(titulo)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent intent = aplicacao.getIntent();
                        aplicacao.finish();
                        aplicacao.startActivity(intent);

                    }
                });
        confirmacaoMensagem.create();
        confirmacaoMensagem.show();

    }

    public boolean validarEditText(EditText editText) {
        boolean retorno = true;

        if (editText.getText().toString().isEmpty()) {
            editText.setError("Campo obrigatorio");
            editText.requestFocus();
            retorno = false;
        }

        return retorno;
    }

    public String comporData(int dia, int mes, int ano) {
        String data = null;
        String _dia = String.valueOf(dia);
        String _mes = String.valueOf(mes);

        if (String.valueOf(dia).length() < 2) {
            _dia = "0" + String.valueOf(dia);
        }

        if (String.valueOf(mes).length() < 2) {
            _mes = "0" + String.valueOf(mes);
        }

        if (dia == 31 && dia > qtdDiasMes(mes, ano)) {
            _dia = String.valueOf(qtdDiasMes(mes, ano));
        }

        if (dia == 30 && dia > qtdDiasMes(mes, ano)) {
            _dia = String.valueOf(qtdDiasMes(mes, ano));
        }

        if (dia == 29 && dia > qtdDiasMes(mes, ano)) {
            _dia = String.valueOf(qtdDiasMes(mes, ano));
        }

        if (dia == 28 && dia > qtdDiasMes(mes, ano)) {
            _dia = String.valueOf(qtdDiasMes(mes, ano));
        }

        data = _dia + "/" + _mes + "/" + String.valueOf(ano);

        return data;
    }

    public String formatarData(String data) {
        String retorno = null;
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        retorno = ano + "-" + mes + "-" + dia;
        return retorno;
    }

    public int qtdDiasMes(int mes, int ano) {
        int qtdDias;
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            qtdDias = 31;
        } else {
            qtdDias = 30;
        }

        if (mes == 2 && anoBissexto(ano)) {
            qtdDias = 29;
        }

        if (mes == 2 && !anoBissexto(ano)) {
            qtdDias = 28;
        }

        return qtdDias;

    }

    public boolean anoBissexto(int ano) {
        if (ano % 4 == 0 && (ano % 400 == 0 || ano % 100 != 0)) {
            return true;
        } else {
            return false;
        }
    }

}
