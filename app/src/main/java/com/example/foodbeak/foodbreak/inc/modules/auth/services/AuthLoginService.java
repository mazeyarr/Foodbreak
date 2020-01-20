package com.example.foodbeak.foodbreak.inc.modules.auth.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthLoginService {
    private static final String TAG = "AuthLoginService";

    private static AuthLoginService AUTH_LOGIN_SERVICE_INSTANCE;
    private FirebaseAuth mFirebaseAuth;

    public AuthLoginService() {
        this.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> authenticateWith(String email, String password) {
        return mFirebaseAuth.signInWithEmailAndPassword(
                email,
                password
        );
    }

    public static AuthLoginService getInstance() {
        if (AUTH_LOGIN_SERVICE_INSTANCE == null) {
            synchronized (AuthLoginService.class) {
                if (AUTH_LOGIN_SERVICE_INSTANCE == null) {
                    AUTH_LOGIN_SERVICE_INSTANCE = new AuthLoginService();
                }
            }
        }

        return AUTH_LOGIN_SERVICE_INSTANCE;
    }
}
