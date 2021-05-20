package com.example.buddypersonal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Buddy extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

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

    static buddy_bot buddy = new buddy_bot();
    //buddy chat object

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy);

        userInput = findViewById(R.id.userInput);
        enterButton = findViewById(R.id.EnterBtn);

        recyclerView = findViewById(R.id.conversation);

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

        String temp = buddy_bot.greeting();
        displayBotText(temp);
        //ConvertToSpeech(temp);

        loadKB();


        drawerLayout = findViewById(R.id.bud_drawer);
        navigationView = findViewById(R.id.bud_nav);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    /*public static String getLastChatMsg() {
        ResponseMessage temp = responseMessageList.get(responseMessageList.size() - 1);
        return temp.text;
    }*/

    public static String getLastUserMsg() {

        int i = responseMessageList.size() - 1;
        ResponseMessage temp = responseMessageList.get(i);

        while(!temp.isMe && i>0){
            i--;
            temp = responseMessageList.get(i);
        }
        return temp.text;
    }

    /*public static String get2ndLastUserMsg() {

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

    }*/

    public static String getLastBotMsg() {

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

            String bot_response = buddy.answer(temp); //get reply from bot
            ResponseMessage responseMessage2 = new ResponseMessage(bot_response.toString(), false);
            responseMessageList.add(responseMessage2);

            ConvertToSpeech(bot_response); //speak reply from bot

            messageAdapter.notifyDataSetChanged();
            if (!isLastVisible())
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            userInput.setText("");

            saveKB();
        }
    }

    public static void displayBotText(String temp) { //send Bot Text.
        if (!temp.toString().equals("")) {

            ResponseMessage responseMessage = new ResponseMessage(temp.toString(), false);
            responseMessageList.add(responseMessage);

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

    private String subFolder = "/userdata";
    private String file = "buddy_kb.txt";

    public String saveKB() {
        File cacheDir = null;
        File appDirectory = null;

        if (android.os.Environment.getExternalStorageState().
                equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File(cacheDir + subFolder);

        } else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File(BaseFolder + subFolder);

        }

        if (appDirectory != null && !appDirectory.exists()) {
            appDirectory.mkdirs();
        }

        File fileName = new File(appDirectory, file);

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(buddy_bot.knowledge);
        } catch (IOException ex) {
            ex.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.flush();
                fos.close();
                if (out != null)
                    out.flush();
                out.close();
            } catch (Exception e) {

            }
        }

        return "I saved my knowledge base.";
    }

    public String loadKB() {
        File cacheDir = null;
        File appDirectory = null;
        if (android.os.Environment.getExternalStorageState().
                equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = getApplicationContext().getExternalCacheDir();
            appDirectory = new File(cacheDir + subFolder);
        } else {
            cacheDir = getApplicationContext().getCacheDir();
            String BaseFolder = cacheDir.getAbsolutePath();
            appDirectory = new File(BaseFolder + subFolder);
        }

        if (appDirectory != null && !appDirectory.exists())
            return "Knowledge base does not exist."; // File does not exist

        File fileName = new File(appDirectory, file);

        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            HashMap<String, String> myHashMap = (HashMap<String, String>) in.readObject();
            buddy_bot.knowledge = myHashMap;
            //System.out.println("count of hash map::"+knowledge.size() + " " + knowledge);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (fis != null) {
                    fis.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "I loaded my knowledge base.";
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
        switch(menuItem.getItemId()){
            case R.id.nav_settings:
                Intent iSettings = new Intent(Buddy.this, Settings.class);
//                finish();
                startActivity(iSettings);
                break;
            case R.id.nav_home:
                Intent iProfile = new Intent(Buddy.this, Home.class);
//                finish();
                startActivity(iProfile);
                break;
            case R.id.nav_map:
                Intent iMap = new Intent(Buddy.this, MapView.class);
//                finish();
                startActivity(iMap);
                break;
            case R.id.nav_pu_events:
                Intent iPuEvents = new Intent(Buddy.this, VenueEvents.class);
//                finish();
                startActivity(iPuEvents);
                break;
            case R.id.nav_itinerary:
                Intent iItinerary = new Intent(Buddy.this, Itinerary.class);
//                finish();
                startActivity(iItinerary);
                break;
            case R.id.nav_calendar:
                Intent iCalendar = new Intent(Buddy.this, Calendar.class);
//                finish();
                startActivity(iCalendar);
                break;
            case R.id.nav_cr_events:
                Intent iCrEvents = new Intent(Buddy.this, CreateEvent.class);
//                finish();
                startActivity(iCrEvents);
                break;
            case R.id.nav_buddy:
                Intent iBuddy = new Intent(Buddy.this, Buddy.class);
//                finish();
                startActivity(iBuddy);
                break;
            case R.id.nav_logout:
                Intent iLogin = new Intent(Buddy.this, Login.class);
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
}