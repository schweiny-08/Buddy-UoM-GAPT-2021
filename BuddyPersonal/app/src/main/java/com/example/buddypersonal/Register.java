package com.example.buddypersonal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    TextView dob;
    EditText name, surname, email, username, password, confPassword;
    Button register;
    CheckBox toc;
    Date DOB;
    SimpleDateFormat sdf;
    Picker picker = new Picker();
    //RestService restService;
    //private int _User_Id = 0;

    Button contact;

    //public Context context;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    //static LocalStorage localstorage = new LocalStorage();

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

        //context = getApplication().getBaseContext();

//        Call<User>

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

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sys_toolbar)));

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
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    String[] recipients={"buddy_customer@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    //intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                    //intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                    //intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
        }
        });

//        _User_Id = 0;
//        Intent intent = getIntent();
//        _User_Id = intent.getIntExtra("user_Id", 0);
//        if (_User_Id > 0) {
//            restService.getService().getUserById(_User_Id, new Callback<User>() {
//                @Override
//                public void success(User user, Response response) {
//                    name.setText(user.name);
//                    surname.setText(user.surname);
//                    dob.setText(user.dob);
//                    email.setText(user.email);
//                    username.setText(user.username);
//                    password.setText(user.password);
//
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Toast.makeText(Register.this, "Error message", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }


//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = username.getText().toString();
//                String pass = password.getText().toString();
//                String conpass = confirm.getText().toString();
//
//                if (name.equals("") || password.equals("") || conpass.equals("")) {
//                    Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (pass.equals(conpass)) {
//                        Boolean checkuser = dbHelper.CheckUsername(name);
//                        if (checkuser == true) {
//                            Boolean insert = dbHelper.Insert(name, pass);
//                            if (insert == true) {
//                                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
//                                username.setText("");
//                                password.setText("");
//                                confirm.setText("");
//                                regConfirm(v);
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
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
//        if ((TextUtils.isEmpty(name.getText().toString())) || (TextUtils.isEmpty(surname.getText().toString())) || (TextUtils.isEmpty(dob.getText().toString())) || (TextUtils.isEmpty(email.getText().toString())) || (TextUtils.isEmpty(username.getText().toString())) || (TextUtils.isEmpty(password.getText().toString())) || (TextUtils.isEmpty(confPassword.getText().toString()))) {
//            Toast.makeText(Register.this, "Please make sure you have filled  the necessary credentials.", Toast.LENGTH_SHORT).show();
//        }
////        else if(new Date().after(DOB)){
////            Toast.makeText(Register.this, "Please enter a valid Date of Birth.", Toast.LENGTH_SHORT).show();
////        }
//        else if (!isEmailValid(email.getText().toString())) {
//            Toast.makeText(Register.this, "The email you provided is invalid.", Toast.LENGTH_SHORT).show();
//        }
////        else if(){
////            //unique email + username
////        }
//        else if (password.getText().toString().length() < 8) {
//            Toast.makeText(Register.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
//        } else if (!password.getText().toString().equals(confPassword.getText().toString())) {
//            Toast.makeText(Register.this, "The passwords do no match.", Toast.LENGTH_SHORT).show();
//        }
////        else if(toc.isChecked()){
////            Toast.makeText(Register.this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
////        }
//        else {
//            //add the user to the database
//
//
////            User user = new User();
////            Integer status = 0;
////            user.name = name.getText().toString();
////            user.surname = surname.getText().toString();
////            user.dob = dob.getText().toString();
////            user.email = email.getText().toString();
////            user.username = username.getText().toString();
////            user.password = password.getText().toString();
////            user.Id = _User_Id;
////
////            if(_User_Id == 0){
////                restService.getService().addUser(user, new Callback<User>(){
////                    @Override
////                    public void success(User user, Response response) {
////                        Toast.makeText(Register.this, "Your account has been registered successfully.", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(Register.this, Login.class);
////                        startActivity(intent);
////                    }
////                    @Override
////                    public void failure(RetrofitError error) {
////                        Toast.makeText(Register.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
////                    }
////                });
////            } else{
////                Toast.makeText(Register.this, "god knows", Toast.LENGTH_SHORT).show();
////            }
//        }
    }

    private void registerUser() {

        sName = name.getText().toString();
        sSurname = surname.getText().toString();
        sDob = dob.getText().toString();
        sEmail = email.getText().toString();
        sUsername = username.getText().toString();
        sPassword = password.getText().toString();

        if(!checkUserValid(sUsername)){
            Toast.makeText(Register.this, "This username has already been registered!", Toast.LENGTH_SHORT).show();
        }else if(!checkEmailValid(sEmail)) { //email already registered
            Toast.makeText(Register.this, "This email has already been registered!", Toast.LENGTH_SHORT).show();
        } else if(!sPassword.equals(confPassword.getText().toString())) { //pass do not match
            Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        } else {

            User user = new User(LocalStorage.usersList.size()+1, sUsername, 12345678, sEmail, sPassword, 0, sName, sSurname, sDob);

            LocalStorage.usersList.add(user);
            saveFile("user_file.json", LocalStorage.getUserJson());

            Call<User> call = jsonPlaceHolderApi.registerUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(Register.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
//                User userResponse = response.body();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        }
    }

    private String subFolder = "/userdata";
    //user_file, event_file

    public void saveFile(String file, String JsonObj) {
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

            FileWriter filenew = new FileWriter(appDirectory + "/" + file);
            filenew.write(JsonObj);
            filenew.flush();
            filenew.close();

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
    }

    public static boolean checkEmailValid(String email) {
        //this was not our part to do. back end function
        int i = 0;
        while(i>-1 && i<LocalStorage.usersList.size()) {
            if(LocalStorage.usersList.get(i).getEmail().equals(email)) {
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
        while(i>-1 && i<LocalStorage.usersList.size()) {
            if(LocalStorage.usersList.get(i).getUsername().equals(user)) {
                return false;
                //match is found. Is valid? no.
            }
            i++;
        }
        return true; /// no match found. is valid? yes
    }

}