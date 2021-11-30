package com.example.phuclong.Admin.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.R;
import com.example.phuclong.model.OrderAD;
import com.example.phuclong.ui.orderViewAd;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrderManager extends AppCompatActivity {
    RecyclerView OrderList;
    FirebaseDatabase databaseee;
    DatabaseReference Referenceee;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<OrderAD, orderViewAd> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        matching();
        databaseee = FirebaseDatabase.getInstance();
        Referenceee = databaseee.getReference("Order");

        OrderList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        OrderList.setLayoutManager(layoutManager);
        loadOrder();


    }

    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<OrderAD, orderViewAd>(OrderAD.class, R.layout.orderlayoutad, orderViewAd.class, Referenceee) {
            @Override
            protected void populateViewHolder(orderViewAd orderViewAd, OrderAD orderAD, int i) {
                Referenceee.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String id = adapter.getRef(i).getKey();
                        String address = "";
                        String phone = "";
                        String status = "";
                        for (DataSnapshot ttdh : snapshot.child(id).getChildren()) {
                            if (ttdh.getKey().equals("ThongTinDonHang")) {
                                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.child(id).child(ttdh.getKey()).child("TTGH").getValue();
                                address = hashMap.get("Address").toString();
                                phone = hashMap.get("NumberPhone").toString();
                                status = hashMap.get("Status").toString();


                                orderViewAd.OrderID.setText(adapter.getRef(i).getKey());
                                orderViewAd.Address.setText(address);
                                orderViewAd.Phone.setText(phone);
                                orderViewAd.Status.setText(status);


                            }
                        }


                        orderViewAd.setItemclickListener(new ItemClinklistene() {
                            @Override
                            public void onClick(View view, int pos, boolean islongClick) {
                                Intent intent = new Intent(OrderManager.this, OrderDetail_Delete.class);
                                intent.putExtra("OrderID", adapter.getRef(pos).getKey());
                                startActivity(intent);
                            }
                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("MANAGE PRODUCT", "Error" + error.toString());
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        OrderList.setAdapter(adapter);


    }


    private void matching() {
        OrderList = (RecyclerView) findViewById(R.id.lv_order);

    }
}