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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    private  String TAG = "Verification";
    private EditText edt_phone_number;
    private Button btn_verify_phone_number;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        setTitleToolbar();
        initUI();

        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
        btn_verify_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumber = edt_phone_number.getText().toString().trim();

                onClickVerifyPhoneNumber(strPhoneNumber);
            }
        });

    }

    private  void onClickVerifyPhoneNumber(String strPhoneNumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                // hàm khi mã xác minh thành công

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                        // khi gửi mã xác minh thất bại
                                Log.e(TAG, e.toString());
                                Toast.makeText(VerifyPhoneNumberActivity.this, "VerificationFailure", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verification_ID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                // khi nhận được otp và phải tụ nhập( hàm bên trên là tự nhập được)

                                // verification_ID: là lượt của mã xác nhận ( ví dụ bấm send nhiều lần thì mỗi lần có 1 mã tương ứng có 1 id)
                                super.onCodeSent(verification_ID, forceResendingToken);

                                goTo_EnterOtpActivity(strPhoneNumber, verification_ID);
                            }


                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private  void initUI(){
        edt_phone_number = (EditText) findViewById(R.id.edt_phone_number);
        btn_verify_phone_number = (Button) findViewById(R.id.btn_verify_phone_number);
    }

    private void setTitleToolbar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Verify Phone Number");
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
                                Toast.makeText(VerifyPhoneNumberActivity.this, "The verigication code entered was invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
       // mAuth.setLanguageCode("vi");
    }

    private void goTo_MainActivity(String phoneNumber) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }

    private void goTo_EnterOtpActivity(String strPhoneNumber, String verificationId) {
        Intent intent = new Intent(this, EnterOtpActivity.class);
        intent.putExtra("phone_number", strPhoneNumber);
        intent.putExtra("verification_id", verificationId);

        startActivity(intent);
    }
}