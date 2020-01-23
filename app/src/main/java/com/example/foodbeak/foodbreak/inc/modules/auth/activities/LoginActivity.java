package com.example.foodbeak.foodbreak.inc.modules.auth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.database.DataViewModel;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthLoginService;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthService;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.modules.product.ProductModule;
import com.example.foodbeak.foodbreak.inc.modules.product.types.ProductActivitiesType;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements MyActivity {
    private static final String TAG = "LoginActivity";

    AuthService mAuthService;
    AuthLoginService mAuthLoginService;
    DataViewModel mDataViewModel;

    TextInputEditText etUsername;
    TextInputEditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthService = AuthService.getInstance();
        mAuthLoginService = AuthLoginService.getInstance();
        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);


        handleUserDestination();

        if (!mAuthService.isAuthUser()) {
            setContentView(MainState
                    .getModule(ModuleType.AUTH, AuthModule.class)
                    .getLayout(AuthActivityTypes.LOGIN)
            );

            initUIFields();
            initListeners();
        }
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
        mAuthLoginService.authenticateWith(
                this.etUsername.getText().toString(),
                this.etPassword.getText().toString()
        ).addOnCompleteListener(loginTask -> {
            if (loginTask.isSuccessful()) {
                Log.d(TAG, "btnLoginOnClick: Login was successfull!");

                handleUserDestination();
            } else {
                Log.d(TAG, "btnLoginOnClick: Login failed!");
                Log.e(TAG, "btnLoginOnClick: " + loginTask.getException().getMessage());
            }
        });
    }

    public void btnNoAccountOnClick(View v) {
        Log.d(TAG, "btnNoAccountOnClick: Going to register page");

        uiCleanup();

        startActivity(MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getActivity(AuthActivityTypes.REGISTER, this)
        );
    }

    public void handleUserDestination() {
        if (mAuthService.isAuthUser()) {
            mDataViewModel.setAuthUser().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (mDataViewModel.isAuthIsCompany()) {
                        goToProductsAdmin();
                        return;
                    }

                    goToProducts();
                }

                Log.e(TAG, "handleUserDestination: failed to get the auth user from data view model");
            });

        }

        Log.d(TAG, "handleUserDestination: Stayin at login, user is not authenticated yet!");
    }

    public void goToProducts() {
        Log.d(TAG, "goToProducts: Going to products page");

        uiCleanup();

        Intent i = MainState
                .getModule(ModuleType.PRODUCT, ProductModule.class)
                .getActivity(ProductActivitiesType.PRODUCT_SHOW, this);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(i);
        finish();
    }

    public void goToProductsAdmin() {
        Log.d(TAG, "goToProductsAdmin: Going to product admin page");

        uiCleanup();

        Intent i = MainState
                .getModule(ModuleType.PRODUCT, ProductModule.class)
                .getActivity(ProductActivitiesType.PRODUCT_SHOW_ADMIN, this);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(i);
        finish();
    }
}
