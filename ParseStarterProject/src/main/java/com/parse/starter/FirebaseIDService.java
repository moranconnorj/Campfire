package com.parse.starter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


//        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {



        // Add custom implementation, as needed.
        // f02JwzFl1VA:APA91bHlpVU8v1HkrnmTx2uowJpf7lH3ivAjeL3YbpqavsqCXI5_LnfHB5WVSRg56zECKz-Pq1nsXCTEcDQrTwt_BN6cSjygkc9g-qFOUZ317ylmUr0L_1QLP3qxuYjlASbxiCQ8Varx
    }
}
