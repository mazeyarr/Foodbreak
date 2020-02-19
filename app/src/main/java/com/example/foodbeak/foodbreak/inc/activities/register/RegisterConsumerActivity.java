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
import com.example.foodbeak.foodbreak.inc.activities.product.ProductConsumerCompanySelectionActivity;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.entities.Route;
import com.example.foodbeak.foodbreak.inc.types.MyActivity;
import com.example.foodbeak.foodbreak.inc.viewmodels.RegisterConsumerViewModel;
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

public class RegisterConsumerActivity extends AppCompatActivity implements MyActivity, Validator.ValidationListener {
    private static final String TAG = "RegisterConsumerA";

    RegisterConsumerViewModel mRegisterConsumerViewModel;

    ConstraintLayout cslRegisterConsumer;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText mFirstname;

    @NotEmpty
    @Length(min = 3)
    TextInputEditText mLastname;

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

    Button btnRegister;
    Button btnGoToCompanyRegister;

    ProgressBar loader;
    ImageView iconCheck;

    Validator validator;
    boolean validated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRegisterConsumerViewModel = ViewModelProviders.of(this).get(RegisterConsumerViewModel.class);

        setContentView(Router.getInstance().getCurrentRoute().getLayout());

        initUIFields();
        initUIData();
        initListeners();
    }

    @Override
    public void initUIFields() {
        this.cslRegisterConsumer = findViewById(R.id.cslRegisterConsumer);

        this.mFirstname = findViewById(R.id.etFirstname);
        this.mLastname = findViewById(R.id.etLastname);
        this.mEmail = findViewById(R.id.etEmail);
        this.mDatebirth = findViewById(R.id.etDateOfBirth);
        this.mPassword = findViewById(R.id.etPassword);
        this.mPasswordConfirm = findViewById(R.id.etPasswordConfirm);

        this.btnRegister = findViewById(R.id.btnRegister);
        this.btnGoToCompanyRegister = findViewById(R.id.btnCompanyRegister);

        this.loader = findViewById(R.id.spnRegisterLoader);
        this.iconCheck = findViewById(R.id.iconCheck);
    }

    @Override
    public void initUIData() {
        mRegisterConsumerViewModel.init();

        Consumer currentConsumerRegistration = mRegisterConsumerViewModel.getConsumerRegistration().getValue();

        assert currentConsumerRegistration != null; // because i did a init above

        this.mFirstname.setText(currentConsumerRegistration.getFirstname());
        this.mLastname.setText(currentConsumerRegistration.getLastname());
        this.mEmail.setText(currentConsumerRegistration.getEmail());
        this.mDatebirth.setText(currentConsumerRegistration.getBirthday());
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
                Consumer updateConsumerRegistration = mRegisterConsumerViewModel.getConsumerRegistration().getValue();

                assert updateConsumerRegistration != null; // because i did an init onCreate

                if (mFirstname.getText().hashCode() == s.hashCode()) {
                    updateConsumerRegistration.setFirstname(
                            Objects.requireNonNull(mFirstname.getText()).toString()
                    );
                }

                if (mLastname.getText().hashCode() == s.hashCode()) {
                    updateConsumerRegistration.setLastname(
                            Objects.requireNonNull(mLastname.getText()).toString()
                    );
                }

                if (mEmail.getText().hashCode() == s.hashCode()) {
                    updateConsumerRegistration.setEmail(
                            Objects.requireNonNull(mEmail.getText()).toString()
                    );
                }

                if (mDatebirth.getText().hashCode() == s.hashCode()) {
                    updateConsumerRegistration.setBirthday(
                            Objects.requireNonNull(mDatebirth.getText()).toString()
                    );
                }

                mRegisterConsumerViewModel.updateConsumerRegistration(updateConsumerRegistration);
            }
        };

        mFirstname.addTextChangedListener(fieldUpdater);
        mLastname.addTextChangedListener(fieldUpdater);
        mEmail.addTextChangedListener(fieldUpdater);
        mDatebirth.addTextChangedListener(fieldUpdater);

        btnRegister.setOnClickListener(this::btnRegisterOnClick);
        btnGoToCompanyRegister.setOnClickListener(this::goToCompanyRegister);

        mRegisterConsumerViewModel.isUpdating().observe(this, isUpdating -> {
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
        mRegisterConsumerViewModel.getErrors().observe(this, errors -> {
            if (errors.size() > 0) {
                Snackbar.make(this.cslRegisterConsumer, errors.get(0), Snackbar.LENGTH_LONG).show();
            }
        });
        mRegisterConsumerViewModel.isComplete().observe(this, isComplete -> {
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
        this.mFirstname.setText("");
        this.mLastname.setText("");
        this.mEmail.setText("");
        this.mDatebirth.setText("");
        this.mPassword.setText("");
        this.mPasswordConfirm.setText("");
    }


    protected void btnRegisterOnClick(View v) {
        validator.validate();

        validator.setViewValidatedAction(action -> {
            if (this.validated) {
                doRegister();
            }
        });
    }

    protected void doRegister() {
        mRegisterConsumerViewModel.sendConsumerRegistration(
                this.mPassword.getText().toString()
        );
    }


    protected void goToCompanyRegister(View v) {
        Log.d(TAG, "goToCompanyRegister: Going to company register page");
        Router.getInstance().goTo(RegisterCompanyActivity.getRoute(this));
        finish();
    }

    private void goToProductShow() {
        Router.getInstance().goTo(ProductConsumerCompanySelectionActivity.getRoute(this));
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
                "Register",
                context,
                RegisterConsumerActivity.class,
                R.layout.activity_register_consumer
        );
    }

    public void setEnabledOnTextFields(boolean toggle) {
        this.mFirstname.setEnabled(toggle);
        this.mLastname.setEnabled(toggle);
        this.mEmail.setEnabled(toggle);
        this.mDatebirth.setEnabled(toggle);
        this.mPassword.setEnabled(toggle);
        this.mPasswordConfirm.setEnabled(toggle);
    }

    public void setShowActionButtons(boolean show) {
        if (show) {
            this.btnGoToCompanyRegister.animate()
                    .alpha(MainApp.ALPHA_VISIBLE)
                    .setDuration(MainApp.FADE_IN_DURATION)
                    .start();
            this.btnRegister.animate()
                    .alpha(MainApp.ALPHA_VISIBLE)
                    .setDuration(MainApp.FADE_IN_DURATION)
                    .start();
        } else {
            this.btnGoToCompanyRegister.animate()
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
