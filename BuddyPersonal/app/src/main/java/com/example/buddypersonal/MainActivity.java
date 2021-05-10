package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Connection connect;
    String ConnectionResult="";


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.nav_toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.nav_settings:
                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
                break;
//            case R.id.nav_map:
//                intent = new Intent(MainActivity.this, Map.class);
//                startActivity(intent);
//                break;
//            case R.id.nav_pu_events:
//                intent = new Intent(MainActivity.this, PublicEvents.class);
//                startActivity(intent);
//                break;
            case R.id.nav_itinerary:
                intent = new Intent(MainActivity.this, Itinerary.class);
                startActivity(intent);
                break;
            case R.id.nav_cr_events:
                intent = new Intent(MainActivity.this, CreateEvent.class);
                startActivity(intent);
                break;
//            case R.id.nav_buddy:
//                intent = new Intent(MainActivity.this, Buddy.class);
//                startActivity(intent);
//                break;
            case R.id.nav_logout:
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }


//    public void GetTextFromSQL(View v){
//        TextView tx1 = (TextView)findViewById(R.id.tv_conn1);
//        TextView tx2 = (TextView)findViewById(R.id.tv_conn2);
//
//        try {
//            ConnectionHelper connectionHelper = new ConnectionHelper();
//            connect = connectionHelper.ConnectionClass();
//            if(connect!=null){
//                String query = "SELECT* FROM PrivateEvents";
//                Statement st = connect.createStatement();
//                ResultSet rs = st.executeQuery(query);
//                while(rs.next()){
//                    tx1.setText(rs.getString(1));
//                    tx1.setText(rs.getString(2));
//                }
//            }
//            else{
//                ConnectionResult = "Check Connection";
//            }
//        }
//        catch (Exception ex){
//
//        }
//    }
}