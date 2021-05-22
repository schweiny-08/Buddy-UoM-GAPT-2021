package com.example.buddypersonal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MapView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    int floor = 0;

    Button up;
    Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String url = "file:///android_asset/Level0/openlayers.html";
        //level 0 first
        WebView view = (WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);

        drawerLayout = findViewById(R.id.map_drawer);
        navigationView = findViewById(R.id.map_nav);
        up = findViewById(R.id.upBtn);
        down = findViewById(R.id.downBtn);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        down.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(floor) {
                    case -2:
                        //do nothing
                        break;
                    case 0:
                        view.loadUrl("file:///android_asset/Level-1/openlayers.html");
                        floor = -1;
                        break;
                    case 1:
                        view.loadUrl("file:///android_asset/Level0/openlayers.html");
                        floor = 0;
                        break;
                    case -1:
                        view.loadUrl("file:///android_asset/Level-2/openlayers.html");
                        floor = -2;
                        break;

                }
            }
        } );

        up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(floor) {
                    case -2:
                        view.loadUrl("file:///android_asset/Level-1/openlayers.html");
                        floor = -1;
                        break;
                    case 0:
                        view.loadUrl("file:///android_asset/Level1/openlayers.html");
                        floor = 1;
                        break;
                    case 1:
                        //do nothing
                        break;
                    case -1:
                        view.loadUrl("file:///android_asset/Level0/openlayers.html");
                        floor = 0;
                        break;

                }
            }
        } );
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_settings:
                Intent iSettings = new Intent(MapView.this, Settings.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(MapView.this, Home.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(MapView.this, MapView.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(MapView.this, VenueEvents.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(MapView.this, Itinerary.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(MapView.this, Calendar.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(MapView.this, CreateEvent.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(MapView.this, Buddy.class);
//                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(MapView.this, Login.class);
//                finish();
                startActivity(iLogin);
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}