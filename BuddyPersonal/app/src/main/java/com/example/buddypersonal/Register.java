package com.example.buddypersonal;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    TextView dob;
    EditText name, surname, email, username, password, confPassword;
    CheckBox toc;
    Date DOB, CURR;
    SimpleDateFormat sdf;
    Pickers picker = new Pickers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.reg_et_name);
        surname = (EditText)findViewById(R.id.reg_et_surname);
        dob = (TextView)findViewById(R.id.reg_et_dob);
        email = (EditText)findViewById(R.id.reg_et_email);
        username = (EditText)findViewById(R.id.reg_et_username);
        password = (EditText)findViewById(R.id.reg_et_password);
        confPassword = (EditText)findViewById(R.id.reg_et_conf_password);
        toc = (CheckBox)findViewById(R.id.reg_cb_tac);

        //DATE
        dob = (TextView) findViewById(R.id.reg_et_dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(dob);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });



//        dbHelper = new DatabaseHelper(this);
//        username = (EditText) findViewById(R.id.reg_et_username);
//        password = (EditText) findViewById(R.id.reg_et_password);
//        confirm = (EditText) findViewById(R.id.reg_et_conf_password);
//        register = findViewById(R.id.reg_bt_register);

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
    }

    
//    public void regConfirm(View view){
//        Intent intent = new Intent(this, Register.class);
//        //EditText PersonName = (EditText) findViewById(R.id.PersonName);
//        //EditText PersonSurname = (EditText) findViewById(R.id.PersonSurname);
//        //EditText PersonDOB = (EditText) findViewById(R.id.PersonDOB);
//        //EditText PersonEmail = (EditText) findViewById(R.id.PersonEmail);
//
//        register = findViewById(R.id.reg_bt_register);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
//            }
//        });
//        startActivity(intent);
//    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        picker.getDate().setText(currentDate);
//                .setsDate(currentDate);
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

    public void register(View view){
        if ((TextUtils.isEmpty(name.getText().toString()))||(TextUtils.isEmpty(surname.getText().toString()))||(TextUtils.isEmpty(dob.getText().toString()))||(TextUtils.isEmpty(email.getText().toString()))||(TextUtils.isEmpty(username.getText().toString()))||(TextUtils.isEmpty(password.getText().toString()))||(TextUtils.isEmpty(confPassword.getText().toString()))) {
            Toast.makeText(Register.this, "Please make sure you have filled  the necessary credentials.", Toast.LENGTH_SHORT).show();
        }
//        else if(){
//            //dob should not be in the future
//        }
        else if (!isEmailValid(email.getText().toString())){
            Toast.makeText(Register.this, "The email you provided is invalid.", Toast.LENGTH_SHORT).show();
        }
//        else if(){
//            //unique username
//        }
        else if(password.getText().toString().length()<8){
            Toast.makeText(Register.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
        }
        else if(!password.getText().toString().equals(confPassword.getText().toString())){
            Toast.makeText(Register.this, "The passwords do no match.", Toast.LENGTH_SHORT).show();
        }
//        else if(){
//        when checkbox is left unmarked
//            Toast.makeText(Register.this, "Please accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
//        }
        else{
            Toast.makeText(Register.this, "Your account has been registered successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }
}