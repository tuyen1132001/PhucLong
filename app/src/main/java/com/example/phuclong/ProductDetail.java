package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TextView name,price,description;
    ImageView image;
    Button order,numberup,numberdown;
    FirebaseDatabase database;
    DatabaseReference reference;
    String productid ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        matching();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");
        if(getIntent()!=null)
            productid = getIntent().getStringExtra("productid");
        if(!productid.isEmpty()){
            reference.child(productid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Product product= snapshot.getValue(Product.class);
                    Picasso.with(getBaseContext()).load(product.getImage()).into(image);
                    name.setText(product.getName());
                    price.setText(product.getPrice());
                    description.setText(product.getDescription());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void matching() {
        name = (TextView) findViewById(R.id.tv_productdetail_name);
        price = (TextView) findViewById(R.id.tv_producdetail_tprice);
        description = (TextView) findViewById(R.id.tv_productdetail_description);
        order =(Button) findViewById(R.id.btn_productdetail_order);
        numberdown= (Button) findViewById(R.id.btn_productdetail_down);
        numberup = (Button)findViewById(R.id.btn_productdetail_up);
        image = (ImageView) findViewById(R.id.im_productdetail);
    }
}