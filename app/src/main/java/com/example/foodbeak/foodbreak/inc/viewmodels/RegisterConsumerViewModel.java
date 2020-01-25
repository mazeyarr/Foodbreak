package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.repositories.RegisterConsumerRepository;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterConsumerViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterConsumerVM";

    private RegisterConsumerRepository mRegisterConsumerRepo;

    public RegisterConsumerViewModel(@NonNull Application application) {
        super(application);

        mRegisterConsumerRepo = RegisterConsumerRepository.getInstance();
    }

    public LiveData<Boolean> isUpdating() {
        return mRegisterConsumerRepo.getIsUpdating();
    }

    public LiveData<Boolean> isComplete() {
        return mRegisterConsumerRepo.getIsComplete();
    }

    public LiveData<ArrayList<String>> getErrors() {
        return mRegisterConsumerRepo.getErrors();
    }

    public LiveData<Consumer> getConsumerRegistration() {
        return mRegisterConsumerRepo.getConsumerRegistration();
    }

    public void updateConsumerRegistration(Consumer Consumer) {
        mRegisterConsumerRepo.updateConsumerRegistration(Consumer);
    }

    public void updateErrors(ArrayList<String> errors) {
        mRegisterConsumerRepo.updateErrors(errors);
    }

    public void addError(String error) {
        mRegisterConsumerRepo.addError(error);
    }

    public void sendConsumerRegistration(String password) {
        assert getConsumerRegistration().getValue() != null;

        mRegisterConsumerRepo.updateIsUpdating(true);

        UserProfileChangeRequest changeProfileRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(getConsumerRegistration().getValue().getFullname())
                .build();

        try {
            mRegisterConsumerRepo.signUpWithEmailPassword(password)
                    .addOnFailureListener(fail -> {
                        mRegisterConsumerRepo.updateIsUpdating(false);
                        mRegisterConsumerRepo.updateIsComplete(false);
                        mRegisterConsumerRepo.addError(fail.getMessage());
                    })
                    .addOnSuccessListener(signUp -> signUp.getUser().updateProfile(changeProfileRequest))
                    .addOnSuccessListener(signUp -> FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(signUp.getUser().getUid())
                            .set(getConsumerRegistration().getValue())
                            .addOnCompleteListener(task -> mRegisterConsumerRepo.updateIsUpdating(false))
                            .addOnSuccessListener(saveUserTask -> mRegisterConsumerRepo.updateIsComplete(true))
                            .addOnFailureListener(fail -> mRegisterConsumerRepo.addError(fail.getMessage()))
                    );
        } catch (NullPointerException e) {
            mRegisterConsumerRepo.addError(e.getLocalizedMessage());
            mRegisterConsumerRepo.updateIsComplete(false);
        }
    }

    public void init() {
        mRegisterConsumerRepo.initConsumerRegistration();
        mRegisterConsumerRepo.initIsUpdating();
        mRegisterConsumerRepo.initIsComplete();
        mRegisterConsumerRepo.initErrors();
    }


}
