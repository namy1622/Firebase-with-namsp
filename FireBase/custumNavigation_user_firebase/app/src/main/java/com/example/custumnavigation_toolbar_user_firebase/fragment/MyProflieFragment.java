package com.example.custumnavigation_toolbar_user_firebase.fragment;

//import static com.example.custumnavigation_toolbar_user_firebase.Manifest.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.custumnavigation_toolbar_user_firebase.MainActivity;
// import com.example.custumnavigation_toolbar_user_firebase.Manifest;
import com.example.custumnavigation_toolbar_user_firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MyProflieFragment extends Fragment {
    public  static final  int MY_REQUESTCODE = 10; // GIAS TRI DE TUY Y
    private Button btnUpdate;
    private EditText edtFullName, edtEmail;
    private ImageView imgAvatar;
    private View mView;

    private MainActivity mainActivity;

private Uri mUri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.my_proflie, container, false);
        initUI();

        setUserInfomation();

        init_Listener();


        return mView;
    }
    //--------------------------------------------------------------
    public   void setUserInfomation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_defalut).into(imgAvatar);
    }
    //--------------------------------------------------------------


    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void init_Listener(){
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });

    }
    //--------------------------------------------------------------
    private void onClickRequestPermission(){
        //mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            return;
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            Log.e("VERSION_CODES","---------------");
            return ;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Log.e("READ_EXTERNAL_STORAGE_ first","---------------");
            mainActivity.openGallery();
            Log.e("VERSION_CODES_last","---------------");

        }
        else{
            Log.e("VERSION_CODES_else","---------------");
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, MY_REQUESTCODE);
        }
    }
    //--------------------------------------------------------------
    // anh xa
    private void initUI(){
        btnUpdate = mView.findViewById(R.id.btn_update_profile);
        mainActivity = (MainActivity) getActivity();
        imgAvatar = mView.findViewById(R.id.img_avatar_profile);
        edtEmail = mView.findViewById(R.id.edt_email);
        edtFullName = mView.findViewById(R.id.edt_fullname);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         MainActivity mainActivity = (MainActivity) getActivity();
        if(requestCode == MY_REQUESTCODE){ // khi duoc cho phep
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mainActivity.openGallery();

            }
            else{
                // khi bi tu choi
            }
        }
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        if (imgAvatar != null) {
            imgAvatar.setImageBitmap(bitmapImageView);
        }
        else{
            Log.e("Error", "imgAvatar is null");
        }
    }

    private void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null ){
            return;
        }
        String strFullName = edtFullName.getText().toString().trim();

        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.e("Tag", "Uesr Proflie update");
                            Toast.makeText(getActivity(), "Uesr Proflie update success",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
