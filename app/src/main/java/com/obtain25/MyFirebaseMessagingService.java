package com.obtain25;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.obtain25.ui.home.receiverequest.ReceiveRequestFragment;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    AudioManager am;
    boolean isRinging;
    Ringtone ringtone;


    @Override
    public void onMessageReceived(RemoteMessage message) {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                playSound(message.getNotification().getBody());
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                playSound(message.getNotification().getBody());
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                sendMyNotification(message.getNotification().getBody());
                break;
        }
        // sendMyNotification(message.getNotification().getBody());
    }


    private void sendMyNotification(String message) {
        //On click of notification it redirect to this Activity

       /* Intent intent = new Intent(this, ReceiveRequestFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);*/
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Firebase Push notification")
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(soundUri)
                .setNumber(50);
        // .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
/*
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                playSound();
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                playSound();
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My Firebase Push notification")
                        .setContentText(message)
                        .setAutoCancel(false)
                        .setSound(soundUri)
                        .setNumber(50);
                // .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, notificationBuilder.build());
                break;
        }
*/


    }


    private void playSound(String message) {

        int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
        if (volume == 0)
            volume = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        am.setStreamVolume(AudioManager.STREAM_ALARM, volume, AudioManager.FLAG_ALLOW_RINGER_MODES);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI));
        if (ringtone != null) {
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
            ringtone.play();
            isRinging = true;
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("My Firebase Push notification")
                    .setContentText(message)
                    .setAutoCancel(false)
                    .setSound(Uri.parse(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI))
                    .setNumber(50);
            // .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }

}