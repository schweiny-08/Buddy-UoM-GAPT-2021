package com.example.buddypersonal;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Buddy extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.userInput);
        enterButton = findViewById(R.id.EnterBtn);

        recyclerView = findViewById(R.id.conversation);

        responseMessageList = new ArrayList<>();  // chat history
        messageAdapter = new MessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        //chat screen objects

        voiceButton = findViewById(R.id.voiceBtn);

        buddy_bot.greeting();

        /**userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() { // chat editor button
         @Override
         public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) { //text editor action

         //Editable temp = userInput.getText();


         if (!userInput.getText().equals("")) {
         if (i == EditorInfo.IME_ACTION_SEND) { //enter from onscreen keyboard

         sendText(userInput.getText().toString());
         //ResponseMessage my_message = new ResponseMessage(userInput.getText().toString(), true);
         //responseMessageList.add(my_message); //add my message

         //ResponseMessage bot_message = new ResponseMessage(userInput.getText().toString(), false);
         //responseMessageList.add(bot_message); //add bot message

         messageAdapter.notifyDataSetChanged(); //update display

         if (!isLastVisible()) // is latest message visible? if not scroll down.
         recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

         userInput.setText("");
         }
         }
         return false;
         }


         }); //chat screen functions**/

        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendText(userInput.getText().toString());
            }
        } );//enter button

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
        //int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
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
        }

    }

    public static void displayBotText(String temp) { //send Bot Text.
        if (!temp.toString().equals("")) {

            ResponseMessage responseMessage = new ResponseMessage(temp.toString(), false);
            responseMessageList.add(responseMessage);

            //ConvertToSpeech(temp); //speak reply from bot

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
    } //chat screen method

    /**public static void getBotReply(String query) {
     displayBotText(buddy.answer(query));
     }**/


}