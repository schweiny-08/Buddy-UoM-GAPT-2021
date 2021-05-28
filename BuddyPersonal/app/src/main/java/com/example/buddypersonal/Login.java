package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.log_et_email);
        password = (EditText) findViewById(R.id.log_et_password);
        LocalStorage.loggedInUser = -1;

        contact = (Button) findViewById(R.id.log_btn_contact_us);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"buddy_customer@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                //intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                //intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                //intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sys_toolbar)));
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login(View view) {

        try {
            build_db();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Toast.makeText(Login.this, LocalStorage.usersList.toString(), Toast.LENGTH_LONG).show();

        int temp = checkForMatch(email.getText().toString(), password.getText().toString()); //check for email and password match, returns user id. returns -1 if not found.

        if ((TextUtils.isEmpty(email.getText().toString()))||(TextUtils.isEmpty(password.getText().toString()))) {
            Toast.makeText(Login.this, "Please make sure you have entered the necessary credentials.", Toast.LENGTH_SHORT).show();
        }
        else if (!isEmailValid(email.getText().toString())){
            Toast.makeText(Login.this, "The email you provided is invalid.", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().length()<8){
            Toast.makeText(Login.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
        }
        else if(temp == -1) {
            //when email and password do not match a registered set.
            Toast.makeText(Login.this, "The credentials do not match any existing account.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
            //update user id after validation
            LocalStorage.loggedInUser = temp;
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        finish();
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        finish();
        startActivity(intent);
    }

    public static String readFromFile(String file, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void build_db() throws JSONException {

        //load users into local arraylist

        String temp = readFromFile("users_file.txt", getApplicationContext()); //read from file, get json to string

        Gson gson = new Gson();

        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();

        ArrayList<User> userArray = gson.fromJson(temp, userListType);

        LocalStorage.usersList = userArray; //populate local storage array list

        //load private events into local arraylist

        temp = readFromFile("priv_events_file.txt", getApplicationContext()); //read from file, get json to string

        Type privateEventListType = new TypeToken<ArrayList<EventModel>>(){}.getType();

        ArrayList<EventModel> privEventArray = gson.fromJson(temp, privateEventListType);

        LocalStorage.privEventList = privEventArray; //populate local storage array list
//
//        //load public events into local arraylist
//
//        temp = readFromFile("public_events_file.txt", getApplicationContext()); //read from file, get json to string
//
//        Type publicEventListType = new TypeToken<ArrayList<PublicEventModel>>(){}.getType();
//
//        ArrayList<PublicEventModel> publicEventArray = gson.fromJson(temp, publicEventListType);
//
//        LocalStorage.eventList = publicEventArray; //populate local storage array list

    }

    public int checkForMatch (String email, String pass) {
        //this was not our part to do. back end function
        int i = 0;
        while(i>-1 && i<LocalStorage.usersList.size()) {
            if(LocalStorage.usersList.get(i).getEmail().equals(email)) {
                if(LocalStorage.usersList.get(i).getPassword().equals(pass)) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

//    public void help(View view){
//        Intent intent = new Intent(this, Help.class);
//        startActivity(intent);
//    }
}