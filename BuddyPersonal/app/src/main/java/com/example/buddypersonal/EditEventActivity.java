package com.example.buddypersonal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, NavigationView.OnNavigationItemSelectedListener {

    EditText title, location, notes;
    TextView startTime, startDate, endTime, endDate;
    Picker picker = new Picker();
    Calendar cST, cSD, cET, cED;
    int parent;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        title = (EditText) findViewById(R.id.crt_evt_et_title);
        startTime = (TextView) findViewById(R.id.crt_evt_et_start_time);
        startDate = (TextView) findViewById(R.id.crt_evt_et_start_date);
        endTime = (TextView) findViewById(R.id.crt_evt_et_end_time);
        endDate = (TextView) findViewById(R.id.crt_evt_et_end_date);
        location = (EditText) findViewById(R.id.crt_evt_et_loc);
        notes = (EditText) findViewById(R.id.crt_evt_et_notes);

        //In the case when an event is being edited, the calendar objects would need to be initialised so that the proper validations occur
        parent = getIntent().getIntExtra("eventParent", -1);

        if (LocalStorage.privateEvent >= 0) {
            title.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getTitle()));
            startTime.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getStartTime()));
            startDate.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getStartDate()));
            endTime.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getEndTime()));
            endDate.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getEndDate()));
            location.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getLoc()));
            notes.setText((LocalStorage.privEventList.get(LocalStorage.privateEvent).getNotes()));
        } else {
            title.setText("");
            startTime.setText("");
            startDate.setText("");
            endTime.setText("");
            endDate.setText("");
            location.setText("");
            notes.setText("");
        }

        //When the parent event is ViewEvent
        if(parent==1){
            //Calendar object are initialised to the values found within the temporal TextViews
            String[] sCST = startTime.getText().toString().split(":");
            cST = Calendar.getInstance();
            cST.set(Calendar.YEAR, 0);
            cST.set(Calendar.MONTH, 0);
            cST.set(Calendar.DAY_OF_YEAR, 0);
            cST.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sCST[0]));
            cST.set(Calendar.MINUTE, Integer.parseInt(sCST[1]));
            cST.set(Calendar.SECOND, 0);
            cST.set(Calendar.MILLISECOND, 0);

            String[] sCET = endTime.getText().toString().split(":");
            cET = Calendar.getInstance();
            cET.set(Calendar.YEAR, 0);
            cET.set(Calendar.MONTH, 0);
            cET.set(Calendar.DAY_OF_YEAR, 0);
            cET.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sCET[0]));
            cET.set(Calendar.MINUTE, Integer.parseInt(sCET[1]));
            cET.set(Calendar.SECOND, 0);
            cET.set(Calendar.MILLISECOND, 0);

            String[] sCSD = startDate.getText().toString().split("/");
            cSD = Calendar.getInstance();
            cSD.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sCSD[0]));
            cSD.set(Calendar.MONTH, Integer.parseInt(sCSD[1])-1);
            cSD.set(Calendar.YEAR, Integer.parseInt(sCSD[2]));
            cSD.set(Calendar.HOUR_OF_DAY, 0);
            cSD.set(Calendar.MINUTE, 0);
            cSD.set(Calendar.SECOND, 0);
            cSD.set(Calendar.MILLISECOND, 0);

            String[] sCED = endDate.getText().toString().split("/");
            cED = Calendar.getInstance();
            cED.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sCED[0]));
            cED.set(Calendar.MONTH, Integer.parseInt(sCED[1])-1);
            cED.set(Calendar.YEAR, Integer.parseInt(sCED[2]));
            cED.set(Calendar.HOUR_OF_DAY, 0);
            cED.set(Calendar.MINUTE, 0);
            cED.set(Calendar.SECOND, 0);
            cED.set(Calendar.MILLISECOND, 0);
        }

        //Click listeners for the temporal TextViews
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setPicker(startTime);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setPicker(endTime);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time end picker");
            }
        });
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setPicker(startDate);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setPicker(endDate);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date end picker");
            }
        });

        drawerLayout = findViewById(R.id.crt_evt_drawer);
        navigationView = findViewById(R.id.crt_evt_nav);
        navDrawer = findViewById(R.id.drawer_icon);

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

    //Saves the new/updated event with the associated data
    public void createEvent(View view) {
        if ((TextUtils.isEmpty(title.getText().toString())) || (TextUtils.isEmpty(startTime.getText().toString())) || (TextUtils.isEmpty(startDate.getText().toString())) || (TextUtils.isEmpty(endTime.getText().toString())) || (TextUtils.isEmpty(endDate.getText().toString()))) {
            Toast.makeText(EditEventActivity.this, "Please make sure you have filled  the necessary credentials.", Toast.LENGTH_SHORT).show();
        } else if (cSD.after(cED)) {
            Toast.makeText(EditEventActivity.this, "Please enter a valid time frame for the event.", Toast.LENGTH_SHORT).show();
        } else if (cSD.equals(cED)) {
            if (cST.after(cET)) {
                Toast.makeText(EditEventActivity.this, "Please enter a valid time frame for the event.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditEventActivity.this, "Event has been successfully created.", Toast.LENGTH_SHORT).show();

                String titl = title.getText().toString();
                String st = startTime.getText().toString();
                String sd = startDate.getText().toString();
                String et = endTime.getText().toString();
                String ed = endDate.getText().toString();
                String noti = notes.getText().toString();
                String loca = location.getText().toString();

                if (LocalStorage.privateEvent >= 0) { //existing event

                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setTitle(titl);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setStartTime(st);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setStartDate(sd);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setEndTime(et);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setEndDate(ed);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setNotes(noti);
                    LocalStorage.privEventList.get(LocalStorage.privateEvent).setLoc(loca);

                } else { //create new event

                    int evid = LocalStorage.privEventList.size() + 1;
                    int uid = LocalStorage.loggedInUser;

                    EventModel eventNew = new EventModel(evid, uid, titl, st, sd, et, ed, noti, loca); //according to parameters, to do
                    LocalStorage.privEventList.add(eventNew); //add to array list
                }
                writeToFile(LocalStorage.getPrivEventsJson(), getApplicationContext()); //save list of private events to file

                Intent intent = new Intent(this, ItineraryActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(EditEventActivity.this, "Event has been successfully created.", Toast.LENGTH_SHORT).show();

            String titl = title.getText().toString();
            String st = startTime.getText().toString();
            String sd = startDate.getText().toString();
            String et = endTime.getText().toString();
            String ed = endDate.getText().toString();
            String noti = notes.getText().toString();
            String loca = location.getText().toString();

            if (LocalStorage.privateEvent >= 0) { //existing event

                LocalStorage.privEventList.get(LocalStorage.privateEvent).setTitle(titl);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setStartTime(st);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setStartDate(sd);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setEndTime(et);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setEndDate(ed);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setNotes(noti);
                LocalStorage.privEventList.get(LocalStorage.privateEvent).setLoc(loca);

            } else { //create new event

                int evid = LocalStorage.privEventList.size() + 1;
                int uid = LocalStorage.loggedInUser;

                EventModel eventNew = new EventModel(evid, uid, titl, st, sd, et, ed, noti, loca); //according to parameters, to do
                LocalStorage.privEventList.add(eventNew); //add to array list
            }
            writeToFile(LocalStorage.getPrivEventsJson(), getApplicationContext()); //save list of private events to file

            Intent intent = new Intent(this, ItineraryActivity.class);
            startActivity(intent);
        }
    }

    public void exit(View view) {
        Intent intent = new Intent(this, ItineraryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    //The calendar object is set solely with the selected temporal data
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 0);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        picker.getPicker().setText(currentTime);
        if (picker.getPicker() == startTime) {
            cST = c;
        } else {
            cET = c;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        picker.getPicker().setText(currentDate);
        if (picker.getPicker() == startDate) {
            cSD = c;
        } else {
            cED = c;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                Intent iSettings = new Intent(EditEventActivity.this, SettingsActivity.class);
                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(EditEventActivity.this, HomeActivity.class);
                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(EditEventActivity.this, ViewMapActivity.class);
                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(EditEventActivity.this, VenueEventActivity.class);
                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(EditEventActivity.this, ItineraryActivity.class);
                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(EditEventActivity.this, CalendarActivity.class);
                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(EditEventActivity.this, EditEventActivity.class);
                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(EditEventActivity.this, BuddyActivity.class);
                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(EditEventActivity.this, LoginActivity.class);
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

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("priv_events_file.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}