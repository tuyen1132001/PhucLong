package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phuclong.model.Order;
import com.example.phuclong.ui.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                    adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class, R.layout.order_layout, OrderViewHolder.class, referencee.child(cartId)) {
                        @Override
                        protected void populateViewHolder(OrderViewHolder orderViewHolder, Order order, int i) {

                            referencee.child(cartId).addListenerForSingleValueEvent(new ValueEventListener() {
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
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase databaseOrder = FirebaseDatabase.getInstance();
                DatabaseReference referOrder = databaseOrder.getReference("Order");
                referOrder.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");
                        String maDonHang = myDateObj.format(myFormatObj);
                        FirebaseDatabase data = FirebaseDatabase.getInstance();
                        DatabaseReference ref = data.getReference("Cart");
                        if (snapshot.getValue() == null) {
                            ref.child(cartId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int i = 0;
                                    int sum = 0;
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                        String datas = dataSnapshot.getValue().toString();
                                        String ProductName = datas.substring(datas.indexOf("ProductName=") + 12, datas.indexOf(", Price"));
                                        String Price = datas.substring(datas.indexOf("Price=") + 6, datas.indexOf(", Quantity"));
                                        String Quantity = datas.substring(datas.indexOf("Quantity=") + 9, datas.indexOf(", Image"));
                                        String Image = datas.substring(datas.indexOf("Image=") + 6);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i)).child("ProductName").setValue(ProductName);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i)).child("Price").setValue(Price);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i)).child("Quantity").setValue(Quantity);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i)).child("Image").setValue(Image);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i)).child("Total").setValue((Integer.valueOf(Price)) + "");
                                        sum += (Integer.valueOf(Price) );
                                        i++;


                                    }
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("Address").setValue(address.getText().toString());
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("Status").setValue("Chờ Duyệt");
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("NumberPhone").setValue(sdt.getText().toString());
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("SumTotal").setValue(String.valueOf(sum));
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("IDUser").setValue(cartId);


                                    ref.child(cartId).removeValue();
                                    Intent intent = new Intent(OrderStatus.this, Home.class);
                                    intent.putExtra("IDUser", cartId);
                                    startActivity(intent);

                                    Toast.makeText(OrderStatus.this, "Đặt Hàng Thành Công !!!", Toast.LENGTH_SHORT).show();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            ref.child(cartId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int i = 0;
                                    int sum = 0;
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                        String datas = dataSnapshot.getValue().toString();
                                        String ProductName = datas.substring(datas.indexOf("ProductName=") + 12, datas.indexOf(", Price"));
                                        String Price = datas.substring(datas.indexOf("Price=") + 6, datas.indexOf(", Quantity"));
                                        String Quantity = datas.substring(datas.indexOf("Quantity=") + 9, datas.indexOf(", Image"));
                                        String Image = datas.substring(datas.indexOf("Image=") + 6);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i) ).child("ProductName").setValue(ProductName);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i) ).child("Price").setValue(Price);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i) ).child("Quantity").setValue(Quantity);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i) ).child("Image").setValue(Image);
                                        referOrder.child(maDonHang).child("Drink").child(String.valueOf(i) ).child("Total").setValue((Integer.valueOf(Price) * Integer.valueOf(Quantity)) + "");
                                        sum += (Integer.valueOf(Price));
                                        i++;


                                    }
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("Address").setValue(address.getText().toString());
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("Status").setValue("Chờ Duyệt");
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("NumberPhone").setValue(sdt.getText().toString());
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("SumTotal").setValue(String.valueOf(sum));
                                    referOrder.child(maDonHang).child("ThongTinDonHang").child("TTGH").child("IDUser").setValue(cartId);


                                    ref.child(cartId).removeValue();
                                    Intent intent = new Intent(OrderStatus.this, Home.class);
                                    intent.putExtra("IDUser", cartId);
                                    startActivity(intent);

                                    Toast.makeText(OrderStatus.this, "Đặt Hàng Thành Công !!!", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void maching() {
        sdt = (EditText) findViewById(R.id.et_sdt);
        address = (EditText) findViewById(R.id.et_address);
        buy = (Button) findViewById(R.id.btn_buy);
        recyclerView = (RecyclerView) findViewById(R.id.OrderStatus);
    }
}