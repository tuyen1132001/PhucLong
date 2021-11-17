package com.example.phuclong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.R;
import com.example.phuclong.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

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
        holder.txt_quantity.setText(order.getQuantity());

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


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cart_name = (TextView) itemView.findViewById(R.id.tv_productnamee);
            txt_price = (TextView) itemView.findViewById(R.id.tv_productpricee);
            txt_quantity = (TextView) itemView.findViewById(R.id.tv_productquantityy);
            img_cart_count = (ImageView) itemView.findViewById(R.id.im_productsimagee);
        }


    }
}
