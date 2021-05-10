package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateEvent extends AppCompatActivity {

    EditText title, startTime, startDate, endTime, endDate, location, host, invitees, attendees, capacity, reminder, notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        title = (EditText)findViewById(R.id.crt_evt_et_title);
        startTime = (EditText)findViewById(R.id.crt_evt_et_start_time);
        startDate = (EditText)findViewById(R.id.crt_evt_et_start_date);
        endTime = (EditText)findViewById(R.id.crt_evt_et_end_time);
        endDate = (EditText)findViewById(R.id.crt_evt_et_end_date);
        location = (EditText)findViewById(R.id.crt_evt_et_loc);
        host = (EditText)findViewById(R.id.crt_evt_et_host);
        invitees = (EditText)findViewById(R.id.crt_evt_et_invitees);
        attendees = (EditText)findViewById(R.id.crt_evt_et_attendees);
        capacity = (EditText)findViewById(R.id.crt_evt_et_cap);
        reminder = (EditText)findViewById(R.id.crt_evt_et_reminder);
        notes = (EditText)findViewById(R.id.crt_evt_et_notes);
    }

    public void createEvent(View view){
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }
    public void exit(View view){
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }
}