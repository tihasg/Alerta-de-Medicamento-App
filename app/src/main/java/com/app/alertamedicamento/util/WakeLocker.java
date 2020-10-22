package com.app.alertamedicamento.util;

import android.content.Context;
import android.os.PowerManager;

import com.app.alertamedicamento.business.Dispositivo;

public abstract class WakeLocker {

    private static PowerManager.WakeLock wakeLock = null;

    public static void acquire(Context ctx) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, Dispositivo.APP_TAG);
        wakeLock.acquire();
    }

    public static void release() {
        if (wakeLock != null) wakeLock.release();
        wakeLock = null;
    }

}