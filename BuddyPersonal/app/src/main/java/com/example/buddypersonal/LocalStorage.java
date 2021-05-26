package com.example.buddypersonal;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LocalStorage {

    Gson gson = new Gson();

    public static ArrayList<User> usersList = new ArrayList<User>(); //need to add new users to list to be saved
    public static ArrayList<PublicEventModel> eventList = new ArrayList<PublicEventModel>(); //need to add new public events to list to be saved

    public static int loggedInUser = 0;
    public static int publicEvent = 0;
    public static int privateEvent = 0;
    //opted to use pointers instead of global vars

    public static void setUser(int temp) {
        loggedInUser = temp;
    }//change pointer and use existing getters n setters

    //public static User getUser(int temp) {
      //  return loggedInUser;
    //}

    public static void setPublic(int temp) {
        publicEvent = temp;
    }//when an event is selected, use this method to update the choice

    public static void setPrivate(int temp) {
        privateEvent = temp;
    }//when an event is selected, use this method to update the private choice

    //public static PublicEventModel getPublic(int temp) {
      //  return publicEvent;
    //}

    //public static EventModel getPrivate(int temp) {
    //    return privateEvent;
   // }

    //other than this use already existing getters and setters, eliminates redundant code

    //private static final String user_file = "user";
    //private static final String event_file = "event";
    //name of json save files

    public static String getUserJson() {
        String userJson = new Gson().toJson(usersList);
        return userJson;
    }

    public static String getEventsJson() {
        String eventJson = new Gson().toJson(eventList);
        return eventJson;
    }

    //convert to json format using Gson Library

}