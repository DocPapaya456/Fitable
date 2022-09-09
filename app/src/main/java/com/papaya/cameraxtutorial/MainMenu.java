package com.papaya.cameraxtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button squatBtn;
    Button lungesBtn;
    Button pushUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        squatBtn = findViewById(R.id.squatBtn);
        lungesBtn = findViewById(R.id.lungesBtn);
        pushUpBtn = findViewById(R.id.pushUpButton);
        Intent intent = new Intent(MainMenu.this, MainActivity.class);
        squatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("exercise", "squats");
                startActivity(intent);
            }
        });
        lungesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("exercise", "lunges");
                startActivity(intent);
            }
        });
        pushUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("exercise", "push-ups");
                startActivity(intent);
            }
        });
    }
}