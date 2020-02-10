package com.example.foodbeak.foodbreak.inc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.entities.Company;
import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.example.foodbeak.foodbreak.inc.repositories.LoginRepository;
import com.example.foodbeak.foodbreak.inc.types.MyViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends AndroidViewModel implements MyViewModel<LoginRepository> {
    private static final String TAG = "LoginVM";

    private LoginRepository mLoginRepo;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mLoginRepo = LoginRepository.getInstance();
    }

    public LiveData<String> getUsername() {
        return mLoginRepo.getUsername();
    }

    public LiveData<Consumer> getAuthConsumer() {
        return mLoginRepo.getAuthConsumer();
    }

    public LiveData<Company> getAuthCompany() {
        return mLoginRepo.getAuthCompany();
    }

    public void sendLogin(String email, String password) {
        mLoginRepo.updateIsUpdating(true);
        mLoginRepo
                .signInWithEmailPassword(email, password)
                .addOnCompleteListener(task -> mLoginRepo.updateIsUpdating(false))
                .addOnFailureListener(fail -> mLoginRepo.addError(fail.getMessage()))
                .addOnSuccessListener(signIn -> {
                    mLoginRepo.updateIsUpdating(true);

                    FirebaseFirestore.getInstance().collection("users")
                            .document(signIn.getUser().getUid())
                            .get()
                            .addOnFailureListener(userFail -> mLoginRepo.addError(userFail.getMessage()))
                            .addOnCompleteListener(task -> mLoginRepo.updateIsUpdating(false))
                            .addOnSuccessListener(user -> {
                                if (user.contains("company")) {
                                    mLoginRepo.updateAuthCompany(user.toObject(Company.class));
                                } else {
                                    mLoginRepo.updateAuthConsumer(user.toObject(Consumer.class));
                                }

                                if (!mLoginRepo.getIsComplete().getValue()) {
                                    mLoginRepo.updateIsComplete(true);
                                }
                            });
                });
    }

    @Override
    public LoginRepository getRepo() {
        return mLoginRepo;
    }

    @Override
    public void init() {
        initDefaults();
        mLoginRepo.initUsername();
    }
}
