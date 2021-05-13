package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText title, location, notes;
    TextView startTime, startDate, endTime, endDate;
    Pickers picker = new Pickers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        title = (EditText) findViewById(R.id.crt_evt_et_title);
        startTime = (TextView) findViewById(R.id.crt_evt_et_start_time);
        startDate = (TextView) findViewById(R.id.crt_evt_et_start_date);
        endTime = (TextView) findViewById(R.id.crt_evt_et_end_time);
        endDate = (TextView) findViewById(R.id.crt_evt_et_end_date);
        location = (EditText) findViewById(R.id.crt_evt_et_loc);
        notes = (EditText) findViewById(R.id.crt_evt_et_notes);


        //TIME
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(startTime);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(endTime);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time end picker");
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(startDate);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(endDate);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date end picker");
            }
        });
    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(java.util.Calendar.MINUTE, minute);

        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        picker.getDate().setText(currentTime);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        picker.getDate().setText(currentDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}