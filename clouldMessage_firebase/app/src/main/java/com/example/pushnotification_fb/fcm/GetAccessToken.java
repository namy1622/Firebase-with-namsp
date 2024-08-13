//package com.example.pushnotification_fb.fcm;//package com.example.pushnotification_fb.fcm;
////
////import com.google.auth.oauth2.GoogleCredentials;
////import okhttp3.MediaType;
////import okhttp3.OkHttpClient;
////import okhttp3.Request;
////import okhttp3.RequestBody;
////import okhttp3.Response;
////
////import java.io.FileInputStream;
////import java.io.IOException;
////import java.util.Collections;
////
////public class FcmApiV1 {
////    private static final String PROJECT_ID = "pushnotification-caead";
////    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
////    private static final String FCM_SEND_ENDPOINT = "https://fcm.googleapis.com/v1/projects/" + PROJECT_ID + "/messages:send";
////    private static final String KEY_PATH = "E:\\androi_ass\\FIRE_BASE\\pushnotification-caead-firebase-adminsdk-pxln7-fe6fcafeba.json";
////
////    public static void main(String[] args) {
////        try {
////            String message = "{"
////                    + "  \"message\":{"
////                    + "    \"token\":\"cBjGQSc7TueogTljhLd_uj:APA91bGfCTiAYGwHzRj0VnhvpCtHS4nAf72dh5JqgZEMr-nAv3W1kSpDM2m_TBmjeW1NR733AkoLJAYhSy-Wuxi5JHOPvMMwW6jJj46ej0z5mnyvxhgrNFz0ARX-7CCvliTMqpC-VVNj\","
////                    + "    \"notification\":{"
////                    + "      \"title\":\"Hello\","
////                    + "      \"body\":\"World\""
////                    + "    }"
////                    + "  }"
////                    + "}";
////
////            // Load service account credentials
////            GoogleCredentials googleCredentials = GoogleCredentials
////                    .fromStream(new FileInputStream(KEY_PATH))
////                    .createScoped(Collections.singleton(MESSAGING_SCOPE));
////            googleCredentials.refreshIfExpired();
////
////            String accessToken = googleCredentials.getAccessToken().getTokenValue();
////
////            // Send FCM message
////            OkHttpClient client = new OkHttpClient();
////            MediaType JSON = MediaType.get("application/json; charset=utf-8");
////            RequestBody requestBody = RequestBody.create(message, JSON);
////            Request request = new Request.Builder()
////                    .url(FCM_SEND_ENDPOINT)
////                    .post(requestBody)
////                    .addHeader("Authorization", "Bearer " + accessToken)
////                    .addHeader("Content-Type", "application/json; UTF-8")
////                    .build();
////
////            Response response = client.newCall(request).execute();
////            System.out.println("Response code: " + response.code());
////            System.out.println("Response body: " + response.body().string());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////}
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.auth.oauth2.AccessToken;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Collections;
//
//public class GetAccessToken {
//
//    private static final String KEY_PATH = "E:\\androi_ass\\FIRE_BASE\\pushnotification-caead-firebase-adminsdk-pxln7-fe6fcafeba.json";
//    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
//
//    public static void main(String[] args) {
//        try {
//            // Load service account credentials
//            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(KEY_PATH))
//                    .createScoped(Collections.singleton(MESSAGING_SCOPE));
//            credentials.refreshIfExpired();
//
//            // Get access token
//            AccessToken accessToken = credentials.getAccessToken();
//            System.out.println("Access Token: " + accessToken.getTokenValue());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
