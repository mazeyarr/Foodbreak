package com.example.foodbeak.foodbreak.inc.modules.auth.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.ModulesNotInitializedException;
import com.example.foodbeak.foodbreak.inc.modules.shared.exceptions.UndefinedActivityException;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.EventListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    TextInputEditText fullname;
    TextInputEditText email;
    TextInputEditText datebirth;
    TextInputEditText password;
    TextInputEditText passwordConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(MainState
                    .getModule(ModuleType.AUTH, AuthModule.class)
                    .getLayout(AuthActivityTypes.REGISTER)
            );

            initUIFields();
            initOnClickListeners();
        } catch (ModulesNotInitializedException e) {
            e.printStackTrace();
        }
    }

    protected void initUIFields() {
        this.fullname = findViewById(R.id.etFullname);
        this.email = findViewById(R.id.etEmail);
        this.datebirth = findViewById(R.id.etDateOfBirth);
        this.password = findViewById(R.id.etPassword);
        this.passwordConfirm = findViewById(R.id.etPasswordConfirm);
    }

    protected void initOnClickListeners() {
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this::btnRegisterOnClick);
    }



    protected void btnRegisterOnClick(View v) {
        Log.d(TAG, "btnRegisterOnClick: Clicked!!");
        Log.d(TAG, "btnRegisterOnClick: fullname = " + this.fullname.getText());
        Log.d(TAG, "btnRegisterOnClick: email = " + this.email.getText());
        
        //TODO:  make user and validate and stuff
        goToLogin();
    }

    protected void goToLogin() {
        try {
            Log.d(TAG, "goToLogin: Going to login page");

            startActivity(MainState
                    .getModule(ModuleType.AUTH, AuthModule.class)
                    .getActivity(AuthActivityTypes.LOGIN, this)
            );
        } catch (UndefinedActivityException e) {
            Log.e(TAG, "goToLogin: Login Actictivity is undefined!");
        } catch (ModulesNotInitializedException e) {
            Log.e(TAG, "goToLogin: Login ModuleNotInitialized!");
        }
    }
}
