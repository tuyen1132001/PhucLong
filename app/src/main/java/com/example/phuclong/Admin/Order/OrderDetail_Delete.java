package com.example.phuclong.Admin.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.R;
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

public class OrderDetail_Delete extends AppCompatActivity {

    TextView IDUserDetail, AddressDetail, PhoneDetail, TotalSumDetail;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recycc;
    Button cancel, delete,save;
    AutoCompleteTextView Status;
    ArrayAdapter<String> adapteritem;
    FirebaseDatabase database1;
    DatabaseReference Reference1;
    String OrderId = "";
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_delete);
        matching();


        recyclerView.setHasFixedSize(true);
        recycc = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recycc);


        Intent intent = getIntent();
        if (intent != null)
            OrderId = intent.getStringExtra("OrderID");
        if (!OrderId.isEmpty()) {
            database1 = FirebaseDatabase.getInstance();
            Reference1 = database1.getReference("Order");


            Reference1.child(OrderId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String IDUser = "";
                    String address = "";
                    String phone = "";
                    String status = "";
                    String Totalsum = "";
                    String[] itemstatus  = {"Chờ Duyệt","Đã Duyệt", "Đang Giao","Đã Hoàn Thành"};
                    for (DataSnapshot ttct : snapshot.getChildren()) {
                        if (ttct.getKey().equals("ThongTinDonHang")) {
                            HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.child(ttct.getKey()).child("TTGH").getValue();
                            IDUser = hashMap.get("IDUser").toString();
                            address = hashMap.get("Address").toString();
                            phone = hashMap.get("NumberPhone").toString();
                            status = hashMap.get("Status").toString();
                            Totalsum = hashMap.get("SumTotal").toString();

                            IDUserDetail.setText(IDUser);
                            AddressDetail.setText(address);
                            PhoneDetail.setText(phone);
                            Status.setText(status);
                            TotalSumDetail.setText(Totalsum);


                        }
                        adapteritem = new ArrayAdapter<String>(OrderDetail_Delete.this,R.layout.list_menu_item,itemstatus);
                        Status.setAdapter(adapteritem);

                    }
                    adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(Order.class, R.layout.order_layout, OrderViewHolder.class, Reference1.child(OrderId).child("Drink")) {
                        @Override
                        protected void populateViewHolder(OrderViewHolder orderViewHolder, Order order, int i) {
                            orderViewHolder.namee.setText(order.getProductName());
                            orderViewHolder.price.setText(order.getPrice());
                            orderViewHolder.quantity.setText(order.getQuantity());

                            Picasso.with(getBaseContext()).load(order.getImage()).into(orderViewHolder.imgview);
                        }
                    };
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reference1.child(OrderId).removeValue();
                    Toast.makeText(OrderDetail_Delete.this, "Xoá thành công ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   String changestatus= Status.getText().toString();
                    Reference1.child(OrderId).child("ThongTinDonHang").child("TTGH").child("Status").setValue(changestatus);
                    finish();
                }
            });
        }
    }

    private void matching() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_OrderDedetail);
        IDUserDetail = (TextView) findViewById(R.id.tv_IDUserDetail);
        AddressDetail = (TextView) findViewById(R.id.tv_Order_AddressDetail);
        PhoneDetail = (TextView) findViewById(R.id.tv_Order_PhoneDetail);
        TotalSumDetail = (TextView) findViewById(R.id.tv_Order_TotalSum);
        cancel = (Button) findViewById(R.id.btn_Order_Cancel);
        delete = (Button) findViewById(R.id.btn_Order_delete);
        save = (Button) findViewById(R.id.btn_Order_save) ;
        Status = (AutoCompleteTextView) findViewById(R.id.auto_tvstatus);

    }
}