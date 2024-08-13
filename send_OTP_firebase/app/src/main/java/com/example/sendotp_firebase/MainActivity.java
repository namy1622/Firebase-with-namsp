package com.example.sendotp_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataIntent();
        setTitleToolbar();
    }

    private  void getDataIntent(){
        String strPhoneNumber = getIntent().getStringExtra("phone_number");

        TextView tvUserInfo = findViewById(R.id.tv_userInfo);
        tvUserInfo.setText(strPhoneNumber);
    }

    private void setTitleToolbar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Main Activity");
        }
    }
}