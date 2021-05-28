package com.example.buddypersonal;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

public class Itinerary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItineraryAdapter.EventClickListener{

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    RecyclerView recyclerView;
    ItineraryAdapter itineraryAdapter;
    public ArrayList<EventModel> prModel = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        recyclerView = findViewById(R.id.recyclerViewItin);

        dateTimeDisplay = (TextView)findViewById(R.id.itn_tv_curr_date);
        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        drawerLayout = findViewById(R.id.itn_drawer);
        navigationView = findViewById(R.id.itn_nav);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.itn_toolbar)));

        popRecyclerView();

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MMM/yyyy");
        String strDate = mdformat.format(calendar.getTime());

        insertEvents(strDate);
        LocalStorage.privateEvent = -1;
    }

    public void popRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itineraryAdapter = new ItineraryAdapter(prModel, this);
        recyclerView.setAdapter(itineraryAdapter);
    }

    public void insertEvents(String date){
        //TODO: when loading, it should check if the StartDate and StartTime have already elapsed, private events

        for(int i = 0;i<LocalStorage.privEventList.size();i++){
            //get today's, do by string data parameter
            //string to date simpledateformat
            //loop list, check date if date matches then load....
            //EventModel em = new EventModel();
//            em.setEventId(i);
//            em.setUserId(1);
//            em.setTitle("test"+i);
//            em.setStartTime("12:45");
//            em.setEndTime("14:00");
//            em.setStartDate("12/07/2021");
//            em.setEndDate("12/07/2021");
//            em.setLoc("somewhere"+i);
//            em.setNotes("something"+i);
//            prModel.add(em);

            if((LocalStorage.privEventList.get(i).getUserId() == LocalStorage.loggedInUser) ) {
                //match the date and the user IDs
                EventModel em = LocalStorage.privEventList.get(i);
                prModel.add(em);
            }
        }
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

    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(Itinerary.this, ViewEvent.class);
        intent.putExtra("vEventId", prModel.get(position).getEventId());
        intent.putExtra("vUserId", prModel.get(position).getUserId());
        intent.putExtra("vTitle", prModel.get(position).getTitle());
        intent.putExtra("vStartTime", prModel.get(position).getStartTime());
        intent.putExtra("vStartDate", prModel.get(position).getStartDate());
        intent.putExtra("vEndTime", prModel.get(position).getEndTime());
        intent.putExtra("vEndDate", prModel.get(position).getEndDate());
        intent.putExtra("vLocation", prModel.get(position).getLoc());
        intent.putExtra("vNotes", prModel.get(position).getNotes());
        LocalStorage.privateEvent = prModel.get(position).getEventId()-1;
        startActivity(intent);
    }
}