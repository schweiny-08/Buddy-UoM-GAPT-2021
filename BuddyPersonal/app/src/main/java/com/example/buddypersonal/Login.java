package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.log_et_email);
        password = (EditText) findViewById(R.id.log_et_password);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login(View view) {
        if ((TextUtils.isEmpty(email.getText().toString()))||(TextUtils.isEmpty(password.getText().toString()))) {
            Toast.makeText(Login.this, "Please make sure you have entered the necessary credentials.", Toast.LENGTH_SHORT).show();
        }
        else if (!isEmailValid(email.getText().toString())){
            Toast.makeText(Login.this, "The email you provided is invalid.", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().length()<8){
            Toast.makeText(Login.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
        }
//        else if() {
//            //when email and password do not match a registered set.
//        Toast.makeText(Login.this, "The credentials do not match any existing account.", Toast.LENGTH_SHORT).show();
//        }
        else{
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void forgotPassword(View view) {
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