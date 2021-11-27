package com.example.phuclong.ui;


import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.R;

public class orderViewAd extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public TextView OrderID,Address,Phone,Status;
    private ItemClinklistene itemClinklistene;


    public orderViewAd(@NonNull View itemView) {
        super(itemView);
        OrderID = (TextView) itemView.findViewById(R.id.tv_Order_ID);
        Address = (TextView) itemView.findViewById(R.id.tv_Order_address);
        Phone = (TextView) itemView.findViewById(R.id.tv_Order_phone);
        Status = (TextView) itemView.findViewById(R.id.tv_Order_Status);




        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }
    public void setItemclickListener(ItemClinklistene itemclickListener){
        this.itemClinklistene=itemclickListener;
    }


    @Override
    public void onClick(View view) {
        itemClinklistene.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select The Acion ");
        contextMenu.add(0,0,getAdapterPosition(),"Update");
    }


}
