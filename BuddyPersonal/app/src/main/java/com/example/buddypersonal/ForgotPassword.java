package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ForgotPassword extends AppCompatActivity {

    int code = 0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sys_toolbar)));
    }

    public void resetPassword(View view){
        Toast.makeText(this, "Your password has been successfully reset.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public static int getRandNum(int lowerBound, int upperBound) {
        Random random = new Random();
        int randomNumber;
        return randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;

    }

    public void sendMail(String recipient){

        code = getRandNum(1001,9999); //example of how to use.

                try {
            ArrayList<String> toEmailList = new ArrayList<String>();
            toEmailList.add(recipient);

            new SendMailTask(ForgotPassword.this).execute("buddypersonal21@gmail.com",
                    "GAPT2021", toEmailList, "Your Buddy Personal Password Code Is Here", "Your code is "+code);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
}