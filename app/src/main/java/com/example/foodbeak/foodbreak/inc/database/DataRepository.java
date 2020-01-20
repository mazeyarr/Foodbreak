package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.example.foodbeak.foodbreak.inc.modules.user.services.AccountService;
import com.google.firebase.firestore.DocumentReference;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class DataRepository {

    DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
    }


    DocumentReference createUser(User user) throws Exception {
        AtomicBoolean error = new AtomicBoolean(false);
        AtomicReference<DocumentReference> userRef = new AtomicReference<>();

        AtomicReference<Exception> exception = new AtomicReference<>();

        AccountService
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
