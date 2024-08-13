package com.example.custumnavigation_toolbar_user_firebase;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.custumnavigation_toolbar_user_firebase.fragment.FravoriteFragment;
import com.example.custumnavigation_toolbar_user_firebase.fragment.HistoryFragment;
import com.example.custumnavigation_toolbar_user_firebase.fragment.HomeFragment;
import com.example.custumnavigation_toolbar_user_firebase.fragment.MyProflieFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final  int FRAGMENT_HOME = 0;
    private static final  int FRAGMENT_FRAVORITE = 1;
    private static final  int FRAGMENT_HISTORY = 2;
    private static final  int FRAGMENT_MyProflie = 3;
    private final MyProflieFragment mMyProfileFragment  = new MyProflieFragment();

private final ActivityResultLauncher<Intent>  mActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    if(intent == null){
                        return ;
                    }
                    Uri uri = intent.getData();
mMyProfileFragment.setmUri(uri);
                    try {
                        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        if (uri != null) {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            // tiếp tục xử lý bitmap...

                            if (mMyProfileFragment != null) {

                                mMyProfileFragment.setBitmapImageView(bitmap);
                            } else {
                                Log.e("Error", "mMyProfileFragment is null");
                            }
                        } else {
                            Log.e("Error", "Uri is null");
                        }

                        //mMyProfileFragment.setBitmapImageView(bitmap);



                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    private static final  int FRAGMENT_SignIn = 5;
//--------------------------------------------

   private NavigationView mNnavigationView;
    private ImageView img_avatar;
    private TextView txt_name, txt_email;


    //--------------------------------------------------


    private  int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   // add toolbar
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        // bắt sk khi click vào item của navigationView----------

        mNnavigationView.setNavigationItemSelectedListener(this);
        //----------------------------------------------------

        replaceFragment(new HomeFragment()); // khi bắt đầu chạy chươntrinh thì vào Home luôn
        mNnavigationView.getMenu().findItem(R.id.nav_home).setChecked(true); // khi khởi chạy thì Home hiển thị nhưng chưa được check nên phải setChecled

        showUserInfomation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }
        else if (id == R.id.nav_favorite){
            if(mCurrentFragment != FRAGMENT_FRAVORITE){
                replaceFragment(new FravoriteFragment());
                mCurrentFragment = FRAGMENT_FRAVORITE;
            }
        }
        else if (id == R.id.nav_history) {
            if(mCurrentFragment != FRAGMENT_HISTORY){
                replaceFragment(mMyProfileFragment);
                mCurrentFragment = FRAGMENT_HISTORY;
            }
        }
        else if (id == R.id.nav_proflie) {
            if(mCurrentFragment != FRAGMENT_MyProflie){
                replaceFragment(new MyProflieFragment());
                mCurrentFragment = FRAGMENT_MyProflie;
            }
        }
        else if (id == R.id.nav_change_password) {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }


    private void initUI(){
        mNnavigationView = findViewById(R.id.navigation_view);
        img_avatar = mNnavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        txt_email =(TextView) mNnavigationView.getHeaderView(0).findViewById(R.id.txt_email);
        txt_name =(TextView) mNnavigationView.getHeaderView(0).findViewById(R.id.txt_name);
    }

    // hamf lấy dl từ firebase -- hiển thị tt user lên header
    public void showUserInfomation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            return ;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUri = user.getPhotoUrl();

        txt_name.setText(name) ;
//        if(name == null){  // nếu name lấy về null
//            txt_name.setVisibility(View.GONE); // -> ẩn txt_name đi
//        }
//        else{
//            txt_name.setVisibility(View.VISIBLE);  // -> hiện lên
//            txt_name.setText(name) ;
//        }
        txt_email.setText(email);
        Glide.with(this).load(photoUri).error(R.drawable.ic_avatar_defalut).into(img_avatar);
    }

    public void openGallery(){
        Log.e("oenGallerry_start","---------------");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
        Log.e("oenGallerry_end","---------------");
    }


}