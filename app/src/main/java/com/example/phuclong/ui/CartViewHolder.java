package com.example.phuclong.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_namee,product_pricee,product_quantity;
    public ImageView product_imagee;
    public ItemClinklistene itemClinklistene;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        matching();
        itemView.setOnClickListener(this);
    }

    private void matching() {
        product_namee = (TextView) itemView.findViewById(R.id.tv_productnamee);
        product_quantity = (TextView) itemView.findViewById(R.id.tv_productquantityy);
        product_imagee = (ImageView)itemView.findViewById(R.id.im_productsimagee);
        product_pricee = (TextView)itemView.findViewById(R.id.tv_productpricee);
    }

    @Override
    public void onClick(View view) {
        itemClinklistene.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClinklistene(ItemClinklistene itemClinklistene) {
        this.itemClinklistene = itemClinklistene;
    }
}

