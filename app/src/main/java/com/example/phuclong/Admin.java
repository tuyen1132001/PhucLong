package com.example.phuclong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    Button logOut;
    ListView lvUser;
    FirebaseFirestore store;
//    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        maching();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvUser.setAdapter(arrayAdapter);

        store = FirebaseFirestore.getInstance();
        DocumentReference df = store.collection("Users").document();
//        df.get().add

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void maching() {
        logOut = (Button) findViewById(R.id.btn_Admin_logout);
        lvUser = (ListView) findViewById(R.id.lv_user);
    }
}