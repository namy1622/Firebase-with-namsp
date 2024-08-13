package com.example.pushnotification_fb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.InputStream;
import java.io.IOException;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d(TAG, "FCM Registration Token: " + token);

                    // Lưu hoặc gửi token đến máy chủ của bạn để gửi thông báo sau này
                    // sendTokenToServer(token);
                });

        new FetchAccessTokenTask().execute();
    }

    private class FetchAccessTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Open JSON file from resources
                InputStream stream = getResources().openRawResource(R.raw.a);
                GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                        .createScoped(Collections.singleton(MESSAGING_SCOPE));
                credentials.refreshIfExpired();

                // Get access token
                AccessToken accessToken = credentials.getAccessToken();
                Log.d("bearer", "Access Token: " + accessToken.getTokenValue());
            } catch (IOException e) {
                Log.d("bearer_error", e.toString());
            }
            return null;
        }
    }
}
