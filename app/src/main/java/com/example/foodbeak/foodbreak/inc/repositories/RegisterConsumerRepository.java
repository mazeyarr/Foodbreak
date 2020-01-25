package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterConsumerRepository {
    private static final String TAG = "RegisterConsumerRepo";

    private static volatile RegisterConsumerRepository REGISTER_CONSUMER_REPO_INSTANCE;

    private MutableLiveData<Consumer> mConsumerRegistration;
    private MutableLiveData<Boolean> mIsUpdating;
    private MutableLiveData<Boolean> mIsComplete;
    private MutableLiveData<ArrayList<String>> mErrors;

    private RegisterConsumerRepository() {
        initConsumerRegistration();
    }

    public static RegisterConsumerRepository getInstance() {
        if (REGISTER_CONSUMER_REPO_INSTANCE == null) {
            synchronized (RegisterConsumerRepository.class) {
                if (REGISTER_CONSUMER_REPO_INSTANCE == null) {
                    REGISTER_CONSUMER_REPO_INSTANCE = new RegisterConsumerRepository();
                }
            }
        }

        return REGISTER_CONSUMER_REPO_INSTANCE;
    }

    public void initConsumerRegistration() {
        if (mConsumerRegistration != null) {
            return;
        }

        mConsumerRegistration = new MutableLiveData<>();
        mConsumerRegistration.setValue(Consumer.defConsumer());
    }

    public void initIsUpdating() {
        if (mIsUpdating != null) {
            return;
        }

        mIsUpdating = new MutableLiveData<>();
        mIsUpdating.setValue(false);
    }

    public void initIsComplete() {
        mIsComplete = new MutableLiveData<>();
        mIsComplete.setValue(false);
    }

    public void initErrors() {
        mErrors = new MutableLiveData<>();
        mErrors.setValue(new ArrayList<>());
    }

    public Task<AuthResult> signUpWithEmailPassword(String password) {
        Log.d(TAG, "signUpWithEmailPassword: email: " + mConsumerRegistration.getValue().getEmail());
        Log.d(TAG, "signUpWithEmailPassword: pass: " + password);
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                mConsumerRegistration.getValue().getEmail(),
                password
        );
    }

    public MutableLiveData<Consumer> getConsumerRegistration() {
        return mConsumerRegistration;
    }

    public MutableLiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }

    public MutableLiveData<Boolean> getIsComplete() {
        return mIsComplete;
    }

    public MutableLiveData<ArrayList<String>> getErrors() {
        return mErrors;
    }

    public void updateConsumerRegistration(Consumer company) {
        mConsumerRegistration.postValue(company);
    }

    public void updateIsUpdating(Boolean toggle) {
        mIsUpdating.postValue(toggle);
    }

    public void updateIsComplete(Boolean toggle) {
        mIsComplete.postValue(toggle);
    }

    public void updateErrors(ArrayList<String> errors) {
        mErrors.postValue(errors);
    }

    public void addError(String error) {
        ArrayList<String> errors = mErrors.getValue();

        errors.add(error);

        mErrors.postValue(errors);
    }
}
