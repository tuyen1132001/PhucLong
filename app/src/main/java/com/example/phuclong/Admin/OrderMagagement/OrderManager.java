package com.example.phuclong.Admin.OrderMagagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.phuclong.Admin.Products.ManageProduct;
import com.example.phuclong.Admin.Products.Update_DeleteProduct;
import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.Product;
import com.example.phuclong.ProductsViewHolder;
import com.example.phuclong.R;
import com.example.phuclong.model.Order;
import com.example.phuclong.model.OrderAD;
import com.example.phuclong.ui.slideshow.orderViewAd;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderManager extends AppCompatActivity {
    RecyclerView OrderList;
    FirebaseDatabase databasee;
    DatabaseReference Referencee;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<OrderAD, orderViewAd> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        matching();
        databasee = FirebaseDatabase.getInstance();
        Referencee = databasee.getReference("Order");
        OrderList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        OrderList.setLayoutManager(layoutManager);
        loadOrder();


    }

    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<OrderAD, orderViewAd>(OrderAD.class,R.layout.orderlayoutad,orderViewAd.class,Referencee) {
            @Override
            protected void populateViewHolder(orderViewAd orderViewAd, OrderAD orderAD, int i) {
                Referencee.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderViewAd.OrderIDUser.setText(orderAD.getOrderID());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("MANAGE PRODUCT","Error"+error.toString());
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