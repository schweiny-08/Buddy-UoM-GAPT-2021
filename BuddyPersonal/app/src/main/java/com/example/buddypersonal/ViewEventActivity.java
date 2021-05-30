package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ViewEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView title, startTime, startDate, endTime, endDate, location, notes;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        title = (TextView) findViewById(R.id.vew_evt_tv_title_det);
        startTime = (TextView) findViewById(R.id.vew_evt_tv_start_time_det);
        startDate = (TextView) findViewById(R.id.vew_evt_tv_start_date_det);
        endTime = (TextView) findViewById(R.id.vew_evt_tv_end_time_det);
        endDate = (TextView) findViewById(R.id.vew_evt_tv_end_date_det);
        location = (TextView) findViewById(R.id.vew_evt_tv_loc_det);
        notes = (TextView) findViewById(R.id.vew_evt_tv_notes_det);

        Intent intent = getIntent();
        title.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getTitle());
        startTime.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getStartTime());
        startDate.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getStartDate());
        endTime.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getEndTime());
        endDate.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getEndDate());
        location.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getLoc());
        notes.setText(LocalStorage.privEventList.get(LocalStorage.privateEvent).getNotes());

        recyclerView = findViewById(R.id.recyclerViewItin);
        navDrawer = findViewById(R.id.drawer_icon);
        drawerLayout = findViewById(R.id.vew_evt_drawer);
        navigationView = findViewById(R.id.vew_evt_nav);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void cancel(View view) {
        Intent intent = new Intent(ViewEventActivity.this, EditEventActivity.class);
        finish();
        startActivity(intent);
    }

    public void delete(View view) {
        //delete event from itinerary
    }

    public void edit(View view) {
        Intent intent = new Intent(ViewEventActivity.this, EditEventActivity.class);
        intent.putExtra("eventParent", 1);
        startActivity(intent);
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
                Intent iSettings = new Intent(ViewEventActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(ViewEventActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(ViewEventActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(ViewEventActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(ViewEventActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(ViewEventActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(ViewEventActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(ViewEventActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
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