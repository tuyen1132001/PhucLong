package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Productslist extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database ;
    DatabaseReference reference;
    String Categoryid="";
    FirebaseRecyclerAdapter<Product,ProductsViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productslist);
        database= FirebaseDatabase.getInstance();
        reference= database.getReference("Products");
        matching();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null)
            Categoryid = getIntent().getStringExtra("categoryid");
        if(!Categoryid.isEmpty()&& Categoryid !=null){
            loadProduct(Categoryid);
        }
    }

    private void loadProduct(String categoryid) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductsViewHolder>(Product.class,R.layout.products_item,ProductsViewHolder.class,reference.orderByChild("categoryid").equalTo(categoryid)) {
            @Override
            protected void populateViewHolder(ProductsViewHolder productsViewHolder, Product product, int i) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productsViewHolder.product_name.setText(product.getName());
                        productsViewHolder.product_price.setText(product.getPrice());
                        Picasso.with(getBaseContext()).load(product.getImage()).into(productsViewHolder.product_image);
                        final  Product local = product;
                        productsViewHolder.setItemClinklistene(new ItemClinklistene() {
                            @Override
                            public void onClick(View view, int pos, boolean islongClick) {
                                Intent intent = new Intent(Productslist.this,ProductDetail.class);
                                intent.putExtra("productid",adapter.getRef(pos).getKey());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void matching() {
        recyclerView= (RecyclerView) findViewById(R.id.rv_productlist);
    }

}