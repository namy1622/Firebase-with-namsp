package com.example.pushnotification_fb;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    // khai báo hằng số public : dùng để định danh kênh thông báo
    public  static final String CHANNEL_ID = "push_notification_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification(); // phương thức tạo kênh thông báo
    }

    private void createChannelNotification() {
        // kiểm tra phiên bản HDH có phải là apk oreo(api26) trở lên không,
        // vì kênh thông báo chỉ được hỗ trợ từ api26 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // tạo đối tượng NotificationChannel với:  , ,
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,   //id kênh: CHANNEL_ID
                    "PushNotification", //tên kênh: PushNotification
                    NotificationManager.IMPORTANCE_DEFAULT); //độ quantrong DEFAULT
            //
            // lấy đối tượng NotificationManager từ hệ thông
            NotificationManager manager  = getSystemService((NotificationManager.class));
            manager.createNotificationChannel(channel); // tạo kênh thông báo bằng cách sd NotificationManager
        }
    }
}
