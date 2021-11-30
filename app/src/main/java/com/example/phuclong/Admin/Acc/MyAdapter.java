package com.example.phuclong.Admin.Acc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.R;
import com.example.phuclong.model.ModelAcc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ShowActivity activity;
    private List<ModelAcc> mlist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    public MyAdapter(ShowActivity activity, List<ModelAcc> mlist) {
        this.activity = activity;
        this.mlist = mlist;
    }

    public void updateData(int position) {
        ModelAcc item = mlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uID", item.getID());
        bundle.putString("uEmail", item.getEmail());
        bundle.putString("uName", item.getName());
        bundle.putString("uPassword", item.getPassword());
        bundle.putString("uPhone", item.getPhone());
        bundle.putString("uRole", item.getRole());
        Intent intent = new Intent(activity, UpdateAcc.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        ModelAcc item = mlist.get(position);
        db.collection("Users").document(item.getID()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error: ... " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void notifyRemoved(int position){
        mlist.remove(position);
        notifyRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_acc, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvId.setText(mlist.get(position).getID());
        holder.tvEmail.setText(mlist.get(position).getEmail());
        holder.tvName.setText(mlist.get(position).getName());
        holder.tvPassword.setText(mlist.get(position).getPassword());
        holder.tvPhone.setText(mlist.get(position).getPhone());
        holder.tvRole.setText(mlist.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvEmail, tvName, tvPassword, tvPhone, tvRole;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_acc_Id);
            tvEmail = itemView.findViewById(R.id.tv_acc_Email);
            tvName = itemView.findViewById(R.id.tv_acc_Name);
            tvPassword = itemView.findViewById(R.id.tv_acc_Password);
            tvPhone = itemView.findViewById(R.id.tv_acc_Phone);
            tvRole = itemView.findViewById(R.id.tv_acc_Role);
        }
    }
}
