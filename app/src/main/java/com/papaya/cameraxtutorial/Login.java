package com.papaya.cameraxtutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.papaya.cameraxtutorial.R;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class Login extends AppCompatActivity {
    EditText emailTxtField;
    EditText pwdTxtField;
    TextView loginText;
    TextView registerBtn;
    String email, pwd;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    ActivityResultLauncher<Intent> mStartRegisterForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                finish();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedBundleInstance) {

        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Button loginBtn = findViewById(R.id.loginBtn);
        Button skipBtn = findViewById(R.id.skipBtn);
        registerBtn = findViewById(R.id.registerBtn);
        emailTxtField = findViewById(R.id.emailTxtField);
        pwdTxtField = findViewById(R.id.passTxtField);
        loginText = findViewById(R.id.loginText);
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            transitionToMain();
        }

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionToMain();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionToRegister();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = String.valueOf(emailTxtField.getText());
                pwd = String.valueOf(pwdTxtField.getText());
                loginText.setText("Loading...");
                if (email != null && pwd != null) {
                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("LOGIN", "signInComplete");
                                currentUser = mAuth.getCurrentUser();
                                transitionToMain();
                            } else {
                                Log.w("LOGIN", "signInFailed");
                                loginText.setText("Email or password incorrect. Please try again.");
                            }
                        }
                    });
                } else {
                    loginText.setText("Please enter your email and password");
                }
            }
        });



    }
    void transitionToMain() {
        Intent intent = new Intent(this, MainMenu.class);
        finish();
        startActivity(intent);
    }

    void transitionToRegister() {
        Intent intent = new Intent(this, Register.class);
        mStartRegisterForResult.launch(intent);
    }



}
