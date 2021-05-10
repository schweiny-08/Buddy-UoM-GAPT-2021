package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

//    public void map(View view){
//        Intent intent = new Intent(this, Map.class);
//        startActivity(intent);
//    }

    public void itinerary(View view){
        Intent intent = new Intent(this, Itinerary.class);
        startActivity(intent);
    }

//    public void buddy(View view){
//        Intent intent = new Intent(this, Buddy.class);
//        startActivity(intent);
//    }
}