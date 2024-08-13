package com.example.pushnotification_fb.fcm;

;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.pushnotification_fb.MainActivity;
import com.example.pushnotification_fb.MyApplication;
import com.example.pushnotification_fb.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagningService extends FirebaseMessagingService {
    public static final String TAG = MyFirebaseMessagningService.class.getName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) { // pt này được gọi khi NHẬN ĐƯỢC TIN NHẮN TỪ FCM
        super.onMessageReceived(message);

        // nhận data từ firebase( lấy thông tin thông báo từ tin nhắn fcm)
   // Lấy thông báo từ payload
        RemoteMessage.Notification notification = message.getNotification();
        if (notification != null) {
            Log.e ("if", " ..................");
            String title = notification.getTitle();
            String body = notification.getBody();
            sendNotification(title, body);
        } else {
            // Lấy dữ liệu từ payload
            Map<String, String> data = message.getData();
            String title = data.get("title");
            String body = data.get("body");
            sendNotification(title, body);
        }
    }

    // pt để tạo và hiển thị thông báo
    private void sendNotification(String stringTitle, String strMessage) {
        Intent intent = new Intent(this, MainActivity.class);  // tạo 1 intent để MỞ mainActivity KHI NHẤN VÀO THÔNG BÁO
        // đính kèm vào thông báo
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifycationBuilder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentText(strMessage)
                .setContentTitle(stringTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        Notification notification= notifycationBuilder.build();  // Xây dựng thongbao từ NotificationCompat.Builder
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager != null){
            notificationManager.notify(1, notification);
        }
    }

    // trae về 1 String ( mỗi máy có 1 Token, để server xác định device nhận push)
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e(TAG, token);
    }
}


/*
Intent
Khái niệm
- Intent trong Android là một đối tượng mô tả một hành động để thực hiện và các dữ liệu cần thiết để thực hiện hành động đó.
- Intent thường được sử dụng để khởi động một Activity, Service, hoặc gửi broadcast.
Cách sử dụng

Intent intent = new Intent(this, MainActivity.class);
//---------------------------------------

PendingIntent
Khái niệm
- PendingIntent là một đối tượng bao bọc một Intent để nó có thể được thực thi sau bởi một ứng dụng khác.
- Điều này rất hữu ích trong các trường hợp bạn muốn một ứng dụng khác thực thi một hành động của bạn trong tương lai, chẳng hạn như khi một thông báo được nhấn.
Cách sử dụng

PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
 */