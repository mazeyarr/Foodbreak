package com.example.foodbeak.foodbreak.inc.modules.shared.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.database.DataViewModel;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.ModulesNotInitializedException;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
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
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MainState();

        try {
            setContentView(MainState
                    .getModule(ModuleType.SHARED, SharedModule.class)
                    .getLayout(SharedActivities.SPLASH)
            );
            startSplashScreen();
        } catch (ModulesNotInitializedException e) {
            // TODO: do something if modules have not been initialized
        } catch (UndefinedActivityException e) {
            // TODO: do something if wrong activity is called
        }
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
        try {
            Log.d(TAG, "startLoginActivity: " + MainState.getModule(ModuleType.AUTH, AuthModule.class));

            startActivity(MainState
                    .getModule(ModuleType.AUTH, AuthModule.class)
                    .getActivity(AuthActivityTypes.REGISTER, this)
            );
        } catch (ModulesNotInitializedException e) {
            // TODO: do something if modules have not been initialized
        } catch (UndefinedActivityException e) {
            // TODO: do something if wrong activity is called
        }
    }
}
