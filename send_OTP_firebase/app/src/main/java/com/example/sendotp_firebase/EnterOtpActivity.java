package com.example.sendotp_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOtpActivity extends AppCompatActivity {
public  static  final String TAG = EnterOtpActivity.class.getName();
FirebaseAuth mAuth;
    private EditText edt_otp;
    private Button btn_send_otp;
    private Button btn_sent_again;

    private String mPhoneNumber;
    private String mVerification_ID;

    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        getDataIntent();
        setTitleToolbar();
        initUI();

        mAuth = FirebaseAuth.getInstance();

        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOtp = edt_otp.getText().toString().trim();
                onClickSendOtpCode(strOtp);
            }
        });

        btn_sent_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSentOtpAgain();
            }
        });
    }

    private void getDataIntent(){
            mPhoneNumber = getIntent().getStringExtra("phone_number");
            mVerification_ID = getIntent().getStringExtra("verification_id");
    }

    private void onClickSendOtpCode(String strOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification_ID, strOtp);
        signInWithPhoneAuthCredential(credential);

    }

    private void onClickSentOtpAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // hàm khi mã xác minh thành công

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // khi gửi mã xác minh thất bại
                                Toast.makeText(EnterOtpActivity.this, "VerificationFailure", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verification_ID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // khi nhận được otp và phải tụ nhập( hàm bên trên là tự nhập được)

                                // verification_ID: là lượt của mã xác nhận ( ví dụ bấm send nhiều lần thì mỗi lần có 1 mã tương ứng có 1 id)
                                super.onCodeSent(verification_ID, forceResendingToken);

                                mVerification_ID = verification_ID;

                                mForceResendingToken = forceResendingToken;
                               // goTo_EnterOtpActivity(strPhoneNumber, verification_ID);
                            }


                        })
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void initUI() {
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        btn_send_otp = (Button) findViewById(R.id.btn_send_otp_code);
        btn_sent_again = (Button) findViewById(R.id.btn_send_again);

    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Send OTP");
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "signInWithCredential: success");

                            FirebaseUser user = task.getResult().getUser();

                            assert user != null;
                            goTo_MainActivity(user.getPhoneNumber());
                        }
                        else{
                            Log.e(TAG, "signInWithCredential: failure", task.getException());

                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(EnterOtpActivity.this, "The verigication code entered was invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    private void goTo_MainActivity(String phoneNumber) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}