package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VenueEventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    VenueEventAdapter puEventAdapter;
    public ArrayList<PublicEventModel> prModel = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_events);

        mRecyclerView = findViewById(R.id.recyclerViewVen);
        drawerLayout = findViewById(R.id.ven_evt_drawer);
        navigationView = findViewById(R.id.ven_evt_nav);
        navDrawer = findViewById(R.id.drawer_icon);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());


        hardCodedEvents();
        String strDate = "";

        if (LocalStorage.selDate.equals("")) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("dd/MMM/yyyy");
            strDate = mdformat.format(calendar.getTime());
        } else {
            strDate = LocalStorage.selDate;
        }

        insertEvents(strDate);
        LocalStorage.publicEvent = -1;

        popRecyclerView();
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void popRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        puEventAdapter = new VenueEventAdapter(prModel);
        mRecyclerView.setAdapter(puEventAdapter);
    }

    public void insertEvents(String temp) {
        //TODO: when loading, it should check if the StartDate and StartTime have already elapsed
        for (int i = 0; i < LocalStorage.eventList.size(); i++) {

            //if((LocalStorage.eventList.get(i).getStartDate().equals(temp) ) ) {
            //match the date and the user IDs
            PublicEventModel em = LocalStorage.eventList.get(i);
            prModel.add(em);
            //}
        }
    }

    public void hardCodedEvents() {

        for(int i = 0; i<9; i++){
            PublicEventModel em = new PublicEventModel(i,i,i,"Event "+i, "0"+i+":00", "28/06/21", i+12+":00", "28/06/21", "UoM", "Description goes here");
            LocalStorage.eventList.add(em);
        }

        //writeToFile(LocalStorage.getEventsJson(), getApplicationContext());
    }

    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("public_events_file.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
                Intent iSettings = new Intent(VenueEventActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(VenueEventActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(VenueEventActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(VenueEventActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(VenueEventActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(VenueEventActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(VenueEventActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(VenueEventActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(VenueEventActivity.this, LoginActivity.class);
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