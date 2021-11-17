package com.example.phuclong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Productslist extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database ;
    DatabaseReference reference;
    String Categoryid="";
    FirebaseRecyclerAdapter<Product,ProductsViewHolder> adapter;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productslist);
        Bundle bundle = getIntent().getExtras();
        iduser = bundle.getString("IDUser");
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
                productsViewHolder.product_name.setText(product.getName());
                productsViewHolder.product_price.setText(product.getPrice());
                Picasso.with(getBaseContext()).load(product.getImage()).into(productsViewHolder.product_image);
                final  Product local = product;
                productsViewHolder.setItemClinklistene(new ItemClinklistene() {
                    @Override
                    public void onClick(View view, int pos, boolean islongClick) {
                        Intent intent = new Intent(Productslist.this,ProductDetail.class);
                        intent.putExtra("IDUser",iduser);
                        intent.putExtra("productid",adapter.getRef(pos).getKey());
                        startActivity(intent);
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