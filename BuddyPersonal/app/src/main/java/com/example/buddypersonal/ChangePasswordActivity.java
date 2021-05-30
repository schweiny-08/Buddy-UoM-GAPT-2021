package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class ChangePasswordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button changePassword;
    EditText currPass, newPass, confNewPass;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currPass = (EditText) findViewById(R.id.cng_psw_et_curr_pass);
        newPass = (EditText) findViewById(R.id.cng_psw_et_new_pass);
        confNewPass = (EditText) findViewById(R.id.cng_psw_et_conf_new_pass);
        changePassword = (Button) findViewById(R.id.cng_psw_bt_cng_psw);

        recyclerView = findViewById(R.id.recyclerViewItin);
        navDrawer = findViewById(R.id.drawer_icon);

        drawerLayout = findViewById(R.id.cng_psw_drawer);
        navigationView = findViewById(R.id.cng_psw_nav);
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
        if ((TextUtils.isEmpty(currPass.getText().toString())) || (TextUtils.isEmpty(newPass.getText().toString())) || (TextUtils.isEmpty(confNewPass.getText().toString()))) {
            Toast.makeText(ChangePasswordActivity.this, "Please make sure you have filled the necessary credentials.", Toast.LENGTH_SHORT).show();
        } else if (newPass.getText().toString().equals(LocalStorage.usersList.get(LocalStorage.loggedInUser).getPassword())) {
            //current password matches the one in the database
            Toast.makeText(ChangePasswordActivity.this, "New password cannot be old password.", Toast.LENGTH_SHORT).show();
        } else if (newPass.getText().toString().length() < 8) {
            Toast.makeText(ChangePasswordActivity.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
        } else if (!newPass.getText().toString().equals(confNewPass.getText().toString())) {
            Toast.makeText(ChangePasswordActivity.this, "The passwords do no match.", Toast.LENGTH_SHORT).show();
        } else {
            //update in the database
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setPassword(newPass.getText().toString());
            RegisterActivity.writeToFile(LocalStorage.getUserJson(), getApplicationContext());

            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this, EditAccountActivity.class);
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
                Intent iSettings = new Intent(ChangePasswordActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(ChangePasswordActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(ChangePasswordActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(ChangePasswordActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(ChangePasswordActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(ChangePasswordActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(ChangePasswordActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                Intent iLogin = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                iLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iLogin);
                finish();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}