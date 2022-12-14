package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button aboutMeButton;
    private Button linkCollectorButton;
    private Button primeDirectiveButton;
    private TextView information;
    private Button clickyclickyButton;
    private Button locationButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutMeButton = new Button(this);
        linkCollectorButton = new Button(this);
        information = new TextView(this);
        clickyclickyButton = new Button(this);
        primeDirectiveButton = new Button(this);
        locationButton = new Button(this);

        aboutMeButton = findViewById(R.id.findPrimesButton);
        linkCollectorButton = findViewById(R.id.linkCollector);
        primeDirectiveButton = findViewById(R.id.primeDirective);
        information = findViewById(R.id.currentNumberTextView);
        clickyclickyButton = findViewById(R.id.clickyButton);
        locationButton = findViewById(R.id.locationButton);

        clickyclickyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClickyActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        linkCollectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LinkCollector.class);
                view.getContext().startActivity(intent);
            }
        });

        primeDirectiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PrimeDirective.class);
                view.getContext().startActivity(intent);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Locator.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void displayAboutMe(View view) {
        Intent intent1 = new Intent(this, AboutMe.class);
        startActivity(intent1);
    }
}