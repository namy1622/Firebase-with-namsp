package com.example.custumnavigation_toolbar_user_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    private ProgressDialog progrressDialog;
    private LinearLayout layoutSignUp;
    private EditText email, password;
    private Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUI();

        initListener();
    }

    private void initUI() {
        email = findViewById(R.id.edt_email_signIn);
        password = findViewById(R.id.edt_password_signIn);
        btn_signIn = findViewById(R.id.btn_sign_in);
        progrressDialog = new ProgressDialog(this);
        layoutSignUp = findViewById(R.id.layout_sign_up);
    }

    private void initListener() {
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_SignIn();
            }
        });

    }

    private void onClick_SignIn() {
        progrressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progrressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.e("SignIn(successful)", "SignIn_ThanhCong");
                            FirebaseUser user = auth.getCurrentUser();

                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);

                            finishAffinity();   // đóng all activity trước MainActivity

                        } else {
                            Log.e("SignIn(Fail)", "SignIn_ThatBai- " + task.getException());
                            Toast.makeText(SigninActivity.this,"SignIn_ThatBai- ", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}