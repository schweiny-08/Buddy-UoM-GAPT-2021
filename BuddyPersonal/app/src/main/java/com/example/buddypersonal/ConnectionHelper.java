package com.example.buddypersonal;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;
    @SuppressLint("NewAPI")
    public Connection ConnectionClass(){
        ip = "46.11.171.238";
        database = "BuddyDBFrontendTesting";
        uname = "adminbuddy";
        pass = "Admin123";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" +port + ";" +database+"databasename="+database+";usern="+uname+";password"+pass+";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch(Exception ex){
            Log.e("Error ", ex.getMessage());
        }

        return connection;
    }
}
