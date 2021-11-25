package com.example.phuclong.ui.slideshow;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.R;

public class orderViewAd extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView OrderIDUser;
    private ItemClinklistene itemClinklistene;


    public orderViewAd(@NonNull View itemView) {
        super(itemView);
        OrderIDUser = (TextView) itemView.findViewById(R.id.tv_Order_ID);



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
