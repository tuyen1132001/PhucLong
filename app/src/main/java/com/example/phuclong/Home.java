package com.example.phuclong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phuclong.Admin.Category.ManageCategory;
import com.example.phuclong.Admin.InfoUser.ManagerInforCustomerActivity;
import com.example.phuclong.Admin.Products.ManageProduct;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phuclong.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Home extends AppCompatActivity{
    TextView Fullname,usermail;
    RecyclerView menu;
    FloatingActionButton fab;
    ImageView avatar;

    FirebaseDatabase database;
    DatabaseReference Reference;
    RecyclerView.LayoutManager layoutManager;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    String iduser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);

                startActivity(cartIntent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        iduser = bundle.getString("IDUser");

        matching();


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Admin, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //

        // lay firebase
        database = FirebaseDatabase.getInstance();
        Reference = database.getReference("Category");

        menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        menu.setLayoutManager(layoutManager);
        loadcateggory();

        FirebaseDatabase user = FirebaseDatabase.getInstance();
        DatabaseReference userdata = user.getReference("InforUser");
        userdata.child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    View headerview = navigationView.getHeaderView(0);
                    avatar = headerview.findViewById(R.id.img_avatar);
                    Fullname = headerview.findViewById(R.id.tv_FullName);
                    usermail = headerview.findViewById(R.id.tv_usermail);
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                    String name = hashMap.get("Name").toString();
                    String mail = hashMap.get("Email").toString();
                    if(hashMap.size()==6) {
                        Fullname.setText(name);
                        usermail.setText(mail);
                    }else {
                        Picasso.with(Home.this).load(hashMap.get("HinhDaiDien").toString()).into(avatar);
                        Fullname.setText(name);
                        usermail.setText(mail);
                    }
                }catch (Exception e){
                    Log.d("Loi JSON", e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,Cart.class);
                intent.putExtra("cartid",iduser);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.nav_home) {
                    Intent intent=new Intent(Home.this,Home.class);
                    intent.putExtra("IDUser",iduser);
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId()== R.id.nav_Account) {
                    Intent intent=new Intent(Home.this,ProfileUserActivity.class);
                    intent.putExtra("Userid",iduser);
                    intent.putExtra("View", "User");
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_Order) {
                    Intent intent=new Intent(Home.this,InfoOrder.class);
                    intent.putExtra("Userid",iduser);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_logoutuser) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Home.this, MainActivity.class));
                }
                return false;
            }
        });





    }

    private void loadcateggory() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,Reference) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                Reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        menuViewHolder.menuname.setText(category.getName());
                        Picasso.with(getBaseContext()).load(category.getImage())
                                .into(menuViewHolder.image);
                        Category item = category;
                        Log.d("FIREBASE","loadPost:onCancelled"+item,null);

                        menuViewHolder.setItemclickListener(new ItemClinklistene() {
                            @Override
                            public void onClick(View view, int pos, boolean islongClick) {
                                // get category id
                                Intent intent = new Intent(Home.this,Productslist.class);
                                intent.putExtra("IDUser",iduser);
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
        menu = (RecyclerView) findViewById(R.id.rv_menu);
        fab = (FloatingActionButton) findViewById(R.id.fab);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}