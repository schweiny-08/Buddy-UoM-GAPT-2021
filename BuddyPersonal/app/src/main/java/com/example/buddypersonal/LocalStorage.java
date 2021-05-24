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

    private Context parent;
    private FileInputStream fileIn;
    private FileOutputStream fileOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Object outputObject;
    private String filePath;

    //public static Integer user_Id, telephone, role_Id;
    //public static String username, email, password;
    //global vars for user

    //private int puEventId, architectId, siteId, userId;
    //private String title, startTime, startDate, endTime, endDate, location, description;
    //global vars for selected events

    public static User loggedInUser = new User();
    public static PublicEventModel publicEvent = new PublicEventModel();
    public static EventModel privateEvent = new EventModel();
    //opted to use pointers instead of global vars

    public LocalStorage() {

    }

    public static void setUser(User temp) {
        loggedInUser = temp;
    }//change pointer and use existing getters n setters

    public static User getUser() {
        return loggedInUser;
    }

    public static void setPublic(PublicEventModel temp) {
        publicEvent = temp;
    }//when an event is selected, use this method to update the choice

    public static void setPrivate(EventModel temp) {
        privateEvent = temp;
    }//when an event is selected, use this method to update the private choice

    public static PublicEventModel getPublic() {
        return publicEvent;
    }

    public static EventModel getPrivate() {
        return privateEvent;
    }

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

    //convert to json format using Gson

}
