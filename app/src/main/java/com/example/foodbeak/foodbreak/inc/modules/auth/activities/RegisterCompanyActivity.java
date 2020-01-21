package com.example.foodbeak.foodbreak.inc.modules.auth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainState;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.database.DataViewModel;
import com.example.foodbeak.foodbreak.inc.modules.auth.AuthModule;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthRegisterService;
import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthService;
import com.example.foodbeak.foodbreak.inc.modules.auth.types.AuthActivityTypes;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;
import com.example.foodbeak.foodbreak.inc.types.ModuleType;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class RegisterCompanyActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "RegisterCompanyActivity";

    AuthService sAuthService;
    AuthRegisterService sAuthRegisterService;
    DataViewModel mDataViewModel;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText mCompanyName;

    @NotEmpty
    @Email
    TextInputEditText mEmail;

    @NotEmpty
    TextInputEditText mLocation;

    @Password
    @NotEmpty
    TextInputEditText mPassword;

    @ConfirmPassword
    TextInputEditText mPasswordConfirm;

    Validator validator;
    boolean validated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sAuthService = AuthService.getInstance();
        sAuthRegisterService = AuthRegisterService.getInstance();
        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        setContentView(MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getLayout(AuthActivityTypes.REGISTER_COMPANY)
        );

        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.mCompanyName = findViewById(R.id.etCompanyName);
        this.mEmail = findViewById(R.id.etEmail);
        this.mLocation = findViewById(R.id.etLocation);
        this.mPassword = findViewById(R.id.etPassword);
        this.mPasswordConfirm = findViewById(R.id.etPasswordConfirm);
    }

    @Override
    public void initListeners() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this::btnRegisterOnClick);

        Button btnGoToConsumerRegister = findViewById(R.id.btnCompanyRegister);
        btnGoToConsumerRegister.setOnClickListener(this::goToConsumerRegister);
    }

    @Override
    public void uiCleanup() {
        this.mCompanyName.setText("");
        this.mEmail.setText("");
        this.mLocation.setText("");
        this.mPassword.setText("");
        this.mPasswordConfirm.setText("");
    }


    protected void btnRegisterOnClick(View v) {
        Log.d(TAG, "btnRegisterOnClick: Clicked!!");
        validator.validate();

        while (validator.isValidating()) {
            // TODO: spinner
            Log.d(TAG, "btnRegisterOnClick: Waiting for validation");
        }

        if (this.validated) {
            registerAction();
        }
    }

    protected void registerAction() {
        sAuthRegisterService.createUserWith(
                this.mEmail.getText().toString(),
                this.mPassword.getText().toString()
        ).addOnCompleteListener(this, registerTask -> {
            if (registerTask.isSuccessful()) {
                Log.d(TAG, "registerAction: Successfull!");

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(
                                this.mCompanyName.getText().toString()
                        )
                        .build();

                //TODO: in service
                sAuthService
                        .updateCurrentProfile(profileUpdates)
                        .addOnCompleteListener(this, profileUpdateTask -> {
                            if (profileUpdateTask.isSuccessful()) {
                                Log.d(TAG, "registerAction: Profile is updated with correct displayName!");

                                try {
                                    CompanyUser user = new CompanyUser(
                                            sAuthService.getAuthUser().getUid(),
                                            sAuthService.getAuthUser().getDisplayName(),
                                            sAuthService.getAuthUser().getEmail(),
                                            mLocation.getText().toString(),
                                            true
                                    );

                                    mDataViewModel.createUser(user);
                                    mDataViewModel.setAuthUser(user);

                                    goToLogin();
                                } catch (Exception e) {
                                    Toast.makeText(
                                            this,
                                            e.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        });
            } else {
                Log.e(TAG, "registerAction: Failed...");
                Log.e(TAG, "registerAction: " + registerTask.getException().getMessage());

                Toast.makeText(this,
                        registerTask.getException().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    protected void goToLogin() {
        Log.d(TAG, "goToLogin: Going to login page");

        uiCleanup();

        Intent i = MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getActivity(AuthActivityTypes.LOGIN, this);

        startActivity(i);
    }

    protected void goToConsumerRegister(View v) {
        Log.d(TAG, "goToCompanyRegister: Going to consumer register page");

        startActivity(MainState
                .getModule(ModuleType.AUTH, AuthModule.class)
                .getActivity(AuthActivityTypes.REGISTER, this)
        );
    }

    @Override
    public void onValidationSucceeded() {
        this.validated = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String errorMessage = error.getCollatedErrorMessage(this);

            if (view instanceof TextInputEditText) {
                View textInputLayout = (View) view.getParent().getParent();

                if (textInputLayout instanceof TextInputLayout) {
                    TextInputLayout textInputLay = findViewById(textInputLayout.getId());

                    textInputLay.setErrorEnabled(true);
                    ((TextInputLayout) textInputLayout).setError(errorMessage);
                }

            } else {
                Log.e(TAG, "onValidationFailed: " + errorMessage);
            }
        }
    }
}
