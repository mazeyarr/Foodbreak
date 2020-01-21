package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.foodbeak.foodbreak.inc.modules.product.entities.Product;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.CompanyUser;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
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

    // Product
}
