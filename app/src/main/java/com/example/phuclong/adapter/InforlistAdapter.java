package com.example.phuclong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.Home;
import com.example.phuclong.ProfileUserActivity;
import com.example.phuclong.R;
import com.example.phuclong.ui.InforCustomer.InforlistDomain;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InforlistAdapter extends RecyclerView.Adapter<InforlistAdapter.InforlistViewHolder> {
    ArrayList<InforlistDomain> list;
    Context myContext;

    public InforlistAdapter(Context context, ArrayList<InforlistDomain> list) {
        this.list = list;
        this.myContext = context;
    }

    @NonNull
    @Override
    public InforlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_list_infor_customer, parent, false);
        return new InforlistAdapter.InforlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InforlistViewHolder holder, int position) {
        InforlistDomain domain = list.get(position);
        holder.ten.setText(domain.getName());
        holder.email.setText(domain.getEmail());
        if(!domain.getImg().equals("ic_baseline_account_circle_24"))
            Picasso.with(myContext).load(domain.getImg()).into(holder.img);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(myContext, ProfileUserActivity.class);
                intent.putExtra("Userid", domain.getId());
                intent.putExtra("View", "Admin");
                myContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InforlistViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView ten, email;
        private ConstraintLayout layout;

        public InforlistViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_avatar_profile_manager);
            ten = itemView.findViewById(R.id.tv_list_infor_name);
            email = itemView.findViewById(R.id.tv_list_infor_email);
            layout = itemView.findViewById(R.id.layout_list_infor_cus);
        }
    }
}
