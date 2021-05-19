package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarList extends AppCompatActivity {

    private TextView dateTimeDisplay;
    RecyclerView mRecyclerView;
    ItineraryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        mRecyclerView = findViewById(R.id.recyclerViewItin);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new ItineraryAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);

        dateTimeDisplay = (TextView)findViewById(R.id.cal_lst_tv_curr_date);

        Intent calIntent = getIntent();
        if (calIntent.getStringExtra("selected date")!=null) {
            String calDate = calIntent.getExtras().getString("selected date");
            dateTimeDisplay.setText(calDate);
        }
    }

    private ArrayList<Model> getMyList() {
        //gets from database based on selected date
        //include condition for when there is only a Start Time and no End Time

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setStartTime("8:00");
        m.setEndTime("10:00");
        m.setTitle("Developing Developments");
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
}