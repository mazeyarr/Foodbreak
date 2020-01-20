package com.example.foodbeak.foodbreak.inc.modules.auth.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRegisterService extends AuthService {
    private static final String TAG = "AuthRegisterService";

    private static AuthRegisterService AUTH_REGISTER_SERVICE_INSTANCE;
    private FirebaseAuth mFirebaseAuth;

    public AuthRegisterService() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> createUserWith(String email, String password) {
        return mFirebaseAuth.createUserWithEmailAndPassword(
                email,
                password
        );
    }

    public static AuthRegisterService getInstance() {
        if (AUTH_REGISTER_SERVICE_INSTANCE == null) {
            synchronized (AuthRegisterService.class) {
                if (AUTH_REGISTER_SERVICE_INSTANCE == null) {
                    AUTH_REGISTER_SERVICE_INSTANCE = new AuthRegisterService();
                }
            }
        }

        return AUTH_REGISTER_SERVICE_INSTANCE;
    }
}
