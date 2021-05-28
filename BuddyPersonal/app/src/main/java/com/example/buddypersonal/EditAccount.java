package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EditAccount extends AppCompatActivity{

    EditText name, surname, email, username;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        name = (EditText) findViewById(R.id.act_et_name);
        surname = (EditText) findViewById(R.id.act_et_surname);
        email = (EditText) findViewById(R.id.act_et_email);
        username = (EditText) findViewById(R.id.act_et_username);

        name.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUName());
        surname.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUSurname());
        email.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getEmail());
        username.setText(LocalStorage.usersList.get(LocalStorage.loggedInUser).getUsername());
        //dob?
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sys_toolbar)));
    }

    public void changePassword(View view){
        Intent intent = new Intent(EditAccount.this, ChangePassword.class);
        startActivity(intent);
    }

    public void save(View view){

        if(!Register.checkEmailValid(email.toString()))
            Toast.makeText(EditAccount.this, "This email has already been registered!", Toast.LENGTH_SHORT).show();
            else if(!Register.checkUserValid(username.toString()))
            Toast.makeText(EditAccount.this, "This username has already been registered!", Toast.LENGTH_SHORT).show();
                else{

            LocalStorage.usersList.get(LocalStorage.loggedInUser).setName(name.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setSurname(surname.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setEmail(email.getText().toString());
            LocalStorage.usersList.get(LocalStorage.loggedInUser).setUsername(username.getText().toString());
            Register.writeToFile(LocalStorage.getUserJson(), getApplicationContext());

            Intent intent = new Intent(EditAccount.this, ViewAccount.class);
            startActivity(intent);
        }
    }

}