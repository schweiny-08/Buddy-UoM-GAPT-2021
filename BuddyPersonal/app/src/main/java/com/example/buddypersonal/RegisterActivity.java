package com.example.buddypersonal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    TextView dob;
    EditText name, surname, email, username, password, confPassword;
    Button register;
    CheckBox toc;
    Calendar DOB, CURR;
    Picker picker = new Picker();


    Button contact;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    String sName, sSurname, sDob, sEmail, sUsername, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.1.90:44346/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        name = (EditText) findViewById(R.id.reg_et_name);
        surname = (EditText) findViewById(R.id.reg_et_surname);
        dob = (TextView) findViewById(R.id.reg_et_dob);
        email = (EditText) findViewById(R.id.reg_et_email);
        username = (EditText) findViewById(R.id.reg_et_username);
        password = (EditText) findViewById(R.id.reg_et_password);
        confPassword = (EditText) findViewById(R.id.reg_et_conf_password);
        toc = (CheckBox) findViewById(R.id.reg_cb_tac);
        register = (Button) findViewById(R.id.reg_bt_register);

        sName = name.getText().toString();
        sSurname = surname.getText().toString();
        sDob = dob.getText().toString();
        sEmail = email.getText().toString();
        sUsername = username.getText().toString();
        sPassword = password.getText().toString();

        //DATE
        dob = (TextView) findViewById(R.id.reg_et_dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setPicker(dob);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        contact = (Button) findViewById(R.id.reg_bt_contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] recipients = {"buddy_customer@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                //intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                //intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                //intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        CURR = Calendar.getInstance();
        DOB = Calendar.getInstance();
        DOB.set(java.util.Calendar.YEAR, year);
        DOB.set(java.util.Calendar.MONTH, month);
        DOB.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DOB.set(Calendar.HOUR_OF_DAY, 0);
        DOB.set(Calendar.MINUTE, 0);
        DOB.set(Calendar.SECOND, 0);
        DOB.set(Calendar.MILLISECOND, 0);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(DOB.getTime());
        picker.getPicker().setText(currentDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void register(View view) {
        registerUser();
    }

    private void registerUser() {

        sName = name.getText().toString();
        sSurname = surname.getText().toString();
        sDob = dob.getText().toString();
        sEmail = email.getText().toString();
        sUsername = username.getText().toString();
        sPassword = password.getText().toString();

        if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(surname.getText().toString())) || (TextUtils.isEmpty(dob.getText().toString())) || (TextUtils.isEmpty(email.getText().toString())) || (TextUtils.isEmpty(username.getText().toString())) || (TextUtils.isEmpty(password.getText().toString())) || (TextUtils.isEmpty(confPassword.getText().toString()))) {
            Toast.makeText(RegisterActivity.this, "Please make sure you have filled  the necessary credentials.", Toast.LENGTH_SHORT).show();
        } else if (CURR.before(DOB)||CURR.equals(DOB)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid Date of Birth.", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "The email you provided is invalid.", Toast.LENGTH_SHORT).show();
        } else if (sPassword.length() < 8) {
            Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
        } else if (!sPassword.equals(confPassword.getText().toString())) { //pass do not match
            Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        } else if (!toc.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
        } else if (!checkUserValid(sUsername)) {
            Toast.makeText(RegisterActivity.this, "This username has already been registered!", Toast.LENGTH_SHORT).show();
        } else if (!checkEmailValid(sEmail)) { //email already registered
            Toast.makeText(RegisterActivity.this, "This email has already been registered!", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(LocalStorage.usersList.size() + 1, sUsername, 12345678, sEmail, sPassword, 0, sName, sSurname, sDob);

            LocalStorage.usersList.add(user);
            writeToFile(LocalStorage.getUserJson(), getApplicationContext());

            Call<User> call = jsonPlaceHolderApi.registerUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (!response.isSuccessful()) {
                        //Toast.makeText(Register.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
//                User userResponse = response.body();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    //Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            Toast.makeText(RegisterActivity.this, "User has been successdully registered!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("users_file.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static boolean checkEmailValid(String email) {
        //this was not our part to do. back end function
        int i = 0;
        while (i > -1 && i < LocalStorage.usersList.size()) {
            if (LocalStorage.usersList.get(i).getEmail().equals(email)) {
                return false;
                //match is found. Is valid? no.
            }
            i++;
        }
        return true; /// no match found. is valid? yes
    }

    public static boolean checkUserValid(String user) {
        //this was not our part to do. back end function
        int i = 0;
        while (i > -1 && i < LocalStorage.usersList.size()) {
            if (LocalStorage.usersList.get(i).getUsername().equals(user)) {
                return false;
                //match is found. Is valid? no.
            }
            i++;
        }
        return true; /// no match found. is valid? yes
    }
}