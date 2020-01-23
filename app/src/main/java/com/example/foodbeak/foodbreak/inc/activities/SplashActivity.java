package com.example.foodbeak.foodbreak.inc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.MainApp;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.activities.auth.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private static final int SPLASH_TIME_MILLIS = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MainApp();

        setContentView(R.layout.activity_splash);

        startSplashScreen();
    }

    private void startSplashScreen() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startLoginActivity();
                finish();
            }
        }, SPLASH_TIME_MILLIS);
    }

    private void startLoginActivity() {
        Log.d(TAG, "startLoginActivity: starting login!");

        startActivity(new Intent(this, LoginActivity.class));
    }
}