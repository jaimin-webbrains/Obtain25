package com.obtain25;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.obtain25.ui.home.receiverequest.ReceiveRequestFragment;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    AudioManager am;
    String channelId = "id";
    boolean isRinging;
    Ringtone ringtone;


    @Override
    public void onMessageReceived(RemoteMessage message) {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {

            case AudioManager.RINGER_MODE_SILENT:
                // sendMyNotification(message);
                playSound(message);
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                // sendMyNotification(message);
                playSound(message);
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                sendMyNotification(message);
                break;
        }

    }


    private void sendMyNotification(RemoteMessage message) {
        //On click of notification it redirect to this Activity
        RemoteMessage.Notification notification = message.getNotification();
        /*Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);*/
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(false)
                .setSound(soundUri)
                .setNumber(50)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        //.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(channelId, 0, notificationBuilder.build());

    }

    private void playSound(RemoteMessage message) {
        RemoteMessage.Notification notification = message.getNotification();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
        if (volume == 0)
            volume = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        am.setStreamVolume(AudioManager.STREAM_ALARM, volume, AudioManager.FLAG_PLAY_SOUND);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI));
        if (ringtone != null) {
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
            ringtone.play();
            isRinging = true;
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(false)
                    .setNumber(50)
                    .setSound(soundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
            //.setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(channelId, 0, notificationBuilder.build());
        }
    }


}