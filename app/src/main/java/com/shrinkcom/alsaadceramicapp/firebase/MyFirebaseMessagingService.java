package com.shrinkcom.alsaadceramicapp.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.Home;

import java.io.IOException;
import java.net.URL;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "detail";
    public static final String FCM_notificationtype = "notification_type";


    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("remoteMessagereeegdgdgdfgdg", ">>> " + remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Map<String, String> data = remoteMessage.getData();
        Log.e("REMOTEMESSAGE", ">>> " + notification.getBody());
        Log.e("REMOTEMESSAGE", ">>> " + notification.getTitle());
        Log.d("FROM", remoteMessage.getFrom());


        sendNotification(notification, data);

    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        // Bundle bundle = new Bundle();
        Intent intent = null;
        // bundle.putString(FCM_PARAM, data.get(FCM_PARAM));
        //  bundle.putString(FCM_notificationtype, data.get(FCM_notificationtype));

        Log.d("TAG", "sendNotification: "+data.get(FCM_notificationtype));

        intent = new Intent(this, Home.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        try {
            String picture = data.get(FCM_PARAM);
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.default_notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());


    }
}
