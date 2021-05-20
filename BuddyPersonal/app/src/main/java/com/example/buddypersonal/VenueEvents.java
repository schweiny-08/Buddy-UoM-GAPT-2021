package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VenueEvents extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    VenueEventsAdapter myAdapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_events);

        mRecyclerView = findViewById(R.id.recyclerViewVen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new VenueEventsAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);

        drawerLayout = findViewById(R.id.ven_evt_drawer);
        navigationView = findViewById(R.id.ven_evt_nav);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private ArrayList<Model> getMyList() {
        //gets from database
        //include condition for when there is only a Start Time and no End Time

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setStartTime("10:45");
        m.setEndTime("12:00");
        m.setTitle("Dog Show");
        m.setLoc("Hall H");
        m.setDescription("Come watch inbred dogs run around and do cool tricks you can't even do. Don't miss out on this nail-biting show, where everyone knows the judges are bought.");
        models.add(m);

        m = new Model();
        m.setStartTime("11:30");
        m.setEndTime("12:15");
        m.setTitle("Something about new Tech!");
        m.setLoc("Room N");
        m.setDescription("Something about new Tech! is a presentation by student who present their work in the form of a presentation. This will give them an opportunity to be scoped by the market.");
        models.add(m);

        m = new Model();
        m.setStartTime("13:15");
        m.setEndTime("13:45");
        m.setTitle("I don't even know");
        m.setLoc("Room C");
        m.setDescription("Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore.");
        models.add(m);

        m = new Model();
        m.setStartTime("12:30");
        m.setEndTime("13:30");
        m.setTitle("If you'll bother reading the titles");
        m.setLoc("Room J");
        m.setDescription("Mama always said life was like a box of chocolates. You never know what you're gonna get. Mama always said life was like a box of chocolates. You never know what you're");
        models.add(m);

        m = new Model();
        m.setStartTime("14:20");
        m.setEndTime("15:00");
        m.setTitle("Anymore. I don't blame you.");
        m.setLoc("Room M");
        m.setDescription("Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer.");
        models.add(m);

        m = new Model();
        m.setStartTime("10:45");
        m.setEndTime("12:00");
        m.setTitle("Dog Show");
        m.setLoc("Hall H");
        m.setDescription("Come watch inbred dogs run around and do cool tricks you can't even do. Don't miss out on this nail-biting show, where everyone knows the judges are bought.");
        models.add(m);

        m = new Model();
        m.setStartTime("11:30");
        m.setEndTime("12:15");
        m.setTitle("Something about new Tech!");
        m.setLoc("Room N");
        m.setDescription("Something about new Tech! is a presentation by student who present their work in the form of a presentation. This will give them an opportunity to be scoped by the market.");
        models.add(m);

        m = new Model();
        m.setStartTime("13:15");
        m.setEndTime("13:45");
        m.setTitle("I don't even know");
        m.setLoc("Room C");
        m.setDescription("Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore.");
        models.add(m);

        m = new Model();
        m.setStartTime("12:30");
        m.setEndTime("13:30");
        m.setTitle("If you'll bother reading the titles");
        m.setLoc("Room J");
        m.setDescription("Mama always said life was like a box of chocolates. You never know what you're gonna get. Mama always said life was like a box of chocolates. You never know what you're");
        models.add(m);

        m = new Model();
        m.setStartTime("14:20");
        m.setEndTime("15:00");
        m.setTitle("Anymore. I don't blame you.");
        m.setLoc("Room M");
        m.setDescription("Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer.");
        models.add(m);

        return models;
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
                Intent iSettings = new Intent(VenueEvents.this, Settings.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(VenueEvents.this, Home.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(VenueEvents.this, MapView.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(VenueEvents.this, VenueEvents.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(VenueEvents.this, Itinerary.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(VenueEvents.this, Calendar.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(VenueEvents.this, CreateEvent.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(VenueEvents.this, Buddy.class);
//                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(VenueEvents.this, Login.class);
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