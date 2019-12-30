package com.example.foodbeak.foodbreak.inc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthAcitvities;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new MainState();

        super.onCreate(savedInstanceState);
        setContentView(MainState
                        .getModule(ModuleType.AUTH, AuthModule.class)
                        .getLayout(AuthAcitvities.LOGIN)
        );
    }


}
