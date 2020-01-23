package com.example.foodbeak.foodbreak.inc.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "LoginActivity";

    TextInputEditText etUsername;
    TextInputEditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.etUsername = findViewById(R.id.etUsername);
        this.etPassword = findViewById(R.id.etPassword);
    }

    @Override
    public void initListeners() {
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::btnLoginOnClick);

        Button btnNoAccount = findViewById(R.id.btnNoAccount);
        btnNoAccount.setOnClickListener(this::btnNoAccountOnClick);
    }

    @Override
    public void uiCleanup() {
        try {
            this.etUsername.setText("");
            this.etPassword.setText("");
        } catch (NullPointerException e) {
            Log.d(TAG, "uiCleanup: skipping cleanup because elements were null!");
        }
    }

    public void btnLoginOnClick(View v) {
    }

    public void btnNoAccountOnClick(View v) {
        Log.d(TAG, "btnNoAccountOnClick: Going to register page");

        uiCleanup();

        startActivity(new Intent(this, RegisterActivity.class));
    }
}
