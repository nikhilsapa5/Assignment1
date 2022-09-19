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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutMeButton = new Button(this);
        information = new TextView(this);

        aboutMeButton = findViewById(R.id.button);
        information = findViewById(R.id.textView);

        aboutMeButton.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Name: Nikhil Sapa | Email: sapa.n@northeastern.edu", Toast.LENGTH_SHORT).show());
    }
}