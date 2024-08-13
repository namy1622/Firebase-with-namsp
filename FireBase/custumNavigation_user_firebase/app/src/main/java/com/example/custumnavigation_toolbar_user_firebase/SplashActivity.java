/*
    ACTIVITY kiểm tra người dùng có tài khoản chưa
    nếu có (đã đăng nhập) --> log vào MainActivity
    nếu chưa có --> tạo tkhoan
 */

package com.example.custumnavigation_toolbar_user_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // khi mới chạy chtrinh thì delay 2s để người dùng xem nội dung màn hình ban đầu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }


        }, 2000);
    }

    private void nextActivity() {
        // check user da login chua
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){  // chu login
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }
        else{  // da login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}