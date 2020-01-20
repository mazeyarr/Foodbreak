package com.example.foodbeak.foodbreak.inc.modules.shared.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.modules.shared.SharedModule;
import com.example.foodbeak.foodbreak.inc.modules.shared.types.SharedActivities;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private static final int SPLASH_TIME_MILLIS = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MainState();
        setContentView(MainState
                .getModule(ModuleType.SHARED, SharedModule.class)
                .getLayout(SharedActivities.SPLASH)
        );

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
        Log.d(TAG, "startLoginActivity: " + MainState.getModule(ModuleType.AUTH, AuthModule.class));

        startActivity(MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getActivity(AuthActivityTypes.LOGIN, this)
        );
    }
}
