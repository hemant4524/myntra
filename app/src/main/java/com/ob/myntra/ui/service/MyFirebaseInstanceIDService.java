package com.ob.myntra.ui.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
 
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
 
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token"+refreshedToken);
      //  Toast.makeText(getApplicationContext(), "Refreshed token"+refreshedToken, Toast.LENGTH_SHORT).show();
        sendRegistrationToServer(refreshedToken);
    }
 
    private void sendRegistrationToServer(String token) {
        // save the token
    }
}