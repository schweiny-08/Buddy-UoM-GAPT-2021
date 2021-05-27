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
            LocalStorage.setUser(temp);
            Intent intent = new Intent(this, Home.class);
//            finish();
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

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

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
        String temp = readFromFile(getApplicationContext());
        ArrayList<User> tempusersList = new ArrayList<User>();

        JSONArray jsonArray = new JSONArray(temp);
        JSONObject jsnobject = new JSONObject(temp);

        jsonArray = jsnobject.getJSONArray("locations");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            String email = jsonArray.getJSONObject(i).getString("email");
            String pass = jsonArray.getJSONObject(i).getString("password");
            //array.getJSONObject(i).getString("privateEvents");
            int role = jsonArray.getJSONObject(i).getInt("role_Id");
            int tel = jsonArray.getJSONObject(i).getInt("telephone");
            String dob = jsonArray.getJSONObject(i).getString("uDob");
            String uname = jsonArray.getJSONObject(i).getString("uName");
            String surn = jsonArray.getJSONObject(i).getString("uSurname");
            int uid = jsonArray.getJSONObject(i).getInt("user_Id");
            String user = jsonArray.getJSONObject(i).getString("username");
        }

//        JSONObject array = obj.getJSONObject();
//        for(int i = 0 ; i < array.length() ; i++){
//            //get parameters from json
//            String email = array.getJSONObject(i).getString("email");
//            String pass = array.getJSONObject(i).getString("password");
//            //array.getJSONObject(i).getString("privateEvents");
//            int role = array.getJSONObject(i).getInt("role_Id");
//            int tel = array.getJSONObject(i).getInt("telephone");
//            String dob = array.getJSONObject(i).getString("uDob");
//            String uname = array.getJSONObject(i).getString("uName");
//            String surn = array.getJSONObject(i).getString("uSurname");
//            int uid = array.getJSONObject(i).getInt("user_Id");
//            String user = array.getJSONObject(i).getString("username");
//
//            User usernew = new User(uid, user, tel, email, pass, role, uname, surn, dob);
//            tempusersList.add(usernew);
//        }

        LocalStorage.usersList = tempusersList;

    }

    private String subFolder = "/userdata";
    //user_file, event_file



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