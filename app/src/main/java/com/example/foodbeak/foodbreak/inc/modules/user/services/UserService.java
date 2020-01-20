package com.example.foodbeak.foodbreak.inc.modules.user.services;

import com.example.foodbeak.foodbreak.inc.database.AppDatabase;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserService {
    private static final String TAG = "UserService";

    private FirebaseFirestore mFirestore;
    public static UserService USER_SERVICE_INSTANCE;

    public UserService() {
        mFirestore = AppDatabase.getFirestore();
    }

    public Task<DocumentReference> createUser(User userEntity) {
        return mFirestore.collection("users")
                .add(userEntity);
    }

    public static UserService getInstance() {
        if (USER_SERVICE_INSTANCE == null) {
            synchronized (UserService.class) {
                if (USER_SERVICE_INSTANCE == null) {
                    USER_SERVICE_INSTANCE = new UserService();
                }
            }
        }

        return USER_SERVICE_INSTANCE;
    }
}
