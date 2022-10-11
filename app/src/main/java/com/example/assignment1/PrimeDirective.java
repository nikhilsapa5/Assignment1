package com.example.assignment1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PrimeDirective extends AppCompatActivity {

    private static final String TAG = "PrimeTimeActivity";

    private TextView tVStartThread;

    private TextView tVPrimeThread;

    private Handler mainHandler = new Handler();

    private int i = 0;

    private int primeNumber = 0;

    private volatile boolean stopThread = false;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_directive);

        tVStartThread = findViewById(R.id.textView);
        tVPrimeThread = findViewById(R.id.textView4);

        if(savedInstanceState != null) {
            i = savedInstanceState.getInt("allNumbers");
            primeNumber = savedInstanceState.getInt("prime");
            tVStartThread.setText("Current number being checked- " + i);
            tVPrimeThread.setText("Latest Prime number- " + primeNumber);
        }



    }

    public void startThread(View view) {
        stopThread = false;
        ExampleThread thread = new ExampleThread(100000);
        thread.start();
    }

    public void stopThread(View view) {
        stopThread = true;
        tVPrimeThread.setText("Latest Prime number- " + primeNumber);

    }

    public boolean isPrime(int n){
        //since 0 and 1 is not prime return false.
        if(n==1||n==0)return false;

        //Run a loop from 2 to n-1
        for(int i=2; i<n; i++){
            // if the number is divisible by i, then n is not a prime number.
            if(n%i==0)return false;
        }
        //otherwise, n is prime number.
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("allNumbers", i);
        outState.putInt("prime",primeNumber);
    }

    class ExampleThread extends Thread {

        int seconds;
//        int primeNumber = 0;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(i=3; i<seconds; i+=2) {
                if(stopThread) {
                    return;
                }
//                tVStartThread.setText("Current number being checked- " + i);
//                if(i == 5) {
//                if(isPrime(i)) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                            tVPrimeThread.setText("Latest Prime number- " + i);
                        if(i%11==0) {
                            tVStartThread.setText("Current number being checked- " + i);
                        }

                        if(isPrime(i)) {
                            primeNumber = i;
                        }


                    }
                });
//                }
//                }
                Log.d(TAG, "startThread: "+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}