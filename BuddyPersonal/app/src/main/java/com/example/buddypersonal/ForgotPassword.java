package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        try {
            ArrayList<String> toEmailList = new ArrayList<String>();
            toEmailList.add("cedricmuscat1@gmail.com");

            new SendMailTask(ForgotPassword.this).execute("buddypersonal21@gmail.com",
                    "GAPT2021", toEmailList, "This is Subject", "This is Body");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sys_toolbar)));
    }

    public void resetPassword(View view){
        Toast.makeText(this, "Your password has been successfully reset.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}