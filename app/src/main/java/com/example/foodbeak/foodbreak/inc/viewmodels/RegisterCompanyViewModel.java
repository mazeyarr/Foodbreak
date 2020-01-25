package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.repositories.RegisterCompanyRepository;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterCompanyViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterCompanyVM";

    private RegisterCompanyRepository mRegisterCompanyRepo;

    public RegisterCompanyViewModel(@NonNull Application application) {
        super(application);

        mRegisterCompanyRepo = RegisterCompanyRepository.getInstance();
    }

    public LiveData<Boolean> isUpdating() {
        return mRegisterCompanyRepo.getIsUpdating();
    }

    public LiveData<Boolean> isComplete() {
        return mRegisterCompanyRepo.getIsComplete();
    }

    public LiveData<ArrayList<String>> getErrors() {
        return mRegisterCompanyRepo.getErrors();
    }

    public LiveData<Company> getCompanyRegistration() {
        return mRegisterCompanyRepo.getCompanyRegistration();
    }

    public void updateCompanyRegistration(Company company) {
        mRegisterCompanyRepo.updateCompanyRegistration(company);
    }

    public void updateErrors(ArrayList<String> errors) {
        mRegisterCompanyRepo.updateErrors(errors);
    }

    public void addError(String error) {
        mRegisterCompanyRepo.addError(error);
    }

    public void sendCompanyRegistration(String password) {
        assert getCompanyRegistration().getValue() != null;

        mRegisterCompanyRepo.updateIsUpdating(true);

        UserProfileChangeRequest changeProfileRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(getCompanyRegistration().getValue().getName())
                .build();

        try {
            mRegisterCompanyRepo.signUpWithEmailPassword(password)
                    .addOnFailureListener(fail -> {
                        mRegisterCompanyRepo.updateIsUpdating(false);
                        mRegisterCompanyRepo.updateIsComplete(false);
                        mRegisterCompanyRepo.addError(fail.getMessage());
                    })
                    .addOnSuccessListener(signUp -> signUp.getUser().updateProfile(changeProfileRequest))
                    .addOnSuccessListener(signUp -> FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(signUp.getUser().getUid())
                            .set(getCompanyRegistration().getValue())
                            .addOnCompleteListener(task -> mRegisterCompanyRepo.updateIsUpdating(false))
                            .addOnSuccessListener(saveUserTask -> mRegisterCompanyRepo.updateIsComplete(true))
                            .addOnFailureListener(fail -> mRegisterCompanyRepo.addError(fail.getMessage()))
                    );
        } catch (NullPointerException e) {
            mRegisterCompanyRepo.addError(e.getLocalizedMessage());
            mRegisterCompanyRepo.updateIsComplete(false);
        }
    }

    public void init() {
        mRegisterCompanyRepo.initCompanyRegistration();
        mRegisterCompanyRepo.initIsUpdating();
        mRegisterCompanyRepo.initIsComplete();
        mRegisterCompanyRepo.initErrors();
    }


}
