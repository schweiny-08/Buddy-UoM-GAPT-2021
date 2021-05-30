package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ItineraryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItineraryAdapter.EventClickListener {

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    RecyclerView recyclerView;
    ItineraryAdapter itineraryAdapter;
    public ArrayList<EventModel> prModel = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        recyclerView = findViewById(R.id.recyclerViewItin);
        navDrawer = findViewById(R.id.drawer_icon);

        dateTimeDisplay = (TextView) findViewById(R.id.itn_tv_curr_date);
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
        navDrawer.setOnClickListener(view -> openDrawer());

        popRecyclerView();
        String strDate = "";

        if (LocalStorage.selDate != "") {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("dd/MMM/yyyy");
            strDate = mdformat.format(calendar.getTime());
        } else {
            strDate = LocalStorage.selDate;
        }

        insertEvents(strDate);
        LocalStorage.privateEvent = -1;
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void popRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itineraryAdapter = new ItineraryAdapter(prModel, this);
        recyclerView.setAdapter(itineraryAdapter);
    }

    public void insertEvents(String date) {
        for (int i = 0; i < LocalStorage.privEventList.size(); i++) {
            //match the users ID and the current date
            if ((LocalStorage.privEventList.get(i).getUserId() == LocalStorage.loggedInUser)) {
                if((LocalStorage.privEventList.get(i).getStartDate().equals(date))) {
                    EventModel em = LocalStorage.privEventList.get(i);
                    prModel.add(em);
                }
            }
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
                Intent iSettings = new Intent(ItineraryActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(ItineraryActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(ItineraryActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(ItineraryActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(ItineraryActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(ItineraryActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(ItineraryActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(ItineraryActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(ItineraryActivity.this, LoginActivity.class);
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

    @Override
    public void onEventClick(int position) {
        Intent intent = new Intent(ItineraryActivity.this, ViewEventActivity.class);
        LocalStorage.privateEvent = prModel.get(position).getEventId() - 1;
        startActivity(intent);
    }
}