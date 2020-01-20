package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import com.example.foodbeak.foodbreak.inc.modules.user.database.UserDao;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.example.foodbeak.foodbreak.inc.modules.user.services.UserService;
import com.google.firebase.firestore.DocumentReference;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class DataRepository {

    private UserDao mUserDao;

    DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        mUserDao = db.userDao();
    }


    DocumentReference createUser(User user) throws Exception {
        AtomicBoolean error = new AtomicBoolean(false);
        AtomicReference<DocumentReference> userRef = new AtomicReference<>();

        AtomicReference<Exception> exception = new AtomicReference<>();

        UserService
                .getInstance()
                .createUser(user)
                .addOnSuccessListener(userRef::getAndSet)
                .addOnFailureListener(e -> {
                    error.set(true);
                    exception.set(e);
                });

        if (error.get()) {
            throw exception.get();
        }

        return userRef.get();
    }
}
