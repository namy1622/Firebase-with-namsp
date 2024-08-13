package com.example.realtimedb_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final MapActivity mapActi = new MapActivity();
    private  TextView tv_getdata;
    private EditText edt_data;
    private  Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_push = findViewById(R.id.btn_push);
        // edt_data = findViewById(R.id.edt_data);
         tv_getdata = findViewById(R.id.tv_get);
        Button btn_getdata = findViewById(R.id.btn_get);
        btn_delete = findViewById(R.id.btn_delete);
        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickPushData();
            }
        });
        
        btn_getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGetData();


            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteData();
            }
        });
        Button btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateData();
            }
        });

        Button btn_toActivity = findViewById(R.id.btn_to_activity);
        btn_toActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        Button btn_to_List = findViewById(R.id.btn_to_activity_list);
        btn_to_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_List_KT.class);
                startActivity(intent);
            }
        });
    }

 //   -----------------------------------------------------
    // ham update du lieu
    private void onClickUpdateData() {

//        // cach 1: ghi de data
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user_Object_inObject");
//
//        User user = new User(2, "nam", new Job(2, "job 2"));
//        myRef.setValue(user, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.this, "update data success",Toast.LENGTH_LONG).show();
//
//            }
//        });

        // cach 2: update key cu the
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user_info/name");
//         myRef.setValue("test_2", new DatabaseReference.CompletionListener() {
//             @Override
//             public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                 Toast.makeText(MainActivity.this, "update data success",Toast.LENGTH_LONG).show();
//
//             }
//         });

        // cach 3:
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_object_inObject");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "namhusky");
        map.put("job/name", "job_3");

        myRef.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Update data success",Toast.LENGTH_LONG).show();

            }
        });

    }

    //-----------------------------------------------------
    private void onClickDeleteData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test");

        // cach 1;
        //myRef.removeValue();  // khi xoas xong ko co thong bao

        // cach 2 xoa xong co thong bao
//        myRef.removeValue(new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.this, "Delete data success",Toast.LENGTH_LONG).show();
//            }
//        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Ban co muon xoa khong ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("user_info/name");
                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "delete data success",Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    //-----------------------------------------------------
    private void OnClickPushData() {
        //write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_info");

        User user = new User(1, "namsp");
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Push data success",Toast.LENGTH_LONG).show();
            }
        });

        // TH push object chua object
        DatabaseReference myRef2 = database.getReference("user_object_inObject");

        User user_2 = new User(1, "namsp", new Job(1, "it"));
        myRef2.setValue(user_2);
      //  DatabaseReference myRef = database.getReference("message");
//        myRef.setValue(edt_data.getText().toString().trim(), new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MainActivity.this, "Push data success",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        DatabaseReference myRef_2 = database.getReference("test");
//        myRef_2.setValue("namsp");
//
//        DatabaseReference myRef_3 = database.getReference("check");
//        myRef_3.setValue(true);
//
//        DatabaseReference myRef_4 = database.getReference("check_1/check_1_1/check_1_1_1");
//        myRef_4.setValue("check1.1");
    }
    //-----------------------------------------------------
    private void  onClickGetData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_info");
        //read from the database
        myRef.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                tv_getdata.setText(user.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}