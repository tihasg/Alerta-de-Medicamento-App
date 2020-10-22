package com.app.alertamedicamento.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;

import com.app.alertamedicamento.R;

import java.io.Closeable;
import java.io.IOException;

/**
 * Manages beeps and vibrations.
 */
public final class BeepManager implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, Closeable {

    private static final String TAG = BeepManager.class.getSimpleName();

    private static final float BEEP_VOLUME = 1.0f;

    private final Activity activity;
    private MediaPlayer mediaPlayer = null;

    public BeepManager(Activity activity) {
        this.activity = activity;
        this.mediaPlayer = null;
        updatePrefs();
    }

    private static boolean shouldBeep(Context activity) {
        AudioManager audioService = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        audioService.setStreamVolume(AudioManager.STREAM_ALARM, audioService.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_PLAY_SOUND);

        return true;
    }

    public synchronized void updatePrefs() {
        shouldBeep(activity);

        if (mediaPlayer == null) {
            activity.setVolumeControlStream(AudioManager.STREAM_ALARM);
            mediaPlayer = buildMediaPlayer(activity);
        }
    }

    public synchronized void playBeepSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public synchronized void stopBeepSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private MediaPlayer buildMediaPlayer(Context activity) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(activity, PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        try {
            AssetFileDescriptor file = activity.getResources().openRawResourceFd(R.raw.digital_alarm_clock);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            } finally {
                file.close();
            }
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            mediaPlayer.release();
            return null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // When the beep has finished playing, rewind to queue up another one.
        mp.seekTo(0);
    }

    @Override
    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            activity.finish();
        } else {
            mp.release();
            mediaPlayer = null;
            updatePrefs();
        }
        return true;
    }

    @Override
    public synchronized void close() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
