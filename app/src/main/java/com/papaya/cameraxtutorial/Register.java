package com.papaya.cameraxtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText newEmail;
    EditText newPassword;
    EditText checkPassword;
    Button registerBtn;
    Button cancelBtn;
    TextView errorText;
    String email, pwd;
    String checkPwd;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialise buttons and textbox
        newEmail = findViewById(R.id.newEmail);
        newPassword = findViewById(R.id.newPassword);
        checkPassword = findViewById(R.id.checkPassword);
        registerBtn = findViewById(R.id.registerBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        errorText = findViewById(R.id.errorText);
        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(newEmail.getText());
                pwd = String.valueOf(newPassword.getText());
                checkPwd = String.valueOf(checkPassword.getText());
                if (pwd.equals(checkPwd)) {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                transitionToMain();
                            }
                        }
                    });
                }
            }
        });



    }
    void transitionToMain() {
        Intent intent = new Intent(this, MainMenu.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }
}
