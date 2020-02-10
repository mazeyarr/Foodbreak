package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.repositories.RegisterCompanyRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterCompanyViewModel extends AndroidViewModel implements MyViewModel<RegisterCompanyRepository> {
    private static final String TAG = "RegisterCompanyVM";

    private RegisterCompanyRepository mRegisterCompanyRepo;

    public RegisterCompanyViewModel(@NonNull Application application) {
        super(application);

        mRegisterCompanyRepo = RegisterCompanyRepository.getInstance();
    }

    public LiveData<Company> getCompanyRegistration() {
        return mRegisterCompanyRepo.getCompanyRegistration();
    }

    public void updateCompanyRegistration(Company company) {
        mRegisterCompanyRepo.updateCompanyRegistration(company);
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

    @Override
    public void init() {
        initDefaults();
        mRegisterCompanyRepo.initCompanyRegistration();
    }


    @Override
    public RegisterCompanyRepository getRepo() {
        return mRegisterCompanyRepo;
    }

}
