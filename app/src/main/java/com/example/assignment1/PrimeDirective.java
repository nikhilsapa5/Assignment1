package com.example.assignment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PrimeDirective extends AppCompatActivity {
    private static final String TAG = "Prime Directive";
    private TextView startThread;
    private TextView primeThread;
    private Handler mainHandler = new Handler();
    private int iteratingVariable = 0;
    private int primeNumber = 0;
    private volatile boolean stopThread = false;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PrimeDirective.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_directive);
        startThread = findViewById(R.id.currentNumberTextView);
        primeThread = findViewById(R.id.latestPrimeTextView);

        if(savedInstanceState != null) {
            iteratingVariable = savedInstanceState.getInt("allNumbers");
            primeNumber = savedInstanceState.getInt("prime");
            startThread.setText("Current number being checked:- " + iteratingVariable);
            primeThread.setText("Latest Prime number:- " + primeNumber);
        }
    }

    public void startThread(View view) {
        stopThread = false;
        ExampleThread thread = new ExampleThread(100000);
        thread.start();
    }

    public void stopThread(View view) {
        stopThread = true;
        primeThread.setText("Latest Prime number- " + primeNumber);
    }

    public boolean checkIsPrime(int n){
        if(n == 1 || n == 0)return false;

        for(int i = 2; i < n; i++){
            if(n % i == 0)
                return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("allNumbers", iteratingVariable);
        outState.putInt("prime",primeNumber);
    }

    class ExampleThread extends Thread {
        int seconds;
        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(iteratingVariable = 3; iteratingVariable < seconds; iteratingVariable += 2) {
                if (stopThread) {
                    return;
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iteratingVariable % 13 == 0) {
                            startThread.setText("Current number being checked:- " + iteratingVariable);
                        }
                        if (checkIsPrime(iteratingVariable)) {
                            primeNumber = iteratingVariable;
                        }
                    }
                });
                Log.d(TAG, "startThread: "+ iteratingVariable);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}