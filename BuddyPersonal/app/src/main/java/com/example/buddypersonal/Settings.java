package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void account(View view){
        Intent intent = new Intent(this, Accessibility.class);
        startActivity(intent);
    }
    public void accessibility(View view){
        Intent intent = new Intent(this, Accessibility.class);
        startActivity(intent);
    }
    public void customisation(View view){
        Intent intent = new Intent(this, Accessibility.class);
        startActivity(intent);
    }
    public void howToUse(View view){
        Intent intent = new Intent(this, Accessibility.class);
        startActivity(intent);
    }
}