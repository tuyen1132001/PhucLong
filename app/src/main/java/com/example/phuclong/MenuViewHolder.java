package com.example.phuclong;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public   ItemClinklistene itemClinklistene;
    public   TextView menuname;
    public   ImageView image;
    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        menuname = (TextView)itemView.findViewById(R.id.tv_menuname);
        image = (ImageView)itemView.findViewById(R.id.im_image);
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
