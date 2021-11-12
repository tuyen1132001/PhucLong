package com.example.phuclong;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   TextView product_name,product_price;
   ImageView product_image;
   public ItemClinklistene itemClinklistene;
    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);
        matching();
        itemView.setOnClickListener(this);
    }

    private void matching() {
        product_name = (TextView)itemView.findViewById(R.id.tv_productname);
        product_image = (ImageView)itemView.findViewById(R.id.im_productsimage);
        product_price = (TextView)itemView.findViewById(R.id.tv_productprice);
    }

    @Override
    public void onClick(View view) {
        itemClinklistene.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClinklistene(ItemClinklistene itemClinklistene) {
        this.itemClinklistene = itemClinklistene;
    }
}
