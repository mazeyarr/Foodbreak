package com.example.foodbeak.foodbreak.inc.database;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class AppDatabase {
    private static final String TAG = "AppDatabase";

    public static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }
}
