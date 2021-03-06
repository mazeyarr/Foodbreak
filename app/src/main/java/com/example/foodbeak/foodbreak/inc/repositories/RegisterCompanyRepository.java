package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterCompanyRepository extends CoreRepository {
    private static final String TAG = "RegisterCompanyRepo";

    private static volatile RegisterCompanyRepository REGISTER_COMPANY_REPO_INSTANCE;

    private MutableLiveData<Company> mCompanyRegistration;

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

    public void updateCompanyRegistration(Company company) {
        mCompanyRegistration.postValue(company);
    }


}
