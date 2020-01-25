package com.example.foodbeak.foodbreak.inc.activities.register;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodbeak.foodbreak.inc.MainApp;
import com.example.foodbeak.foodbreak.inc.R;
import com.example.foodbeak.foodbreak.inc.Router;
import com.example.foodbeak.foodbreak.inc.activities.product.ProductCompanyActivity;
import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.types.Route;
import com.example.foodbeak.foodbreak.inc.viewmodels.RegisterCompanyViewModel;
import com.google.android.material.snackbar.Snackbar;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterCompanyActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "RegisterCompanyA";

    RegisterCompanyViewModel mRegisterCompanyViewModel;

    ConstraintLayout cslRegisterCompany;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText mName;

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

    Button btnRegister;
    Button btnGoToConsumerRegister;

    ProgressBar loader;
    ImageView iconCheck;

    Validator validator;
    boolean validated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRegisterCompanyViewModel = ViewModelProviders.of(this).get(RegisterCompanyViewModel.class);

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        initUIFields();
        initUIData();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.cslRegisterCompany = findViewById(R.id.cslRegisterCompany);

        this.mName = findViewById(R.id.etCompanyName);
        this.mEmail = findViewById(R.id.etEmail);
        this.mLocation = findViewById(R.id.etLocation);
        this.mPassword = findViewById(R.id.etPassword);
        this.mPasswordConfirm = findViewById(R.id.etPasswordConfirm);

        this.btnRegister = findViewById(R.id.btnRegister);
        this.btnGoToConsumerRegister = findViewById(R.id.btnCompanyRegister);

        this.loader = findViewById(R.id.spnRegisterLoader);
        this.iconCheck = findViewById(R.id.iconCheck);
    }

    @Override
    public void initUIData() {
        mRegisterCompanyViewModel.init();

        Company currentCompanyRegistration = mRegisterCompanyViewModel.getCompanyRegistration().getValue();

        assert currentCompanyRegistration != null; // because i did a init above

        this.mName.setText(currentCompanyRegistration.getName());
        this.mEmail.setText(currentCompanyRegistration.getEmail());
        this.mLocation.setText(currentCompanyRegistration.getLocation());
    }

    @Override
    public void initListeners() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        TextWatcher fieldUpdater = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                Company updateCompanyRegistration = mRegisterCompanyViewModel.getCompanyRegistration().getValue();

                assert updateCompanyRegistration != null; // because i did an init onCreate

                if (mName.getText().hashCode() == s.hashCode()) {
                    updateCompanyRegistration.setName(
                            Objects.requireNonNull(mName.getText()).toString()
                    );
                }

                if (mEmail.getText().hashCode() == s.hashCode()) {
                    updateCompanyRegistration.setEmail(
                            Objects.requireNonNull(mEmail.getText()).toString()
                    );
                }

                if (mLocation.getText().hashCode() == s.hashCode()) {
                    updateCompanyRegistration.setLocation(
                            Objects.requireNonNull(mLocation.getText()).toString()
                    );
                }

                mRegisterCompanyViewModel.updateCompanyRegistration(updateCompanyRegistration);
            }
        };

        mName.addTextChangedListener(fieldUpdater);
        mEmail.addTextChangedListener(fieldUpdater);
        mLocation.addTextChangedListener(fieldUpdater);

        btnRegister.setOnClickListener(this::btnRegisterOnClick);
        btnGoToConsumerRegister.setOnClickListener(this::goToConsumerRegister);

        mRegisterCompanyViewModel.isUpdating().observe(this, isUpdating -> {
            if (isUpdating) {
                this.loader.setVisibility(View.VISIBLE);
                setEnabledOnTextFields(false);
                setShowActionButtons(false);
            } else {
                this.loader.setVisibility(View.INVISIBLE);
                setEnabledOnTextFields(true);
                setShowActionButtons(true);
            }
        });
        mRegisterCompanyViewModel.getErrors().observe(this, errors -> {
            if (errors.size() > 0) {
                Snackbar.make(this.cslRegisterCompany, errors.get(0), Snackbar.LENGTH_LONG).show();
            }
        });
        mRegisterCompanyViewModel.isComplete().observe(this, isComplete -> {
            if (isComplete) {
                iconCheck.setVisibility(View.VISIBLE);
                setEnabledOnTextFields(false);
                setShowActionButtons(false);

                Timer timerGoToLogin = new Timer();
                timerGoToLogin.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        goToProductShow();
                    }
                }, 1000);

            } else {
                iconCheck.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void uiCleanup() {
        this.mName.setText("");
        this.mEmail.setText("");
        this.mLocation.setText("");
        this.mPassword.setText("");
        this.mPasswordConfirm.setText("");
    }

    private void btnRegisterOnClick(View v) {
        Log.d(TAG, "btnRegisterOnClick: Clicked!!");
        validator.validate();

        while (validator.isValidating()) {
            // TODO: spinner
            Log.d(TAG, "btnRegisterOnClick: Waiting for validation");
        }

        if (this.validated) {
            doRegister();
        }
    }

    private void doRegister() {
        mRegisterCompanyViewModel.sendCompanyRegistration(
                this.mPassword.getText().toString()
        );
    }

    private void goToConsumerRegister(View v) {
        Log.d(TAG, "goToCompanyRegister: Going to consumer register page");
        Router.getInstance().goTo(RegisterConsumerActivity.getRoute(this));
        finish();
    }

    private void goToProductShow() {
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

    public static Route getRoute(Context context) {
        return new Route(
                "Register Company",
                context,
                RegisterCompanyActivity.class,
                R.layout.activity_register_company
        );
    }

    public void setEnabledOnTextFields(boolean toggle) {
        this.mName.setEnabled(toggle);
        this.mEmail.setEnabled(toggle);
        this.mLocation.setEnabled(toggle);
        this.mPassword.setEnabled(toggle);
        this.mPasswordConfirm.setEnabled(toggle);
    }

    public void setShowActionButtons(boolean show) {
        if (show) {
            this.btnGoToConsumerRegister.animate()
                    .alpha(MainApp.ALPHA_VISIBLE)
                    .setDuration(MainApp.FADE_IN_DURATION)
                    .start();
            this.btnRegister.animate()
                    .alpha(MainApp.ALPHA_VISIBLE)
                    .setDuration(MainApp.FADE_IN_DURATION)
                    .start();
        } else {
            this.btnGoToConsumerRegister.animate()
                    .alpha(MainApp.ALPHA_INVISIBLE)
                    .setDuration(MainApp.FADE_OUT_DURATION)
                    .start();
            this.btnRegister.animate()
                    .alpha(MainApp.ALPHA_INVISIBLE)
                    .setDuration(MainApp.FADE_OUT_DURATION)
                    .start();
        }
    }
}
