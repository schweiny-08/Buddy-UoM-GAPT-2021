package com.example.buddypersonal;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
//    Button register;
//    EditText username, password, confirm;
//    DatabaseHelper dbHelper;

    TextView startTime, endTime, startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        //TIME
//        startTime = (TextView) findViewById(R.id.reg_et_dob);
//        startTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(), "time picker");
//            }
//        });
//
//        endTime = (TextView) findViewById(R.id.evd_tv_end_time_det);
//        endTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(), "time e picker");
//            }
//        });

        //DATE
        startDate = (TextView) findViewById(R.id.reg_et_dob);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

//        endDate = (TextView) findViewById(R.id.evd_tv_end_date_det);
//        endDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment datePicker = new DatePickerFragment();
//                datePicker.show(getSupportFragmentManager(), "date e picker");
//            }
//        });

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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(java.util.Calendar.MINUTE, minute);

        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//        if(getTag().equalsIgnoreCase("time picker")) {
        startTime.setText(currentTime);
//        }
//        else{
//            endTime.setText(currentTime);
//        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, year);
        c.set(java.util.Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        startDate.setText(currentDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void register(View view){
        Intent intent = new Intent(this, Login.class);
        EditText name = (EditText)findViewById(R.id.reg_et_name);
        EditText surname = (EditText)findViewById(R.id.reg_et_surname);
        EditText dob = (EditText)findViewById(R.id.reg_et_dob);
        EditText email = (EditText)findViewById(R.id.reg_et_email);
        EditText username = (EditText)findViewById(R.id.reg_et_username);
        EditText password = (EditText)findViewById(R.id.reg_et_password);
        EditText confPassword = (EditText)findViewById(R.id.reg_et_conf_password);
        startActivity(intent);
    }
}