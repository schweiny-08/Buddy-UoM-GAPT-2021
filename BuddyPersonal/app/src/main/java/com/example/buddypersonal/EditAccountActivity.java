package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class EditAccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText name, surname, email, username;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;
    RecyclerView recyclerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        name = (EditText) findViewById(R.id.edt_act_et_name);
        surname = (EditText) findViewById(R.id.edt_act_et_surname);
        email = (EditText) findViewById(R.id.edt_act_et_email);
        username = (EditText) findViewById(R.id.edt_act_et_username);

        name.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUName());
        surname.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUSurname());
        email.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getEmail());
        username.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUsername());

        recyclerView = findViewById(R.id.recyclerViewItin);
        navDrawer = findViewById(R.id.drawer_icon);
        drawerLayout = findViewById(R.id.edt_act_drawer);
        navigationView = findViewById(R.id.edt_act_nav);
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

    public void changePassword(View view) {
        Intent intent = new Intent(EditAccountActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void save(View view) {

        if (!RegisterActivity.checkEmailValid(email.toString()))
            Toast.makeText(EditAccountActivity.this, "This email has already been registered!", Toast.LENGTH_SHORT).show();
        else if (!RegisterActivity.checkUserValid(username.toString()))
            Toast.makeText(EditAccountActivity.this, "This username has already been registered!", Toast.LENGTH_SHORT).show();
        else {

            LocalStorage.usersList.get(LocalStorage.loggedInUser).setName(name.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setSurname(surname.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setEmail(email.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setUsername(username.getText().toString());
            RegisterActivity.writeToFile(LocalStorage.getUserJson(), getApplicationContext());

            Intent intent = new Intent(EditAccountActivity.this, ViewAccountActivity.class);
            startActivity(intent);
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
                Intent iSettings = new Intent(EditAccountActivity.this, SettingsActivity.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(EditAccountActivity.this, HomeActivity.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(EditAccountActivity.this, ViewMapActivity.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(EditAccountActivity.this, VenueEventActivity.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(EditAccountActivity.this, ItineraryActivity.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(EditAccountActivity.this, CalendarActivity.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(EditAccountActivity.this, EditEventActivity.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(EditAccountActivity.this, BuddyActivity.class);
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