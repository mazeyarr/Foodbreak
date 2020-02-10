package com.example.foodbeak.foodbreak.inc.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainApp;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.activities.product.ProductCompanyActivity;
import com.example.foodbeak.foodbreak.inc.activities.product.ProductConsumerActivity;
import com.example.foodbeak.foodbreak.inc.activities.register.RegisterConsumerActivity;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.viewmodels.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "LoginActivity";

    LoginViewModel mLoginViewModel;

    ConstraintLayout cslLogin;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText etUsername;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText etPassword;

    Button btnNoAccount;
    Button btnLogin;

    ProgressBar spnLoginLoader;

    Validator validator;
    boolean validated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        initUIFields();
        initUIData();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.cslLogin = findViewById(R.id.cslLogin);

        this.etUsername = findViewById(R.id.etUsername);
        this.etPassword = findViewById(R.id.etPassword);

        this.btnNoAccount = findViewById(R.id.btnNoAccount);
        this.btnLogin = findViewById(R.id.btnLogin);

        this.spnLoginLoader = findViewById(R.id.spnLoginLoader);
    }

    @Override
    public void initUIData() {
        mLoginViewModel.init();

        this.etUsername.setText(mLoginViewModel.getUsername().getValue());
    }

    @Override
    public void initListeners() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        mLoginViewModel.getErrors().observe(this, e -> {
            if (e.size() > 0) Snackbar.make(cslLogin, e.get(e.size() - 1), Snackbar.LENGTH_LONG).show();
        });

        mLoginViewModel.isUpdating().observe(this, this::setUILoading);

        mLoginViewModel.isComplete().observe(this, isComplete -> {
            Log.d(TAG, "initListeners: Going to products page");

            if (isComplete) {
                Consumer consumer = mLoginViewModel.getAuthConsumer().getValue();
                Company company = mLoginViewModel.getAuthCompany().getValue();

                uiCleanup();

                if (consumer != null) {
                    goToProductConsumerShow();
                } else if (company != null) {
                    goToProductCompanyShow();
                }
            }
        });

        this.btnLogin.setOnClickListener(this::btnLoginOnClick);
        this.btnNoAccount.setOnClickListener(this::btnNoAccountOnClick);
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
        validator.validate();

        validator.setViewValidatedAction(action -> {
            if (this.validated) {
                mLoginViewModel.sendLogin(
                        this.etUsername.getText().toString(),
                        this.etPassword.getText().toString()
                );
            }
        });
    }

    public void btnNoAccountOnClick(View v) {
        Log.d(TAG, "btnNoAccountOnClick: Going to register page");

        uiCleanup();

        Router.getInstance().goTo(RegisterConsumerActivity.getRoute(this));
    }

    public static Route getRoute(Context context) {
        return new Route(
                "Login",
                context,
                LoginActivity.class,
                R.layout.activity_login
        );
    }

    public void setUILoading(boolean toggle) {
        showLoader(toggle);
        setButtonsHide(toggle);
        setFormDisable(toggle);
    }

    public void showLoader(boolean toggle) {
        if (toggle) {
            spnLoginLoader.setVisibility(View.VISIBLE);
        } else {
            spnLoginLoader.setVisibility(View.INVISIBLE);
        }
    }

    public void setButtonsHide(boolean toggle) {
        if (toggle) {
            btnLogin.animate().alpha(0).setDuration(MainApp.FADE_OUT_DURATION).start();
            btnNoAccount.animate().alpha(0).setDuration(MainApp.FADE_OUT_DURATION).start();
        } else {
            btnLogin.animate().alpha(1f).setDuration(MainApp.FADE_IN_DURATION).start();
            btnNoAccount.animate().alpha(1f).setDuration(MainApp.FADE_IN_DURATION).start();
        }
    }

    public void setFormDisable(boolean toggle) {
        this.etUsername.setEnabled(!toggle);
        this.etPassword.setEnabled(!toggle);

        this.btnLogin.setEnabled(!toggle);
        this.btnLogin.setEnabled(!toggle);
    }

    private void goToProductConsumerShow() {
        Router.getInstance().goTo(ProductConsumerActivity.getRoute(this));
        finish();
    }

    private void goToProductCompanyShow() {
        Router.getInstance().goTo(ProductCompanyActivity.getRoute(this));
        finish();
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
