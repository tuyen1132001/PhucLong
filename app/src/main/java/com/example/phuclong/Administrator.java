package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phuclong.Admin.Acc.ShowActivity;
import com.example.phuclong.Admin.Category.ManageCategory;
import com.example.phuclong.Admin.InfoUser.ManagerInforCustomerActivity;
import com.example.phuclong.Admin.Order.OrderManager;
import com.example.phuclong.Admin.Products.ManageProduct;
import com.example.phuclong.databinding.ActivityAdministratorBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Administrator extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdministratorBinding binding;
    String iduser ="";
    TextView Fullname,usermail;
    ImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdministratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdministrator.toolbar);

        DrawerLayout drawer = binding.adminDrawerLayout;
        NavigationView navigationView = binding.adminNavView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_admin_home, R.id.nav_admin_gallery, R.id.nav_admin_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_administrator);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Bundle bundle = getIntent().getExtras();
        iduser = bundle.getString("IDUser");
        FirebaseDatabase user = FirebaseDatabase.getInstance();
        DatabaseReference userdata = user.getReference("InforUser");
        userdata.child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    View headerview = navigationView.getHeaderView(0);
                    avatar = headerview.findViewById(R.id.img_avatarAdmin);
                    Fullname = headerview.findViewById(R.id.tv_adminname);
                    usermail = headerview.findViewById(R.id.tv_adminemail);
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                    String name = hashMap.get("Name").toString();
                    String image = hashMap.get("HinhDaiDien").toString();
                    String  mail = hashMap.get("Email").toString();
                    Picasso.with(Administrator.this).load(image).into(avatar);
                    Fullname.setText(name);
                    usermail.setText(mail);
                }catch (Exception e){
                    Log.d("Loi JSON", e.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.nav_manageuser) {
                    Intent intent=new Intent(Administrator.this, ManagerInforCustomerActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId()== R.id.nav_manageproduct) {
                    Intent intent=new Intent(Administrator.this, ManageProduct.class);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_managecategory) {
                    Intent intent=new Intent(Administrator.this, ManageCategory.class);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_manageorder) {
                    Intent intent=new Intent(Administrator.this, OrderManager.class);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Administrator.this, MainActivity.class));
                }
                if(item.getItemId()== R.id.nav_Accountadmin) {
                    Intent intent=new Intent(Administrator.this,ProfileUserActivity.class);
                    intent.putExtra("Userid",iduser);
                    intent.putExtra("View", "User");
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_manageaccount) {
                    Intent intent=new Intent(Administrator.this, ShowActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.administrator, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_administrator);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

