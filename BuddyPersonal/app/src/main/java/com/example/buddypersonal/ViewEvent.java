package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewEvent extends AppCompatActivity {

    TextView title, startTime, startDate, endTime, endDate, location, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        title = (TextView)findViewById(R.id.vew_evt_tv_title_det);
        startTime = (TextView)findViewById(R.id.vew_evt_tv_start_time_det);
        startDate = (TextView)findViewById(R.id.vew_evt_tv_start_date_det);
        endTime = (TextView)findViewById(R.id.vew_evt_tv_end_time_det);
        endDate = (TextView)findViewById(R.id.vew_evt_tv_end_date_det);
        location = (TextView)findViewById(R.id.vew_evt_tv_loc_det);
        notes = (TextView)findViewById(R.id.vew_evt_tv_notes_det);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("iTitle"));
        startTime.setText(intent.getStringExtra("iStartTime"));
        startDate.setText(intent.getStringExtra("iStartDate"));
        endTime.setText(intent.getStringExtra("iEntTime"));
        endDate.setText(intent.getStringExtra("iEndDate"));
        location.setText(intent.getStringExtra("iLocation"));
        notes.setText(intent.getStringExtra("iNotes"));
    }

    public void cancel(View view){
        Intent intent = new Intent(ViewEvent.this, CreateEvent.class);
        finish();
        startActivity(intent);
    }
    public void delete(View view){
        //delete event from itinerary
    }
    public void edit(View view){
        Intent intent = new Intent(ViewEvent.this, CreateEvent.class);
        finish();
        startActivity(intent);
    }
}