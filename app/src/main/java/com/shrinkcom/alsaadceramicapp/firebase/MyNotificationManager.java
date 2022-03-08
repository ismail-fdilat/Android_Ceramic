package com.shrinkcom.alsaadceramicapp.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


import androidx.core.app.NotificationCompat;

import com.shrinkcom.alsaadceramicapp.MainActivity;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.shrinkcom.alsaadceramicapp.firebase.Constants.CHANNEL_ID;


/**
 * Created by win 7 on 3/20/2018.
 */

public class MyNotificationManager {
    private Context mCtx;
    private static MyNotificationManager mInstance;

    public MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {
        Intent resultIntent;
        UserSession session = new UserSession(mCtx);
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.apiconnew)
                        .setContentTitle(title)
                        .setColor(R.color.colorAccent)
                        .setChannelId(CHANNEL_ID)
                        .setContentText(body);


        String strid = session.getInstance(mCtx).readPrefs(UserSession.PREFS_USERID);
        if (strid.equals("")){
             resultIntent = new Intent(mCtx, MainActivity.class);
        }else {
             resultIntent = new Intent(mCtx, Home.class);
            resultIntent.putExtra("notificationposition", 4);

        }
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotifyMgr != null;
            mBuilder.setChannelId(CHANNEL_ID);
            mNotifyMgr.createNotificationChannel(notificationChannel);
        }
//        assert mNotifyMgr != null;
//        mNotifyMgr.notify(1 /* Request Code */, mBuilder.build());



        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }




}
