package com.example.phuclong.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.R;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imgview;
    public TextView namee,price,quantity;
    private ItemClinklistene itemClinklistene;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        imgview = (ImageView) itemView.findViewById(R.id.im_OrderImg);
        namee = (TextView) itemView.findViewById(R.id.tv_OrderName);
        price = (TextView) itemView.findViewById(R.id.tv_OrderPrice);
        quantity=(TextView) itemView.findViewById(R.id.tv_OrderQuantity);

        itemView.setOnClickListener(this);
    }

    public void setItemclickListener(ItemClinklistene itemclickListener){
        this.itemClinklistene=itemclickListener;
    }


    @Override
    public void onClick(View view) {
        itemClinklistene.onClick(view,getAdapterPosition(),false);


}
}
