package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import com.example.foodbeak.foodbreak.inc.modules.auth.services.AuthService;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.example.foodbeak.foodbreak.inc.modules.user.services.AccountService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

class DataRepository {

    DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
    }


    Task<Void> createUser(User user) {
        return AccountService
                .getInstance()
                .createUser(user);
    }


    Task<Void> createUser(CompanyUser user) {
        return AccountService
                .getInstance()
                .createCompany(user);
    }

    DocumentReference getAuthAccount() {
        return AccountService.getInstance().getAccountByUid(
                AuthService.getInstance().getAuthUser().getUid()
        );
    }

    DocumentReference getAccount(String uid) throws Exception {
        if (AuthService.getInstance().isAuthUser()) {
            return AccountService.getInstance()
                    .getAccountByUid(uid);
        }

        throw new Exception("User is not authenticated");
    }
}
