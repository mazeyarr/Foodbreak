package com.example.foodbeak.foodbreak.inc.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.database.DataViewModel;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class RegisterAdminActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "RegisterAdminActivity";

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

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        setContentView(R.layout.activity_register_company);

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

    }

    protected void goToConsumerRegister(View v) {
        Log.d(TAG, "goToCompanyRegister: Going to consumer register page");

        startActivity(new Intent(this, RegisterActivity.class));
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
