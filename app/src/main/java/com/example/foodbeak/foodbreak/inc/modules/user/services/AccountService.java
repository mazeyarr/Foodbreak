package com.example.foodbeak.foodbreak.inc.modules.user.services;

import com.example.foodbeak.foodbreak.inc.database.AppDatabase;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.Account;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountService {
    private static final String TAG = "AccountService";

    private FirebaseFirestore mFirestore;
    public static AccountService USER_SERVICE_INSTANCE;

    public AccountService() {
        mFirestore = AppDatabase.getFirestore();
    }

    public Task<Void> createUser(User user) {
        return mFirestore
                .collection("users")
                .document(user.getUid())
                .set(user);
    }

    public Task<Void> createCompany(CompanyUser user) {
        return mFirestore
                .collection("users")
                .document(user.getUid())
                .set(user);
    }

    public Task<Void> createAccount(Account account) {
        return mFirestore
                .collection("users")
                .document(account.getUid())
                .set(account);
    }

    public DocumentReference getAccountByUid(String uid) {
        return mFirestore.collection("users")
                .document(uid);
    }

    public static AccountService getInstance() {
        if (USER_SERVICE_INSTANCE == null) {
            synchronized (AccountService.class) {
                if (USER_SERVICE_INSTANCE == null) {
                    USER_SERVICE_INSTANCE = new AccountService();
                }
            }
        }

        return USER_SERVICE_INSTANCE;
    }
}
