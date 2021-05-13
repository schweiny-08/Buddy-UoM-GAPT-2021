package com.example.buddypersonal;

import android.text.Editable;

import java.util.*;

/**
 * This class is a dummy chat bot
 */
public class buddy_bot {
    HashMap<String, String> knowledge = new HashMap<String, String>();
    /**
     * This is a default constructor.
     */
    public buddy_bot() {
        knowledge.put("Hi", "Hello... Pleased to meet you!");
        knowledge.put("Hello", "Hi yo");
        knowledge.put("how are you?", "Great! And you?");
        knowledge.put("what time is it?", "Look at your watch!");
    }

    /**
     * @param question This is user input as string
     */
    public String answer(String question) {
        Set<String> keys = knowledge.keySet();
        String temp = ""; boolean found = false;
        for (String key : keys){
            String lowerKey = key.toLowerCase();
            String lowerQuestion = question.toLowerCase();
            if (lowerKey.contains(lowerQuestion)) {
                temp =  knowledge.get(key);
                found = true;

            }
        }

        if(!found)
           temp = trainMe(question);

        return temp;
    }

    public String trainMe(String question) {


        //Buddy.displayBotText("Sorry, how should I reply?");

        //String temp = Buddy.userInput.getText().toString();



        //knowledge.put(question, temp);

        return "Thanks for teaching me!";
    }

    public static void greeting() {
        Buddy.displayBotText("hey buddy");
        //good morning buddy
        //good after buddy
        //whats up buddy
        //how can i help buddy
        //arraylist
    }

    public void getweather() {
        Buddy.displayBotText("what city");
        //search for city using input

    }

    public void gettime() {

    }

    public void showevents() {
        //go to events page
        // or read from events list. for loop index 1,2,3 ... end
    }


}
