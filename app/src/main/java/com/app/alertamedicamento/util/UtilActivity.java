package com.app.alertamedicamento.util;

import android.content.Context;
import android.os.Vibrator;

public class UtilActivity {

    public static void alertaVibrar(Context context) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//			vibrator.vibrate(200); // vibra por 200 milisseg

            // espera 100ms e vibra por 250ms, depois espera 100ms e vibra por 500ms
            // -1, para nao repetir a vibracao
            vibrator.vibrate(new long[]{100, 250, 100, 500}, -1); // vibra por 250 milisseg

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
