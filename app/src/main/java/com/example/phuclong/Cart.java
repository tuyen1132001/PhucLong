package com.example.phuclong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.adapter.CartAdapter;
import com.example.phuclong.model.Order;
import com.example.phuclong.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.example.phuclong.adapter;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference reference;

    DatabaseReference request;



    TextView TotalPrice;
    int Tongtien = 0;
    Button Place;
    String cartId = "";
    String address = "123 pham dang giang";

    ImageView image;
    RecyclerView.Adapter adapter;
    List<Order> cart = new ArrayList<>();
//    CartAdapter adapter;

    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        matching();









        //Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Cart");


//        Bundle bundle = getIntent().getExtras();
//        iduser = bundle.getString("IDUser");
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        if (getIntent() != null)
            cartId = getIntent().getStringExtra("cartid");
        if (!cartId.isEmpty() && cartId != null) {
            loadlistProduct(cartId);


        }
        Place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Cart.this, OrderStatus.class);
                intent.putExtra("Cartid",cartId);
                startActivity(intent);

            }
        });

    }
    private void loadlistProduct(String cartId) {
        ArrayList<Order> listcart = new ArrayList<>();

        reference.child(cartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] data = snapshot.getValue().toString().split("[}],");
                String Key = "";
                String id = "";
//
                for (DataSnapshot datas : snapshot.getChildren()) {
                    id = datas.getKey();
                    Key = datas.getValue().toString();
                    String name = Key.substring(Key.indexOf("ProductName=") + 12, Key.indexOf(", Price"));
                    String gia = Key.substring(Key.indexOf("Price=") + 6, Key.indexOf(", Quantity="));
                    String soluong = Key.substring(Key.indexOf("Quantity=") + 9, Key.indexOf(", Image="));
                    String image = Key.substring(Key.indexOf("Image=") + 6);
                    listcart.add(new Order(name, image, soluong, gia, cartId, id + ""));
                    adapter = new CartAdapter(listcart, Cart.this);
                    recyclerView.setAdapter(adapter);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }







//            @Override
//            protected void populateViewHolder(ProductsViewHolder productsViewHolder, Product product, int i) {
//                cartViewHolder.product_namee.setText(product.getName());
//                cartViewHolder.product_pricee.setText(product.getPrice());
//                Picasso.with(getBaseContext()).load(product.getImage()).into(productsViewHolder.product_image);
//                final  Product local = product;
//                productsViewHolder.setItemClinklistene(new ItemClinklistene() {
//                    @Override
//                    public void onClick(View view, int pos, boolean islongClick) {
//                        Intent intent = new Intent(Productslist.this,ProductDetail.class);
//                        intent.putExtra("IDUser",iduser);
//                        intent.putExtra("productid",adapter.(pos).getKey());
//                        startActivity(intent);
//                    }





//        if(getIntent()!=null)
//           Categoryid  = getIntent().getStringExtra("categoryid");
//        if(!Categoryid.isEmpty()&& Categoryid !=null){
//            loadProduct(Categoryid);
//        }
////        cart = new Database(this).getCart();
//        adapter = new CartAdapter(cart,this);
//        recyclerView.setAdapter(adapter);
//
//        int total = 0;
//        for(Order order:cart)
//            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
//        Locale locale = new Locale("vi","VN");
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//
//        TotalPrice.setText(fmt.format(total));
//          int total = 0;
//        for(Order order:cart)
//            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
//        Locale locale = new Locale("vi","VN");
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//
//        TotalPrice.setText(fmt.format(total));


    private void matching() {
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        TotalPrice = (TextView) findViewById(R.id.tv_tongtien);
        image = (ImageView) findViewById(R.id.im_productsimagee);
        Place = (Button) findViewById(R.id.btn_dathang);


    }
}

