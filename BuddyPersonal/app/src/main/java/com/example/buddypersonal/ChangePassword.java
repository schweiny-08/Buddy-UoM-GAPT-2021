package com.example.buddypersonal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    Button changePassword;
    EditText currPass, newPass, confNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currPass = (EditText) findViewById(R.id.cng_psw_et_curr_pass);
        newPass = (EditText)findViewById(R.id.cng_psw_et_new_pass);
        confNewPass = (EditText)findViewById(R.id.cng_psw_et_conf_new_pass);
        changePassword = (Button)findViewById(R.id.cng_psw_bt_cng_psw);
    }

    public void changePassword(View view){
        if ((TextUtils.isEmpty(currPass.getText().toString()))||(TextUtils.isEmpty(newPass.getText().toString()))||(TextUtils.isEmpty(confNewPass.getText().toString()))) {
            Toast.makeText(ChangePassword.this, "Please make sure you have filled the necessary credentials.", Toast.LENGTH_SHORT).show();
        }
//        else if(){
//            //current password matches the one in the database
//        }
        else if(newPass.getText().toString().length()<8){
            Toast.makeText(ChangePassword.this, "The passwords is too short.", Toast.LENGTH_SHORT).show();
        }
        else if(!newPass.getText().toString().equals(confNewPass.getText().toString())){
            Toast.makeText(ChangePassword.this, "The passwords do no match.", Toast.LENGTH_SHORT).show();
        }
        else{
            //update in the database
            Toast.makeText(ChangePassword.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePassword.this, Account.class);
            startActivity(intent);
        }
    }
}