package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodbeak.foodbreak.inc.entities.Consumer;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterConsumerRepository extends CoreRepository {
    private static final String TAG = "RegisterConsumerRepo";

    private static volatile RegisterConsumerRepository REGISTER_CONSUMER_REPO_INSTANCE;

    private MutableLiveData<Consumer> mConsumerRegistration;

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

    public void updateConsumerRegistration(Consumer company) {
        mConsumerRegistration.postValue(company);
    }
}
