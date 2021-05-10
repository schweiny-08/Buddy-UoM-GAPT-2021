package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void forgotPassword(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

//    public void contactUs(View view){
//        Intent intent = new Intent(this, ContactUs.class);
//        startActivity(intent);
//    }
//    public void help(View view){
//        Intent intent = new Intent(this, Help.class);
//        startActivity(intent);
//    }
}