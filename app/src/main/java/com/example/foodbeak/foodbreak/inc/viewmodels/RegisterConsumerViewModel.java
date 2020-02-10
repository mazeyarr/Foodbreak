package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.repositories.RegisterConsumerRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterConsumerViewModel extends AndroidViewModel implements MyViewModel<RegisterConsumerRepository> {
    private static final String TAG = "RegisterConsumerVM";

    private RegisterConsumerRepository mRegisterConsumerRepo;

    public RegisterConsumerViewModel(@NonNull Application application) {
        super(application);

        mRegisterConsumerRepo = RegisterConsumerRepository.getInstance();
    }

    public LiveData<Consumer> getConsumerRegistration() {
        return mRegisterConsumerRepo.getConsumerRegistration();
    }

    public void updateConsumerRegistration(Consumer Consumer) {
        mRegisterConsumerRepo.updateConsumerRegistration(Consumer);
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

    @Override
    public void init() {
        initDefaults();
        mRegisterConsumerRepo.initConsumerRegistration();
    }


    @Override
    public RegisterConsumerRepository getRepo() {
        return mRegisterConsumerRepo;
    }
}
