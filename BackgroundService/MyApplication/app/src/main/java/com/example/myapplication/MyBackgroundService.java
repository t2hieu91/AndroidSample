package com.example.myapplication;

import android.app.Notification;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import java.io.IOException;
import java.util.Objects;

public class MyBackgroundService extends LifecycleService {

    public static final String CHANNEL_ID = "Hieu Service";
    public static final String ACTION_START_SERVICE = "start service";
    public static final String ACTION_STOP_SERVICE = "stop service";
    public static final String TAG = MyBackgroundService.class.getSimpleName();
    private static final int FOREGROUND_ID = 1;
    private boolean isServiceStarted = false;

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isServiceStarted) {
            isServiceStarted = true;
            Notification notification = createNotificationChannel(R.string.act_base_services_toast_channel);
            startForeground(FOREGROUND_ID, notification);
        }
    }

    private Notification createNotificationChannel(int resString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel serviceChanel = new NotificationChannel(CHANNEL_ID, getResources()
                    .getString(R.string.act_base_services_toast_channel), NotificationManager.IMPORTANCE_HIGH);
            serviceChanel.setDescription(getResources()
                    .getString(resString));
            serviceChanel.enableVibration(true);
            notificationManager.createNotificationChannel(serviceChanel);
        }

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        return builder.setContentTitle(getResources()
                        .getString(R.string.act_base_services_toast_service))
                .setContentText(getResources().getString(R.string.act_base_services_toast_service_running))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(Notification.PRIORITY_HIGH).build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceStarted = false;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            if (Objects.equals(intent.getAction(), ACTION_START_SERVICE)) {
                Toast.makeText(getApplicationContext(),"This is a Service running in Background",
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Service is running");
                startMedia();
            }
            if (Objects.equals(intent.getAction(), ACTION_STOP_SERVICE)) {
                Log.i(TAG, "Service is stopping");
                stopMedia(startId);
            }
            return START_STICKY;
        }

        return START_STICKY;
    }

    private void startMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.hay_trao_cho_anh);
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    if (!mediaPlayer.isPlaying()) {
//                        mediaPlayer.start();
//                    }
//                }
//            });
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    private void stopMedia(int startId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopForeground(true);
        stopSelfResult(startId);
    }

}
