package com.example.buddypersonal;

import android.content.Intent;
import android.text.Editable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;


public class buddy_bot {
    static HashMap<String, String> knowledge = new HashMap<String, String>();
    static String customAnswer = "";
    static String customQuery = "";


    public buddy_bot() { //bot knowledge base
        knowledge.put("Hi", "Hello... Pleased to meet you!");
        knowledge.put("Hello", "Oh hello there, how can I help?");
        knowledge.put("How are you?", "Great! And you?");
        knowledge.put("What time is it?", getTime());
        knowledge.put("Buddy train me", "Okay, enter user query");
        knowledge.put("Show events", showEvents());
        knowledge.put("Good morning", "Good morning buddy");
        knowledge.put("Good afternoon", "Good afternoon buddy");
        knowledge.put("Good evening", "Good evening buddy");
        knowledge.put("Good night", "Good night buddy, sleep tight");
        knowledge.put("Bye", "Good bye");
        knowledge.put("See you", "Have a good one, buddy");
        knowledge.put("What's up", "Oh not much, just planning and organizing activities for the day. How about you?");
        knowledge.put("Marco", "Polo");
        knowledge.put("What day is it today?", getDate());
        knowledge.put("", "meh");
        knowledge.put("cool", "I agree");
        knowledge.put("Thanks", "You're welcome");
        knowledge.put("Thank you", "You are welcome!");
        knowledge.put("Tell me a joke", "this project");
        knowledge.put("Hey buddy", "Hey, what's up?");
        knowledge.put("Hey", "Hey!");
        knowledge.put("Buddy", "Buddy");
        knowledge.put("Are you a robot?", "Yes I am");
        knowledge.put("Robot", "Human");
        knowledge.put("What are you able to do?", "I can do.....");
        //knowledge.put("Save knowledge base", saveKB(knowledge));
        // knowledge.put("Load knowledge base", loadKB());
        //show map
        //show profile
        //show settings
    }


    public String answer(String question) {
        Set<String> keys = knowledge.keySet();
        String temp = "";
        boolean found = false;

        if (Buddy.getLastBotMsg().equals("How should I reply?")) {
            knowledge.put(Buddy.getLastUserMsg(), question);
            return "Thanks for teaching me!";
        } //train bot for unknown answers to questions

        if (Buddy.getLastBotMsg().equals("Okay, enter how my reply should be")) {
            customAnswer = question;
            knowledge.put(customQuery, customAnswer);

            return "Thanks for teaching me!";
        }//custom bot training functionality

        if (Buddy.getLastBotMsg().equals("Okay, enter user query")) {
            customQuery = question;
            return "Okay, enter how my reply should be";

        } else {
            for (String key : keys) {
                String lowerKey = key.toLowerCase();
                String lowerQuestion = question.toLowerCase();

                if (lowerKey.contains(lowerQuestion)) {
                    temp = knowledge.get(key);
                    found = true;
                }
            }

            if (!found) {
                temp = "I don't know about that";
            }
        }//custom bot training functionality

        return temp;
    }

    public static String greeting() {
        String temp = "Hey Buddy!";

        //good morning buddy
        //good after buddy
        //whats up buddy
        //how can i help buddy
        //arraylist

        return temp;
    }

    public String getweather() {
        return "test";
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strTime = "The time is : " + mdformat.format(calendar.getTime());
        return strTime;
        //speak
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = "Today is: " + mdformat.format(calendar.getTime());
        return strDate;
        //speak
    }

    public String showEvents() {
        //go to events page
        // or read from events list. for loop index 1,2,3 ... end
        //new intent
        return "test";
    }

    public String showIter() {
        //Intent intent = new Intent (this, Main2Activity.class);
        //startActivity(intent);
        return "test";
    }

    public String showSettings() {
        return "test";
    }

    public String showMap() {
        return "test";
    }

}


