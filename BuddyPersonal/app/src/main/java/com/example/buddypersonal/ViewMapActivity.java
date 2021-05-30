package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ViewMapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    int floor = 0;

    Button up, down;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String url = "file:///android_asset/Level0/openlayers.html";
        //level 0 first
        WebView wview = (WebView) this.findViewById(R.id.webview);
        wview.getSettings().setJavaScriptEnabled(true);
        wview.loadUrl(url);

        drawerLayout = findViewById(R.id.map_drawer);
        navigationView = findViewById(R.id.map_nav);
        up = findViewById(R.id.map_bt_up);
        down = findViewById(R.id.map_bt_down);
        navDrawer = findViewById(R.id.drawer_icon);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (floor) {
                    case -2:
                        //do nothing
                        break;
                    case 0:
                        wview.loadUrl("file:///android_asset/Level-1/openlayers.html");
                        floor = -1;
                        break;
                    case 1:
                        wview.loadUrl("file:///android_asset/Level0/openlayers.html");
                        floor = 0;
                        break;
                    case -1:
                        wview.loadUrl("file:///android_asset/Level-2/openlayers.html");
                        floor = -2;
                        break;

                }
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (floor) {
                    case -2:
                        wview.loadUrl("file:///android_asset/Level-1/openlayers.html");
                        floor = -1;
                        break;
                    case 0:
                        wview.loadUrl("file:///android_asset/Level1/openlayers.html");
                        floor = 1;
                        break;
                    case 1:
                        //do nothing
                        break;
                    case -1:
                        wview.loadUrl("file:///android_asset/Level0/openlayers.html");
                        floor = 0;
                        break;

                }
            }
        });
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
                Intent iSettings = new Intent(ViewMapActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(ViewMapActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(ViewMapActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(ViewMapActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(ViewMapActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(ViewMapActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(ViewMapActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(ViewMapActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(ViewMapActivity.this, LoginActivity.class);
                iLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iLogin);
                finish();
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}