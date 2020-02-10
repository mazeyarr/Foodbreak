package com.example.foodbeak.foodbreak.inc.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository extends CoreRepository {
    private static final String TAG = "LoginRepo";

    private static volatile LoginRepository LOGIN_REPOSITORY_INSTANCE;

    private MutableLiveData<String> mUsername;

    public static LoginRepository getInstance() {
        if (LOGIN_REPOSITORY_INSTANCE == null) {
            synchronized (LoginRepository.class) {
                if (LOGIN_REPOSITORY_INSTANCE == null) {
                    LOGIN_REPOSITORY_INSTANCE = new LoginRepository();
                }
            }
        }
        return LOGIN_REPOSITORY_INSTANCE;
    }

    public Task<AuthResult> signInWithEmailPassword(String email, String password) {
        Log.d(TAG, "signUpWithEmailPassword: email: " + email);
        Log.d(TAG, "signUpWithEmailPassword: pass: " + password);

        return FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email,
                password
        );
    }

    public void initUsername() {
        if (mUsername != null) {
            return;
        }

        mUsername = new MutableLiveData<>();
        mUsername.setValue("");
    }

    public MutableLiveData<String> getUsername() {
        return mUsername;
    }
}
