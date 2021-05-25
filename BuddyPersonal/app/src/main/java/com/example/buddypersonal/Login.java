package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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

        loadFile("user_file");
        //loadFile("event_file");

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
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login(View view) {

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

    private String subFolder = "/userdata";
    //user_file, event_file

    public String loadFile(String file) {
        File cacheDir = null;
        File appDirectory = null;
        if (android.os.Environment.getExternalStorageState().
                equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File(cacheDir + subFolder);
        } else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File(BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists())
            return "Knowledge base does not exist."; // File does not exist

        File fileName = new File(appDirectory, file);

        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            Gson g = new Gson();

            //HashMap<String, String> myHashMap = (HashMap<String, String>) in.readObject();
            //knowledge = myHashMap;

            String temp = (String)in.readObject();
            ArrayList<User> usersTemp = new ArrayList((Collection) new Gson().fromJson(temp, User.class));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "I loaded my knowledge base.";
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

//    public void contactUs(View view){
//        Intent intent = new Intent(this, ContactUs.class);
//        startActivity(intent);
//    }
//    public void help(View view){
//        Intent intent = new Intent(this, Help.class);
//        startActivity(intent);
//    }
}