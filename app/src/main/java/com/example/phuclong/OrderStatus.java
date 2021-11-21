package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

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

public class OrderStatus extends AppCompatActivity {
    String cartId = "";
    EditText sdt,address;
    Button buy;
    String key;

    RecyclerView recyclerView ;
    RecyclerView.LayoutManager recyc ;

    FirebaseDatabase database;
    DatabaseReference referencee;

    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        maching();
        recyclerView.setHasFixedSize(true);
        recyc = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyc);

        if (getIntent() != null)
            cartId = getIntent().getStringExtra("Cartid");
        if (!cartId.isEmpty() && cartId != null) {
            database = FirebaseDatabase.getInstance();
            referencee = database.getReference("Cart");
            referencee.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    key = snapshot.getKey();
                    adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class,R.layout.order_layout,OrderViewHolder.class,referencee.child(key).equalTo(cartId)) {
                        @Override
                        protected void populateViewHolder(OrderViewHolder orderViewHolder, Order order, int i) {
                            referencee.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    orderViewHolder.namee.setText(order.getProductName());
                                    orderViewHolder.price.setText(order.getPrice());
                                    orderViewHolder.quantity.setText(order.getQuantity());

                                    Picasso.with(getBaseContext()).load(order.getImage()).into(orderViewHolder.imgview);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        }
                    };
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }




    }

    private void maching() {
        sdt = (EditText) findViewById(R.id.et_sdt);
        address = (EditText) findViewById(R.id.et_address);
        buy = (Button) findViewById(R.id.btn_buy);
        recyclerView = (RecyclerView) findViewById(R.id.OrderStatus);
    }
}