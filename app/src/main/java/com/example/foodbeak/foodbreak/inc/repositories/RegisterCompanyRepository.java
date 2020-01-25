package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterCompanyRepository {
    private static final String TAG = "RegisterCompanyRepo";

    private static volatile RegisterCompanyRepository REGISTER_COMPANY_REPO_INSTANCE;

    private MutableLiveData<Company> mCompanyRegistration;
    private MutableLiveData<Boolean> mIsUpdating;
    private MutableLiveData<Boolean> mIsComplete;
    private MutableLiveData<ArrayList<String>> mErrors;

    private RegisterCompanyRepository() {
        initCompanyRegistration();
    }

    public static RegisterCompanyRepository getInstance() {
        if (REGISTER_COMPANY_REPO_INSTANCE == null) {
            synchronized (RegisterCompanyRepository.class) {
                if (REGISTER_COMPANY_REPO_INSTANCE == null) {
                    REGISTER_COMPANY_REPO_INSTANCE = new RegisterCompanyRepository();
                }
            }
        }

        return REGISTER_COMPANY_REPO_INSTANCE;
    }

    public void initCompanyRegistration() {
        if (mCompanyRegistration != null) {
            return;
        }

        mCompanyRegistration = new MutableLiveData<>();
        mCompanyRegistration.setValue(Company.defCompany());
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
        Log.d(TAG, "signUpWithEmailPassword: email: " + mCompanyRegistration.getValue().getEmail());
        Log.d(TAG, "signUpWithEmailPassword: pass: " + password);
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                mCompanyRegistration.getValue().getEmail(),
                password
        );
    }

    public MutableLiveData<Company> getCompanyRegistration() {
        return mCompanyRegistration;
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

    public void updateCompanyRegistration(Company company) {
        mCompanyRegistration.postValue(company);
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
