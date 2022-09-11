package com.papaya.cameraxtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StartExerciseActivity extends AppCompatActivity {

    TextView workoutTitle, workoutDescription, workoutExercises, workoutRep;
    Button startBtn, cancelBtn;
    Workout workout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);
        Intent intent = getIntent();
        
    }
}