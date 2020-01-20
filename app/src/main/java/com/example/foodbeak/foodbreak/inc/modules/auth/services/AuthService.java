package com.example.foodbeak.foodbreak.inc.modules.auth.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthService {
    private static AuthService AUTH_SERVICE_INSTANCE;

    private FirebaseAuth mFirebaseAuth;

    AuthService() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isAuthUser() {
        return mFirebaseAuth.getCurrentUser() != null;
    }

    public FirebaseUser getAuthUser() {
        return mFirebaseAuth.getCurrentUser();
    }

    public Task<Void> updateCurrentProfile(UserProfileChangeRequest changeRequest) {
        return mFirebaseAuth.getCurrentUser().updateProfile(changeRequest);
    }

    public static AuthService getInstance() {
        if (AUTH_SERVICE_INSTANCE == null) {
            synchronized (AuthService.class) {
                if (AUTH_SERVICE_INSTANCE == null) {
                    AUTH_SERVICE_INSTANCE = new AuthService();
                }
            }
        }

        return AUTH_SERVICE_INSTANCE;
    }
}
