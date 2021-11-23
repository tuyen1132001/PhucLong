package com.example.phuclong.Admin.InfoUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phuclong.Administrator;
import com.example.phuclong.R;
import com.example.phuclong.adapter.InforlistAdapter;
import com.example.phuclong.ui.InforCustomer.InforlistDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerInforCustomerActivity extends AppCompatActivity {
    ImageView cancel;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_infor_customer);
        matching();
        load();
    }
    private void load() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<InforlistDomain> list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("InforUser");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) data.getValue();
                    if(hashMap.get("HinhDaiDien") != null)
                        list.add(new InforlistDomain(data.getKey(), hashMap.get("Email").toString(), hashMap.get("Name").toString(), hashMap.get("HinhDaiDien").toString()));
                    else
                        list.add(new InforlistDomain(data.getKey(), hashMap.get("Email").toString(), hashMap.get("Name").toString(), "ic_baseline_account_circle_24"));
                }
                adapter = new InforlistAdapter(ManagerInforCustomerActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void matching() {
        cancel = (ImageView) findViewById(R.id.img_managerInfor_list_back);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_managerInfor_list);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerInforCustomerActivity.this, Administrator.class));
                finish();
            }
        });
    }
}