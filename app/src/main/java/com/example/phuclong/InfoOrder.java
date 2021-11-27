package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.phuclong.Admin.OrderMagagement.OrderDetail_Delete;
import com.example.phuclong.Admin.OrderMagagement.OrderManager;
import com.example.phuclong.model.OrderAD;
import com.example.phuclong.ui.orderViewAd;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class InfoOrder extends AppCompatActivity {

    RecyclerView OrderClientList;
    FirebaseDatabase database1;
    DatabaseReference Reference1;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<OrderAD, orderViewAd> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_order);
        matching();
        database1 = FirebaseDatabase.getInstance();
        Reference1 = database1.getReference("Order");


        OrderClientList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        OrderClientList.setLayoutManager(layoutManager);
        loadOrderClient();

        }

    private void loadOrderClient() {
            adapter = new FirebaseRecyclerAdapter<OrderAD, orderViewAd>(OrderAD.class,R.layout.orderlayoutad,orderViewAd.class,Reference1) {
                @Override
                protected void populateViewHolder(orderViewAd orderViewAd, OrderAD orderAD, int i) {
                    Reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = adapter.getRef(i).getKey();
                            String address = "";
                            String phone = "";
                            String status = "";
                            for (DataSnapshot ttdh:snapshot.child(id).getChildren()){
                                if (ttdh.getKey().equals("ThongTinDonHang")){
                                    HashMap<String,Object> hashMap = (HashMap<String,Object>) snapshot.child(id).child(ttdh.getKey()).child("TTGH").getValue();
                                    address = hashMap.get("Address").toString();
                                    phone = hashMap.get("NumberPhone").toString();
                                    status = hashMap.get("Status").toString();

                                    orderViewAd.OrderID.setText(adapter.getRef(i).getKey());
                                    orderViewAd.Address.setText(address);
                                    orderViewAd.Phone.setText(phone);
                                    orderViewAd.Status.setText(status);



                                    orderViewAd.setItemclickListener(new ItemClinklistene() {
                                        @Override
                                        public void onClick(View view, int pos, boolean islongClick) {
                                            Intent intent = new Intent(InfoOrder.this, Order_Detail_Client.class);
                                            intent.putExtra("OrderID",adapter.getRef(pos).getKey());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("MANAGE PRODUCT","Error"+error.toString());
                        }
                    });
                }

            };
            adapter.notifyDataSetChanged();
            OrderClientList.setAdapter(adapter);

        }


    private void matching() {
        OrderClientList = (RecyclerView) findViewById(R.id.rv_YourOrder);




    }
}