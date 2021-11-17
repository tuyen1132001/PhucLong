package com.example.phuclong;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.adapter.CartAdapter;
import com.example.phuclong.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//import com.example.phuclong.adapter;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference reference;

    TextView TotalPrice;
    Button Place;
    String cartId = "";
    RecyclerView.Adapter adapter;


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

    }


    private void loadlistProduct(String cartId) {
        ArrayList<Order> listcart = new ArrayList<>();

        reference.child(cartId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] data = snapshot.getValue().toString().split("[}],");

                for (int i = 0; i < data.length - 1; i++) {
                    String name = data[i].substring(data[i].indexOf("ProductName=") + 12, data[i].indexOf(", Price="));
                    String gia = data[i].substring(data[i].indexOf("Price=") + 6, data[i].indexOf(", Quantity="));
                    String soluong = data[i].substring(data[i].indexOf("Quantity=") + 9);
                    listcart.add(new Order(name, "", soluong, gia, ""));
                    adapter = new CartAdapter(listcart, Cart.this);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
    }




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


    private void matching() {
        recyclerView = (RecyclerView) findViewById(R.id.listCart);


    }
}

