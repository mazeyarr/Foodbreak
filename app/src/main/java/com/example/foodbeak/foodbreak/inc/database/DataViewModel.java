package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.foodbeak.foodbreak.inc.modules.product.entities.Product;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private boolean authIsCompany = false;
    private User mAuthUser;
    private CompanyUser mAuthCompanyUser;

    private DataRepository mDataRepository;

    private List<Product> shopCart = new ArrayList<>();

    public DataViewModel(Application application) {
        super(application);

        mDataRepository = new DataRepository(application);
    }

    // Users
    public Task<Void> createUser(User user) throws Exception {
       return mDataRepository.createUser(user);
    }
    public Task<Void> createUser(CompanyUser user) throws Exception {
       return mDataRepository.createUser(user);
    }


    public Task<DocumentSnapshot> setAuthUser() {
        return mDataRepository.getAuthAccount()
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.get("company") != null) {
                        this.authIsCompany = true;
                        this.setAuthUser(documentSnapshot.toObject(CompanyUser.class));
                    } else {
                        this.setAuthUser();
                    }
                });
    }

    public void setAuthUser(User user) {
        this.mAuthUser = user;
    }

    public void setAuthUser(CompanyUser user) {
        this.mAuthCompanyUser = user;
    }

    public <T> T getAuthUser(Class<T> tClass) {
        if (tClass.isInstance(User.class)) {
            return tClass.cast(mAuthUser);
        }

        if (tClass.isInstance(CompanyUser.class)) {
            return tClass.cast(mAuthCompanyUser);
        }

        return null;
    }

    public DocumentReference getAccount(String uid) throws Exception {
        return mDataRepository.getAccount(uid);
    }

    public boolean isAuthIsCompany() {
        return authIsCompany;
    }
}
