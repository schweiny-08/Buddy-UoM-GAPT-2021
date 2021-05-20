package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class EditAccount extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
    }

    public void changePassword(View view){
        Intent intent = new Intent(EditAccount.this, ChangePassword.class);
        startActivity(intent);
    }

    public void save(View view){
        Intent intent = new Intent(EditAccount.this, ViewAccount.class);
        startActivity(intent);
    }

}