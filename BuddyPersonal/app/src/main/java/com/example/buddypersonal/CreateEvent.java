package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText title, location, notes;
    TextView startTime, startDate, endTime, endDate;
    Picker picker = new Picker();
    Calendar cST, cSD, cET, cED;

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
    }

    public void createEvent(View view) {
        if ((TextUtils.isEmpty(title.getText().toString()))||(TextUtils.isEmpty(startTime.getText().toString()))||(TextUtils.isEmpty(startDate.getText().toString()))||(TextUtils.isEmpty(endTime.getText().toString()))||(TextUtils.isEmpty(endDate.getText().toString()))) {
            Toast.makeText(CreateEvent.this, "Please make sure you have filled  the necessary credentials.", Toast.LENGTH_SHORT).show();
        }
        else if(cST.after(cET)){
            if((cSD.after(cED))||(cSD.equals(cED))) {
                Toast.makeText(CreateEvent.this, "Please enter a valid time frame for the event.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(cSD.after(cED)){
            Toast.makeText(CreateEvent.this, "Please enter a valid time frame for the event.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(CreateEvent.this, "Event has been successfully created.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Itinerary.class);
            startActivity(intent);
        }
    }

    public void exit(View view) {
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = java.util.Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        picker.getPicker().setText(currentTime);
        if(picker.getPicker()==startTime){
            cST = c;
        }
        else{
            cET = c;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        picker.getPicker().setText(currentDate);
        if(picker.getPicker()==startDate){
            cSD = c;
        }
        else{
            cED = c;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}