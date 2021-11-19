package com.example.phuclong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.Cart;
import com.example.phuclong.R;
import com.example.phuclong.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    int quantity = 0;
    String tongtien = "";
    TextView txt_price,txt_quantity;
    FirebaseDatabase database;




    private ArrayList<Order> listdata;
    private Context context;

    public CartAdapter(ArrayList<Order> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Order order = listdata.get(position);
        if(order == null){
            return;
        }

        Locale locale = new Locale("vi", "VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        holder.txt_price.setText(order.getPrice());
        holder.txt_cart_name.setText(order.getProductName());
        Picasso.with(context.getApplicationContext()).load(order.getImage()).into(holder.img_cart_count);


//        Picasso.with(context).load(order.getImage()).into(holder.img_cart_count);
//        quantity = Integer.parseInt(listdata.get(position).getQuantity());
//        if(quantity == 1){
//            holder.txt_quantity.setText(order.getQuantity());
//        }
        holder.txt_quantity.setText(order.getQuantity());


        holder.tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart");
                reference.child(order.getIdCart()).child(order.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap <String, Object > hashMap = (HashMap<String, Object>) snapshot.getValue();
                        int tong = (Integer.parseInt(hashMap.get("Price").toString())/ Integer.parseInt(hashMap.get("Quantity").toString()))*(Integer.parseInt(hashMap.get("Quantity").toString())+1);
                        reference.child(order.getIdCart()).child(order.getId()).child("Price").setValue(tong+"");
                        reference.child(order.getIdCart()).child(order.getId()).child("Quantity").setValue((Integer.parseInt(hashMap.get("Quantity").toString())+1)+"");
                        holder.txt_quantity.setText((Integer.parseInt(hashMap.get("Quantity").toString())+1)+"");
                        holder.txt_price.setText(tong+"");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });
        holder.giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart");
                reference.child(order.getIdCart()).child(order.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap <String, Object > hashMap = (HashMap<String, Object>) snapshot.getValue();

                        int tong = (Integer.parseInt(hashMap.get("Price").toString())/ Integer.parseInt(hashMap.get("Quantity").toString()))*(Integer.parseInt(hashMap.get("Quantity").toString())-1);                        reference.child(order.getIdCart()).child(order.getId()).child("Price").setValue(tong+"");
                        reference.child(order.getIdCart()).child(order.getId()).child("Quantity").setValue((Integer.parseInt(hashMap.get("Quantity").toString())-1)+"");
                        holder.txt_quantity.setText((Integer.parseInt(hashMap.get("Quantity").toString())-1)+"");
                        holder.txt_price.setText(tong+"");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        });



        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cart");
                reference.child(order.getIdCart()).child(order.getId()).removeValue();
                ((Cart)context).finish();


            }
        });



    }





    @Override
    public int getItemCount() {
        if(listdata != null)
            return listdata.size();
        return 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_cart_name, txt_price, txt_quantity;
        public ImageView img_cart_count;
        public Button giam, tang, xoa;




        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cart_name = (TextView) itemView.findViewById(R.id.tv_productnamee);
            txt_price = (TextView) itemView.findViewById(R.id.tv_productpricee);
            txt_quantity = (TextView) itemView.findViewById(R.id.tv_productquantityy);
            img_cart_count = (ImageView) itemView.findViewById(R.id.im_productsimagee);
            giam = (Button) itemView.findViewById(R.id.btn_giam);
            tang = (Button) itemView.findViewById(R.id.btn_tang);
            xoa = (Button) itemView.findViewById(R.id.btn_xoa);
        }




    }
}
