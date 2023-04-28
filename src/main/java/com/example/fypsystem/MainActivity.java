package com.example.fypsystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Button mRButton, hAButton;

    // prepare UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRButton= findViewById(R.id.mRButton);
        hAButton= findViewById(R.id.hAButton);

        //magnetic
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(MainActivity.this, MagneticRadiationDetector.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Magnetic sensor is activated.", Toast.LENGTH_SHORT).show();
            }
        });

        //advice
        hAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(MainActivity.this, HelpfulAdvice.class);
                startActivity(intent);
            }
        });
    }
}