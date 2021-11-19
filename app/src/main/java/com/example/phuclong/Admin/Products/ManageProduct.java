package com.example.phuclong.Admin.Products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.Product;
import com.example.phuclong.ProductsViewHolder;
import com.example.phuclong.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ManageProduct extends AppCompatActivity {
    RecyclerView productlist;
    FloatingActionButton addproduct;
    FirebaseDatabase database;
    DatabaseReference Reference;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Product, ProductsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        database = FirebaseDatabase.getInstance();
        Reference = database.getReference("Products");

        matching();
        productlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productlist.setLayoutManager(layoutManager);
        loadProduct();
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageProduct.this, AddProduct.class);
                int size = adapter.getItemCount();
                intent.putExtra("size",size);
                startActivity(intent);
            }
        });

    }

    private void loadProduct() {
        adapter = new FirebaseRecyclerAdapter<Product, ProductsViewHolder>(Product.class,R.layout.products_item,ProductsViewHolder.class,Reference) {
            @Override
            protected void populateViewHolder(ProductsViewHolder productsViewHolder, Product product, int i) {
                Reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productsViewHolder.product_price.setText(product.getPrice());
                        productsViewHolder.product_name.setText(product.getName());
                        Picasso.with(getBaseContext()).load(product.getImage())
                                    .into(productsViewHolder.product_image);
                        productsViewHolder.setItemClinklistene(new ItemClinklistene() {
                            @Override
                            public void onClick(View view, int pos, boolean islongClick) {
                                Intent intent = new Intent(ManageProduct.this, Update_DeleteProduct.class);
                                intent.putExtra("productid",adapter.getRef(pos).getKey());
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("MANAGE PRODUCT","Error"+error.toString());
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        productlist.setAdapter(adapter);
    }

    private void matching() {
        productlist = (RecyclerView) findViewById(R.id.rv_menumanageproduct);
        addproduct = (FloatingActionButton) findViewById(R.id.fabtn_AddProduct);
    }
}