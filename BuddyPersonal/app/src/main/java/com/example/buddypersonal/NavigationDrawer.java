package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.nav_toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.nav_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.nav_home:
                intent = new Intent(this, Home.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                intent = new Intent(this, MapView.class);
                startActivity(intent);
                break;
            case R.id.nav_pu_events:
                intent = new Intent(this, VenueEvents.class);
                startActivity(intent);
                break;
            case R.id.nav_itinerary:
                intent = new Intent(this, Itinerary.class);
                startActivity(intent);
                break;
            case R.id.nav_calendar:
                intent = new Intent(this, Calendar.class);
                startActivity(intent);
                break;
            case R.id.nav_cr_events:
                intent = new Intent(this, CreateEvent.class);
                startActivity(intent);
                break;
            case R.id.nav_buddy:
                intent = new Intent(this, Buddy.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
