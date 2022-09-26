package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {

    private Button aboutMeButton;
    private TextView information;
    private Button clickyclickyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutMeButton = new Button(this);
        information = new TextView(this);
        clickyclickyButton = new Button(this);

        aboutMeButton = findViewById(R.id.button);
        information = findViewById(R.id.textView);
        clickyclickyButton = findViewById(R.id.clickyButton);

        aboutMeButton.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Name: Nikhil Sapa | Email: sapa.n@northeastern.edu", Toast.LENGTH_SHORT).show());
        clickyclickyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}