package com.app.alertamedicamento.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public abstract class WakeIntentService extends IntentService {

    /**
     * Name of the lock.
     */
    private static final String LOCK_NAME_STATIC = "com.app.alertamedicamento.lock";
    private static PowerManager.WakeLock lockStatic = null;

    /**
     * Constructor.
     *
     * @param name Name of the service.
     */
    public WakeIntentService(String name) {
        super(name);
    }

    /**
     * Obtain a lock.
     *
     * @param context Context
     */
    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    /**
     * Synchronized obtaining of the lock.
     *
     * @param context Context.
     * @return WakeLock object.
     */
    synchronized private static PowerManager.WakeLock getLock(Context context) {
        if (lockStatic == null) {
            PowerManager powManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            lockStatic = powManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOCK_NAME_STATIC);
            lockStatic.setReferenceCounted(true);
        }
        return (lockStatic);
    }

    /**
     * Has to be overrated in the class that will inherit from this one and here is where
     * the user of this class takes action to notify the user about the event.
     *
     * @param intent Intent.
     */
    public abstract void doReminderWork(Intent intent);

    /**
     * Called when an intent is starting up this service.
     *
     * @param intent Intent
     */
    @Override
    final protected void onHandleIntent(Intent intent) {
        try {
            doReminderWork(intent);
        } finally {
            try {
                PowerManager.WakeLock wakeLock = getLock(this);
                if (wakeLock.isHeld())
                    wakeLock.release();
            }catch (Exception e){
                // do nothing
            }
        }
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        enabled = true;
        super.setIntentRedelivery(enabled);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);
        Log.v("App","HandlingService.onStartCommand");
        if ((flags & START_FLAG_RETRY) == 0){
            Log.v("App","Service is restarting");
        }
        return START_STICKY;
    }

}