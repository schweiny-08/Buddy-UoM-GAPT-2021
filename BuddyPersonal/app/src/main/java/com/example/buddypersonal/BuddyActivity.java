package com.example.buddypersonal;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class BuddyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static EditText userInput;
    static RecyclerView recyclerView;
    static MessageAdapter messageAdapter;
    static List<ResponseMessage> responseMessageList;
    //chat screen objects

    TextToSpeech textToSpeech;
    //text to speech objects

    ImageButton voiceButton;
    ImageButton enterButton;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    //speech to text objects

    static HashMap<String, String> knowledge = new HashMap<String, String>();
    String customAnswer = "";
    String customQuery = "";
    //buddy chat object

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navDrawer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy);

        userInput = findViewById(R.id.userInput);
        enterButton = findViewById(R.id.EnterBtn);

        recyclerView = findViewById(R.id.conversation);
        navDrawer = findViewById(R.id.drawer_icon);
        navDrawer.setOnClickListener(view -> openDrawer());

        responseMessageList = new ArrayList<>();  // chat history
        messageAdapter = new MessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        //chat screen objects

        voiceButton = findViewById(R.id.voiceBtn);

        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendText(userInput.getText().toString());
            }
        } );//enter button


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()  {
            @Override

            public void onInit(int i ) {
                if (i == TextToSpeech.SUCCESS) {
                    //Select language
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }

        }); //text to speech init

        voiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                speak();
            }
        } ); //speech to text button

        String temp = greeting();
        displayBotText(temp);
        ConvertToSpeech(temp);

        drawerLayout = findViewById(R.id.bud_drawer);
        navigationView = findViewById(R.id.bud_nav);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        buildKB();
        try {
            loadKB();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public String getLastUserMsg() {

        int i = responseMessageList.size() - 1;
        ResponseMessage temp = responseMessageList.get(i);

        while(!temp.isMe && i>0){
            i--;
            temp = responseMessageList.get(i);
        }
        return temp.text;
    }

    public void buildKB() {
        knowledge.put("Hi", "Hello... Pleased to meet you!");
        knowledge.put("Hello", "Oh hello there, how can I help?");
        knowledge.put("How are you?", "Great! And you?");
        knowledge.put("Buddy train me", "Okay, enter user query.");
        knowledge.put("Good morning", "Good morning, buddy.");
        knowledge.put("Good afternoon", "Good afternoon, buddy.");
        knowledge.put("Good evening", "Good evening, buddy.");
        knowledge.put("Good night", "Good night buddy, sleep tight.");
        knowledge.put("Bye", "Good bye.");
        knowledge.put("See you", "Have a good one, buddy.");
        knowledge.put("What's up", "Oh not much, just planning and organizing activities for the day. How about you?");
        knowledge.put("Marco", "Polo.");
        knowledge.put("", "meh");
        knowledge.put("cool", "I agree.");
        knowledge.put("Thanks", "You're welcome.");
        knowledge.put("Thank you", "You are welcome!");
        knowledge.put("Hey buddy", "Hey, what's up?");
        knowledge.put("Hey", "Hey!");
        knowledge.put("Buddy", "Buddy.");
        knowledge.put("Are you a robot?", "Yes I am.");
        knowledge.put("Robot", "Human.");
        knowledge.put("what is love","Baby don't hurt me.");
        knowledge.put("i love you", "We're just good *buddies* :)");
        //knowledge.put("What are you able to do?", "I can do.....");
        saveKB(getBuddyJson(), getApplicationContext());
    }

    public String getLastBotMsg() {

        int i = responseMessageList.size() - 1;
        ResponseMessage temp = responseMessageList.get(i);

        while(temp.isMe && i>0){
            i--;
            temp = responseMessageList.get(i);
        }
        return temp.text;
    }

    private void speak() {
        //intent to show speech to text dialog
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        //start intent
        try {
            //in there was no error, show dialog
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e) {
            //if there is some error
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } //speech to text method, get speech from user

    public void ConvertToSpeech(String s) {  // speaks given strings
        int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void sendText(String temp) { //text editor action, send text to chat screen ie. ENTER function, send User text.

        if (!temp.toString().equals("")) { //check passed string contents, if not empty or null

            userInput.setText(temp.toString());

            ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true); //true for user, false for bot bubble
            responseMessageList.add(responseMessage);

            String bot_response = answer(temp); //get reply from bot
            ResponseMessage responseMessage2 = new ResponseMessage(bot_response.toString(), false);
            responseMessageList.add(responseMessage2);

            ConvertToSpeech(bot_response); //speak reply from bot

            messageAdapter.notifyDataSetChanged();
            if (!isLastVisible())
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            userInput.setText("");

            saveKB(getBuddyJson(), getApplicationContext());
        }
    }

    public String getBuddyJson() {
        Type type = new TypeToken<HashMap<String,String>>(){}.getType();
        String buddyJson = new Gson().toJson(knowledge, type);
        return buddyJson;
    }

    public void displayBotText(String temp) { //send Bot Text.
        if (!temp.toString().equals("")) {

            ResponseMessage responseMessage = new ResponseMessage(temp.toString(), false);
            responseMessageList.add(responseMessage);
            //ConvertToSpeech(temp);

            messageAdapter.notifyDataSetChanged();
            if (!isLastVisible())
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    // get text array from voice intent
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //set to text view
                    userInput.setText(result.get(0));
                    enterButton.performClick();
                }
                break;
            }
        }
    } //speech to text method...

    static boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    } //chat screen method, check if last message is visible

    private static void saveKB(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("buddy_file.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void loadKB() throws JSONException {

        //load knowledge base into local

        String temp = readFromFile("buddy_file.txt", getApplicationContext()); //read from file, get json to string

        Gson gson = new Gson();

        Type buddyType = new TypeToken<HashMap<String, String>>() {
        }.getType();

        HashMap<String,String> buddyArray = gson.fromJson(temp, buddyType);

        knowledge = buddyArray; //populate local storage array list
    }

    public String readFromFile(String file, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            RegisterActivity.writeToFile("",getApplicationContext());
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
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

    public String showIter() {
        Intent iter = new Intent (this, ItineraryActivity.class);
        finish();
        startActivity(iter);
        return "I wonder what's planned for today";
    }

    public String showSettings() {
        Intent setts = new Intent (this, SettingsActivity.class);
        finish();
        startActivity(setts);
        return "Of course!";
    }

    public String showMap() {
        Intent map = new Intent (this, ViewMapActivity.class);
        finish();
        startActivity(map);
        return "Sure thing!";
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_settings:
                Intent iSettings = new Intent(BuddyActivity.this, SettingsActivity.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(BuddyActivity.this, HomeActivity.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(BuddyActivity.this, ViewMapActivity.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(BuddyActivity.this, VenueEventActivity.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(BuddyActivity.this, ItineraryActivity.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(BuddyActivity.this, CalendarActivity.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(BuddyActivity.this, EditEventActivity.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(BuddyActivity.this, BuddyActivity.class);
//                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(BuddyActivity.this, LoginActivity.class);
//                finish();
                startActivity(iLogin);
                Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getTime() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strTime = "The time is : " + mdformat.format(calendar.getTime());
        return strTime;

    }

    public String getDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = "Today is: " + mdformat.format(calendar.getTime());
        return strDate;
    }

    public String forgotPass() {
        Intent pass = new Intent (this, ForgotPasswordActivity.class);
        finish();
        startActivity(pass);
        return "No worries. We can fix that.";
    }

    public String showProfile() {
        Intent prof = new Intent (this, ViewAccountActivity.class);
        finish();
        startActivity(prof);
        return "Sure thing!";
    }

    public String createEv() {
        Intent ev = new Intent (this, EditEventActivity.class);
        finish();
        startActivity(ev);
        return "What's new?";
    }

    public String goHome() {
        Intent home = new Intent (this, HomeActivity.class);
        finish();
        startActivity(home);
        return "Next stop: Home";
    }

    public String greeting() {
        String temp = "Hey "+LocalStorage.usersList.get(LocalStorage.loggedInUser).getUName();
        //good morning buddy
        //good after buddy
        //whats up buddy
        //how can i help buddy
        return temp;
    }

    public static String get2ndLastUserMsg() {

        int i = responseMessageList.size() - 1;
        ResponseMessage temp = responseMessageList.get(i);

        while(!temp.isMe && i>0){
            i--;
            temp = responseMessageList.get(i);
        }

        if(i>0) {
            i--;
            temp = responseMessageList.get(i);

            while(!temp.isMe && i>0){
                i--;
                temp = responseMessageList.get(i);
            }

            return temp.text;
        }else {
            return "Not found";
        }

    }

    public String answer(String question) {
        Set<String> keys = knowledge.keySet();
        String temp = "";
        boolean found = false;

        String Lquestion = question.toLowerCase();

        if(Lquestion.equals("show me the map"))
            return showMap();

        if(Lquestion.equals("show me the itinerary"))
            return showIter();

        if(Lquestion.equals("show me today's events"))
            return showIter();

        if(Lquestion.equals("show me the settings"))
            return showSettings();

        if(Lquestion.equals("what time is it"))
            return getTime();

        if(Lquestion.equals("what day is it"))
            return getDate();

        if(Lquestion.equals("show me my profile"))
            return showProfile();

        if(Lquestion.equals("i forgot my password"))
            return forgotPass();

        if(Lquestion.equals("take me home"))
            return goHome();

        if(Lquestion.equals("i want to create a new event"))
            return createEv();

        //commands/functions

        if (getLastBotMsg().equals("I don't know about that. How should I reply?")) {
            knowledge.put(get2ndLastUserMsg(), question);
            return "Thanks for teaching me!";
        } //train bot for unknown answers to questions

        if (getLastBotMsg().equals("Okay, enter how my reply should be.")) {
            customAnswer = question;
            knowledge.put(customQuery, customAnswer);

            return "Thanks for teaching me!";
        }//custom bot training functionality

        if (getLastBotMsg().equals("Okay, enter user query.")) {
            customQuery = question;
            return "Okay, enter how my reply should be.";

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
                temp = "I don't know about that. How should I reply?";
            }
        }//custom bot training functionality

        return temp;
    }
}