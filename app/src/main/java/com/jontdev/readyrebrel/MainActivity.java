package com.jontdev.readyrebrel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

     private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Welcome()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;
        if (id == R.id.home) {
            selectedFragment = new Rebel();
        } else if (id == R.id.contact) {
            selectedFragment = new Contact();
        } else if (id == R.id.settings) {
            Settings settingsFragment = (Settings) getSupportFragmentManager().findFragmentByTag("settings");

            if (settingsFragment == null) {
                selectedFragment = new Settings();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment, "settings").commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, settingsFragment).commit();
            }
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}



