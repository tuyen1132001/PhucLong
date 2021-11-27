package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.phuclong.model.Order;
import com.example.phuclong.ui.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Order_Detail_Client extends AppCompatActivity {
    TextView AddressClient, PhoneClient,StatusClient,TotalClient;
    RecyclerView ProductOrder;
    RecyclerView.LayoutManager recycc ;

    FirebaseDatabase database1;
    DatabaseReference Reference1;
    String OrderId="";
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_client);
        maching();
        ProductOrder.setHasFixedSize(true);
        recycc = new LinearLayoutManager(this);
        ProductOrder.setLayoutManager(recycc);

        Intent intent = getIntent();
        if(intent !=null)
            OrderId = intent.getStringExtra("OrderID");
        if(!OrderId.isEmpty()) {
            database1 = FirebaseDatabase.getInstance();
            Reference1 = database1.getReference("Order");


            Reference1.child(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String address = "";
                    String phone = "";
                    String status = "";
                    String Totalsum = "";
                    for (DataSnapshot ttct : snapshot.getChildren()) {
                        if (ttct.getKey().equals("ThongTinDonHang")) {
                            HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.child(ttct.getKey()).child("TTGH").getValue();
                            address = hashMap.get("Address").toString();
                            phone = hashMap.get("NumberPhone").toString();
                            status = hashMap.get("Status").toString();
                            Totalsum = hashMap.get("SumTotal").toString();


                            AddressClient.setText(address);
                            PhoneClient.setText(phone);
                            StatusClient.setText(status);
                            TotalClient.setText(Totalsum);


                        }
                        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class, R.layout.order_layout, OrderViewHolder.class, Reference1.child(OrderId).child("Drink")) {
                            @Override
                            protected void populateViewHolder(OrderViewHolder orderViewHolder, Order order, int i) {
                                orderViewHolder.namee.setText(order.getProductName());
                                orderViewHolder.price.setText(order.getPrice());
                                orderViewHolder.quantity.setText(order.getQuantity());

                                Picasso.with(getBaseContext()).load(order.getImage()).into(orderViewHolder.imgview);
                            }
                        };
                        ProductOrder.setAdapter(adapter);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    private void maching() {
        AddressClient = (TextView) findViewById(R.id.tv_address_Client);
        PhoneClient = (TextView) findViewById(R.id.tv_phone_client);
        StatusClient = (TextView) findViewById(R.id.tv_status_client);
        TotalClient = (TextView) findViewById(R.id.tv_Total_Client);
        ProductOrder = (RecyclerView) findViewById(R.id.rv_Product_Client);



    }
}