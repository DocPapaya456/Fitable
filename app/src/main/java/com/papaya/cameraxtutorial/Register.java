package com.papaya.cameraxtutorial;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText newEmail;
    EditText newPassword;
    Button registerBtn;
    Button cancelBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialise buttons and textbox
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        registerBtn = findViewById(R.id.registerBtn);
        cancelBtn = findViewById(R.id.cancelBtn);



    }
}
