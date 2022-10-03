package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button aboutMeButton;
    private Button linkCollectorButton;
    private TextView information;
    private Button clickyclickyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutMeButton = new Button(this);
        linkCollectorButton = new Button(this);
        information = new TextView(this);
        clickyclickyButton = new Button(this);

        aboutMeButton = findViewById(R.id.button);
        linkCollectorButton = findViewById(R.id.linkCollector);
        information = findViewById(R.id.textView);
        clickyclickyButton = findViewById(R.id.clickyButton);


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
    }

    public void displayAboutMe(View view) {
        Intent intent1 = new Intent(this, AboutMe.class);
        startActivity(intent1);
    }
}