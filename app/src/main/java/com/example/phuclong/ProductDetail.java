package com.example.phuclong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TextView name,price,description,quantitydisplay;
    ImageView image;
    Button order,numberup,numberdown;
    FirebaseDatabase database;
    DatabaseReference reference;

    String productid,totalprice ="";
    int quantity = 0;
    Product currentProduct;
    String iduser;
    String idgh = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Bundle bundle = getIntent().getExtras();
        iduser = bundle.getString("IDUser");
        matching();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Products");
        if(getIntent()!=null)
            productid = getIntent().getStringExtra("productid");
        if(!productid.isEmpty()){
            reference.child(productid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentProduct= snapshot.getValue(Product.class);
                    Picasso.with(getBaseContext()).load(currentProduct.getImage()).into(image);
                    name.setText(currentProduct.getName());
                    price.setText(currentProduct.getPrice());
                    totalprice = currentProduct.getPrice();
                    description.setText(currentProduct.getDescription());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        numberup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                quantitydisplay.setText("" + quantity);
                price.setText((Integer.valueOf(totalprice)*Integer.valueOf(quantitydisplay.getText().toString())) + "");

            }

        });
        numberdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity == 0){
                }else{
                    quantity--;
                }
                quantitydisplay.setText(""+quantity);
                price.setText((Integer.valueOf(totalprice)*Integer.valueOf(quantitydisplay.getText().toString())) + "");

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase databasee = FirebaseDatabase.getInstance();
                DatabaseReference referencee = databasee.getReference("Cart");

                referencee.child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            referencee.child(iduser).child("0").child("ProductName").setValue(name.getText().toString());
                            referencee.child(iduser).child("0").child("Image").setValue(currentProduct.getImage());
                            referencee.child(iduser).child("0").child("Quantity").setValue(quantitydisplay.getText().toString());
                            referencee.child(iduser).child("0").child("Price").setValue(price.getText().toString());

                        } else {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                idgh = dataSnapshot.getKey();

                            }

                            referencee.child(iduser).child((Integer.valueOf(idgh) + 1)+"").child("ProductName").setValue(name.getText().toString());
                            referencee.child(iduser).child((Integer.valueOf(idgh) + 1)+"").child("Image").setValue(currentProduct.getImage());
                            referencee.child(iduser).child((Integer.valueOf(idgh) + 1)+"").child("Quantity").setValue(quantitydisplay.getText().toString());
                            referencee.child(iduser).child((Integer.valueOf(idgh) + 1)+"").child("Price").setValue(price.getText().toString());
                            Toast.makeText(ProductDetail.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(ProductDetail.this,Cart.class);
                        intent.putExtra("cartid",iduser);
                        startActivity(intent);
                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//                new Database(getBaseContext()).addToCart(new Order(
//                        productid,
//                        currentProduct.getName(),
//                        quantitydisplay.toString(),
//                        currentProduct.getPrice(),
//                        currentProduct.getDiscourt()
//
//                ));
//
//                Toast.makeText(ProductDetail.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void matching() {
        name = (TextView) findViewById(R.id.tv_productdetail_name);
        price = (TextView) findViewById(R.id.tv_producdetail_tprice);
        description = (TextView) findViewById(R.id.tv_productdetail_description);
        quantitydisplay = (TextView) findViewById(R.id.quantityDETAILnUMBER);
        order =(Button) findViewById(R.id.btn_productdetail_order);
        numberdown= (Button) findViewById(R.id.btn_productdetail_down);
        numberup = (Button)findViewById(R.id.btn_productdetail_up);
        image = (ImageView) findViewById(R.id.im_productdetail);
    }
}