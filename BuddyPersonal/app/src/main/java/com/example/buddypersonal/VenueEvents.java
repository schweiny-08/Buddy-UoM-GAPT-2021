package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VenueEvents extends AppCompatActivity {

    RecyclerView mRecyclerView;
    VenueEventsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_events);

        mRecyclerView = findViewById(R.id.recyclerViewVen);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new VenueEventsAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);
    }

    private ArrayList<Model> getMyList() {
        //gets from database
        //include condition for when there is only a Start Time and no End Time

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setStartTime("10:45");
        m.setEndTime("12:00");
        m.setTitle("Dog Show");
        m.setLoc("Hall H");
        m.setDescription("Come watch inbred dogs run around and do cool tricks you can't even do. Don't miss out on this nail-biting show, where everyone knows the judges are bought.");
        models.add(m);

        m = new Model();
        m.setStartTime("11:30");
        m.setEndTime("12:15");
        m.setTitle("Something about new Tech!");
        m.setLoc("Room N");
        m.setDescription("Something about new Tech! is a presentation by student who present their work in the form of a presentation. This will give them an opportunity to be scoped by the market.");
        models.add(m);

        m = new Model();
        m.setStartTime("13:15");
        m.setEndTime("13:45");
        m.setTitle("I don't even know");
        m.setLoc("Room C");
        m.setDescription("Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore.");
        models.add(m);

        m = new Model();
        m.setStartTime("12:30");
        m.setEndTime("13:30");
        m.setTitle("If you'll bother reading the titles");
        m.setLoc("Room J");
        m.setDescription("Mama always said life was like a box of chocolates. You never know what you're gonna get. Mama always said life was like a box of chocolates. You never know what you're");
        models.add(m);

        m = new Model();
        m.setStartTime("14:20");
        m.setEndTime("15:00");
        m.setTitle("Anymore. I don't blame you.");
        m.setLoc("Room M");
        m.setDescription("Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer.");
        models.add(m);

        m = new Model();
        m.setStartTime("10:45");
        m.setEndTime("12:00");
        m.setTitle("Dog Show");
        m.setLoc("Hall H");
        m.setDescription("Come watch inbred dogs run around and do cool tricks you can't even do. Don't miss out on this nail-biting show, where everyone knows the judges are bought.");
        models.add(m);

        m = new Model();
        m.setStartTime("11:30");
        m.setEndTime("12:15");
        m.setTitle("Something about new Tech!");
        m.setLoc("Room N");
        m.setDescription("Something about new Tech! is a presentation by student who present their work in the form of a presentation. This will give them an opportunity to be scoped by the market.");
        models.add(m);

        m = new Model();
        m.setStartTime("13:15");
        m.setEndTime("13:45");
        m.setTitle("I don't even know");
        m.setLoc("Room C");
        m.setDescription("Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore. Toto, I've got a feeling we're not in Kansas anymore.");
        models.add(m);

        m = new Model();
        m.setStartTime("12:30");
        m.setEndTime("13:30");
        m.setTitle("If you'll bother reading the titles");
        m.setLoc("Room J");
        m.setDescription("Mama always said life was like a box of chocolates. You never know what you're gonna get. Mama always said life was like a box of chocolates. You never know what you're");
        models.add(m);

        m = new Model();
        m.setStartTime("14:20");
        m.setEndTime("15:00");
        m.setTitle("Anymore. I don't blame you.");
        m.setLoc("Room M");
        m.setDescription("Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer. Keep your friends close, but your enemies closer.");
        models.add(m);

        return models;
    }
}