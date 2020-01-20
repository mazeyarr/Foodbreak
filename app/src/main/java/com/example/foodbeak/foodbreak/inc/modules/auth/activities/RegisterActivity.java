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
import com.example.foodbeak.foodbreak.inc.modules.product.ProductModule;
import com.example.foodbeak.foodbreak.inc.modules.product.types.ProductActivitiesType;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
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

public class RegisterActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "RegisterActivity";

    AuthService sAuthService;
    AuthRegisterService sAuthRegisterService;
    DataViewModel mDataViewModel;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText mFullname;

    @NotEmpty
    @Email
    TextInputEditText mEmail;

    @NotEmpty
    TextInputEditText mDatebirth;

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
                .getLayout(AuthActivityTypes.REGISTER)
        );

        initUIFields();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.mFullname = findViewById(R.id.etFullname);
        this.mEmail = findViewById(R.id.etEmail);
        this.mDatebirth = findViewById(R.id.etDateOfBirth);
        this.mPassword = findViewById(R.id.etPassword);
        this.mPasswordConfirm = findViewById(R.id.etPasswordConfirm);
    }

    @Override
    public void initListeners() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this::btnRegisterOnClick);
    }

    @Override
    public void uiCleanup() {
        this.mFullname.setText("");
        this.mEmail.setText("");
        this.mDatebirth.setText("");
        this.mPassword.setText("");
        this.mPasswordConfirm.setText("");
    }


    protected void btnRegisterOnClick(View v) {
        Log.d(TAG, "btnRegisterOnClick: Clicked!!");
        Log.d(TAG, "btnRegisterOnClick: fullname = " + this.mFullname.getText());
        Log.d(TAG, "btnRegisterOnClick: email = " + this.mEmail.getText());

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
                                this.mFullname.getText().toString()
                        )
                        .build();

                //TODO: in service
                sAuthService
                        .updateCurrentProfile(profileUpdates)
                        .addOnCompleteListener(this, profileUpdateTask -> {
                            if (profileUpdateTask.isSuccessful()) {
                                Log.d(TAG, "registerAction: Profile is updated with correct displayName!");

                                try {
                                    mDataViewModel.createUser(
                                            new User(
                                                    sAuthService.getAuthUser().getUid(),
                                                    sAuthService.getAuthUser().getDisplayName(),
                                                    sAuthService.getAuthUser().getEmail(),
                                                    mDatebirth.getText().toString()
                                            )
                                    );

                                    gotToProducts(); // TODO: Skip login
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

    protected void gotToProducts() {
        Log.d(TAG, "gotToProducts: Going to login page");

        uiCleanup();

        Intent i = MainState
                .getModule(ModuleType.PRODUCT, ProductModule.class)
                .getActivity(ProductActivitiesType.PRODUCT_SHOW, this);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(i);
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
