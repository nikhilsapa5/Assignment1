package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonE;
    private Button buttonF;
    private TextView pressedStringInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        buttonA = new Button(this);
        buttonB = new Button(this);
        buttonC = new Button(this);
        buttonD = new Button(this);
        buttonE = new Button(this);
        buttonF = new Button(this);
        pressedStringInformation = new TextView(this);

        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);
        buttonE = findViewById(R.id.buttonE);
        buttonF = findViewById(R.id.buttonF);
        pressedStringInformation = findViewById(R.id.pressedString);

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("A");
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("B");
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("C");
            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("D");
            }
        });
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("E");
            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPressedText("F");
            }
        });
    }

    private void setPressedText(String buttonAlphabet) {
        pressedStringInformation.setText("Pressed:- ");
        StringBuilder text = new StringBuilder(pressedStringInformation.getText());
        text.append(buttonAlphabet);
        pressedStringInformation.setText(text.toString());
    }
}