package com.example.dif13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask splash = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        Timer t = new Timer();
        t.schedule(splash, 2000);
    }
}