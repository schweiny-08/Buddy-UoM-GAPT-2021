package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ViewAccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;
    TextView name, surname, email, username;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        drawerLayout = findViewById(R.id.vew_act_drawer);
        navigationView = findViewById(R.id.vew_act_nav);
        navDrawer = findViewById(R.id.drawer_icon);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navDrawer.setOnClickListener(view -> openDrawer());


        name = (TextView) findViewById(R.id.act_et_name);
        surname = (TextView) findViewById(R.id.act_et_surname);
        email = (TextView) findViewById(R.id.act_et_email);
        username = (TextView) findViewById(R.id.act_et_username);

        name.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUName());
        surname.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUSurname());
        email.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getEmail());
        username.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUsername());
    }


    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void edit(View view) {
        Intent intent = new Intent(ViewAccountActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void delete(View view) {
        LocalStorage.usersList.remove(LocalStorage.loggedInUser); //remove user object from storage list
        RegisterActivity.writeToFile(LocalStorage.getUserJson(), getApplicationContext()); //save new modified list
        Intent intent = new Intent(ViewAccountActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
                Intent iSettings = new Intent(ViewAccountActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(ViewAccountActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(ViewAccountActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(ViewAccountActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(ViewAccountActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(ViewAccountActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(ViewAccountActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(ViewAccountActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(ViewAccountActivity.this, LoginActivity.class);
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