package com.example.buddypersonal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalStorage {

    Gson gson = new Gson();

    public static ArrayList<User> usersList = new ArrayList<User>(); //need to add new users to list to be saved
    public static ArrayList<PublicEventModel> eventList = new ArrayList<PublicEventModel>(); //need to add new public events to list to be saved
    public static ArrayList<EventModel> privEventList = new ArrayList<EventModel>(); //need to add new private events list to be saved

    public static int loggedInUser = -1;
    public static int publicEvent = -1;
    public static int privateEvent = -1;
    public static String selDate = "";
    //opted to use pointers instead of global vars

    public static void setUser(int temp) {
        loggedInUser = temp;
    }//change pointer and use existing getters n setters

    public static void setPublic(int temp) {
        publicEvent = temp;
    }//when an event is selected, use this method to update the choice

    public static void setPrivate(int temp) {
        privateEvent = temp;
    }//when an event is selected, use this method to update the private choice

    public static PublicEventModel getPublic(int temp) {
        return eventList.get(temp);
    }

    public static EventModel getPrivate(int temp) {
        return privEventList.get(temp);
    }

    //other than this use already existing getters and setters, eliminates redundant code

    //private static final String user_file = "user";
    //private static final String event_file = "event";
    //name of json save files

    public static String getUserJson() {
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        String userJson = new Gson().toJson(usersList,type);
        return userJson;
    }

    public static String getEventsJson() {
        Type type = new TypeToken<ArrayList<PublicEventModel>>(){}.getType();
        String eventJson = new Gson().toJson(eventList, type);
        return eventJson;
    }

    public static String getPrivEventsJson() {
        Type type = new TypeToken<ArrayList<PublicEventModel>>(){}.getType();
        String privEventJson = new Gson().toJson(privEventList, type);
        return privEventJson;
    }

    //convert to json format using Gson Library
}
