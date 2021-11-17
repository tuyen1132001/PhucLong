package com.example.phuclong.Admin.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.phuclong.Category;
import com.example.phuclong.ItemClinklistene;
import com.example.phuclong.MenuViewHolder;
import com.example.phuclong.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ManageCategory extends AppCompatActivity implements View.OnCreateContextMenuListener {

    RecyclerView menu;
    FirebaseDatabase database;
    DatabaseReference Reference;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    FloatingActionButton addcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        database = FirebaseDatabase.getInstance();
        Reference = database.getReference("Category");

        matching();
        menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        menu.setLayoutManager(layoutManager);
        loadMenu();
        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageCategory.this, AddCategory.class);
                int size = adapter.getItemCount();
                intent.putExtra("size",size);
                startActivity(intent);
            }
        });

    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category,MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,Reference) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                Reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        menuViewHolder.menuname.setText(category.getName());
                        Picasso.with(getBaseContext()).load(category.getImage())
                                .into(menuViewHolder.image);
                        menuViewHolder.setItemclickListener(new ItemClinklistene() {
                            @Override
                            public void onClick(View view, int pos, boolean islongClick) {
                                // get category id
                                Intent intent = new Intent(ManageCategory.this, Update_DeleteCategory.class);
                                intent.putExtra("categoryid",adapter.getRef(pos).getKey());
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("FIREBASE","loadPost:onCancelled",error.toException());
                    }

                });


            }


        };
        adapter.notifyDataSetChanged();
        menu.setAdapter(adapter);


    }

    private void matching() {
        menu = (RecyclerView) findViewById(R.id.rv_menumanagecategory);
        addcategory = (FloatingActionButton) findViewById(R.id.fabtn_AddCategory);


    }


}
