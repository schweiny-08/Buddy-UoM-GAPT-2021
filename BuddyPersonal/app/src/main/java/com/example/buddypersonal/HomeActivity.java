package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.hom_drawer);
        navigationView = findViewById(R.id.hom_nav);
        navDrawer = findViewById(R.id.drawer_icon);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());

        LocalStorage.privateEvent = -1;
        LocalStorage.selDate = "";
        LocalStorage.publicEvent = -1;
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_settings:
                Intent iSettings = new Intent(HomeActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(HomeActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(HomeActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(HomeActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(HomeActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(HomeActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(HomeActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(HomeActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                Intent iLogin = new Intent(HomeActivity.this, LoginActivity.class);
                iLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iLogin);
                finish();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Redirects user to the Map view activity
    public void map(View view) {
        Intent intent = new Intent(this, ViewMapActivity.class);
        finish();
        startActivity(intent);
    }

    //Redirects user to the Itinerary
    public void itinerary(View view) {
        Intent intent = new Intent(this, ItineraryActivity.class);
        finish();
        startActivity(intent);
    }

    //Redirects the suer to the Buddy cha-bot view
    public void buddy(View view) {
        Intent intent = new Intent(this, BuddyActivity.class);
        finish();
        startActivity(intent);
    }
}