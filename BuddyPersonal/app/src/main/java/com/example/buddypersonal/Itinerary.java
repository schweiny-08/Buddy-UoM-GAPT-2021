package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Itinerary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    RecyclerView mRecyclerView;
    ItineraryAdapter myAdapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        mRecyclerView = findViewById(R.id.recyclerViewItin);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new ItineraryAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);

        dateTimeDisplay = (TextView)findViewById(R.id.itn_tv_curr_date);
        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        drawerLayout = findViewById(R.id.itn_drawer);
        navigationView = findViewById(R.id.itn_nav);
//        toolbar = findViewById(R.id.nav_toolbar);

//        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

//    public void eventDetails(View view){
//        Intent intent = new Intent(this, EventDetails.class);
//        startActivity(intent);
//    }

    private ArrayList<Model> getMyList() {
        //gets from database based on current date
        //include condition for when there is only a Start Time and no End Time

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setStartTime("8:00");
        m.setEndTime("10:00");
        m.setTitle("Developing Developments");
//        m.setStartDate("12/11/2021");
//        m.setEndDate("13/11/2021");
//        m.setLoc("Pizza Hut");
//        m.setNotes("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        models.add(m);

        m = new Model();
        m.setStartTime("10:00");
        m.setEndTime("11:00");
        m.setTitle("Based Data in DBMS");
        models.add(m);

        m = new Model();
        m.setStartTime("12:00");
        m.setEndTime("13:00");
        m.setTitle("Micro-Data and Processing");
        models.add(m);

        m = new Model();
        m.setStartTime("14:00");
        m.setEndTime("17:00");
        m.setTitle("Websites, Cookies & Milk");
        models.add(m);

        m = new Model();
        m.setStartTime("18:00");
        m.setEndTime("20:00");
        m.setTitle("What is Plagiarism?");
        models.add(m);

        m = new Model();
        m.setStartTime("21:00");
        m.setEndTime("21:45");
        m.setTitle("Make Cookie Dough for WCM");
        models.add(m);

        m = new Model();
        m.setStartTime("22:00");
        m.setEndTime("23:30");
        m.setTitle("Draft for Developing Development");
        models.add(m);

        m = new Model();
        m.setStartTime("23:59");
        m.setEndTime("");
        m.setTitle("Micro-Data Deadline");
        models.add(m);

        m = new Model();
        m.setStartTime("22:00");
        m.setEndTime("23:30");
        m.setTitle("Draft for Developing Development");
        models.add(m);

        m = new Model();
        m.setStartTime("23:59");
        m.setEndTime("");
        m.setTitle("Micro-Data Deadline");
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
                Intent iSettings = new Intent(Itinerary.this, Settings.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(Itinerary.this, Home.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(Itinerary.this, MapView.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(Itinerary.this, VenueEvents.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(Itinerary.this, Itinerary.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(Itinerary.this, com.example.buddypersonal.Calendar.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(Itinerary.this, CreateEvent.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(Itinerary.this, Buddy.class);
//                finish();
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