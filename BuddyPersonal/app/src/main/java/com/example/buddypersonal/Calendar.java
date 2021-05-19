package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //get data related to the specific date chosen
                String date = dayOfMonth + "/" + month + "/" + year;
                Intent intent = new Intent(Calendar.this, CalendarList.class);
                intent.putExtra("selected date", date);
                startActivity(intent);
            }
        });
    }
}