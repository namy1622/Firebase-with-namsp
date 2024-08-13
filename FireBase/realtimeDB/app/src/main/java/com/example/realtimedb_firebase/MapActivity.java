package com.example.realtimedb_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    private Button btn_get, btn_push, btn_update, btn_delete;
    private TextView tv_get ;

    //private  User user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Sử dụng findViewById trực tiếp trên Activity hiện tại
        btn_push = findViewById(R.id.btn_map_push);
        btn_get = findViewById(R.id.btn_map_get);
        btn_update = findViewById(R.id.btn_map_update);
        btn_delete = findViewById(R.id.btn_map_delete);
        tv_get = findViewById(R.id.tv_map_get);

        //--------------
        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPushData();
            }
        });
        //---------------
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGetData();
            }
        });
        //--------------
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateData();
            }
        });
    }

    private void onClickUpdateData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_map");
//  // cach 1
//        Map<String, Boolean> mapUpdate = new HashMap<>();
//        mapUpdate.put("1", true);
//        mapUpdate.put("2", true);
//        mapUpdate.put("3", false);
//        mapUpdate.put("4", true);
//
//        myRef.setValue(mapUpdate);

        myRef.child("1").setValue(false);

    }

    private void onClickGetData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_map");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Boolean> mapResult = new HashMap<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String key = snapshot1.getKey();
                    Boolean value = snapshot1.getValue(Boolean.class);

                    mapResult.put(key, value);
                }

                tv_get.setText(mapResult.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   }

    private void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_map");

        Map<String, Boolean> map = new HashMap<>();
        map.put("1", true);
        map.put("2", false);
        map.put("3", false);
        map.put("4", true);

        myRef.setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MapActivity.this, "Push data success",Toast.LENGTH_LONG).show();

            }
        });
    }
}