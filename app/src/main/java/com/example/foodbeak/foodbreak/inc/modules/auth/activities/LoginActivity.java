package com.example.foodbeak.foodbreak.inc.modules.auth.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.database.DataViewModel;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.ModulesNotInitializedException;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getLayout(AuthActivityTypes.LOGIN)
            );
        } catch (ModulesNotInitializedException e) {
            // TODO: do something if modules have not been initialized
            e.printStackTrace();
        }
    }
}
