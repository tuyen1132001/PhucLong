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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.phuclong.Admin.Cart.ManageCart;
import com.example.phuclong.Admin.Category.ManageCategory;
import com.example.phuclong.Admin.InfoUser.ManagerInforCustomerActivity;
import com.example.phuclong.Admin.Order.ManageOrder;
import com.example.phuclong.Admin.Products.ManageProduct;
import com.example.phuclong.databinding.ActivityAdministratorBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class Administrator extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdministratorBinding binding;

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
                if(item.getItemId()== R.id.nav_managecart) {
                    Intent intent=new Intent(Administrator.this, ManageCart.class);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_manageorder) {
                    Intent intent=new Intent(Administrator.this, ManageOrder.class);
                    startActivity(intent);
                }
                if(item.getItemId()== R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Administrator.this, MainActivity.class));
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

