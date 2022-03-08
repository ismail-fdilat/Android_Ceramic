package com.shrinkcom.alsaadceramicapp.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKENTOKANN ",s);

    }
}
