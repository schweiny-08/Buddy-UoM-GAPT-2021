package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AccessibilityActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    Resources resources;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        RadioButton eng = (RadioButton) findViewById(R.id.rdb_english);
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(AccessibilityActivity.this, "en");
                resources = context.getResources();

//                Locale locale = new Locale("en-rEN");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });

        RadioButton malt = (RadioButton) findViewById(R.id.rdb_maltese);
        malt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(AccessibilityActivity.this, "mt");
                resources = context.getResources();

//                Locale locale = new Locale("mt-rMT");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });

        RadioButton span = (RadioButton) findViewById(R.id.rbd_spanish);
        span.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(AccessibilityActivity.this, "es");
                resources = context.getResources();

//                Locale locale = new Locale("es-rES");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
            }
        });

        drawerLayout = findViewById(R.id.acc_drawer);
        navigationView = findViewById(R.id.acc_nav);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void save(View view){
        Intent intent = new Intent(AccessibilityActivity.this, SettingsActivity.class);
        startActivity(intent);
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
                Intent iSettings = new Intent(AccessibilityActivity.this, SettingsActivity.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(AccessibilityActivity.this, HomeActivity.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(AccessibilityActivity.this, ViewMapActivity.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(AccessibilityActivity.this, VenueEventActivity.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(AccessibilityActivity.this, ItineraryActivity.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(AccessibilityActivity.this, CalendarActivity.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(AccessibilityActivity.this, EditEventActivity.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(AccessibilityActivity.this, BuddyActivity.class);
//                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(AccessibilityActivity.this, LoginActivity.class);
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