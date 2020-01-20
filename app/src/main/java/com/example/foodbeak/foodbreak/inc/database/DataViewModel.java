package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.foodbeak.foodbreak.inc.modules.product.entities.Product;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private User mAuthUser;
    private DataRepository mDataRepository;

    private List<Product> shopCart = new ArrayList<>();

    public DataViewModel(Application application) {
        super(application);

        mDataRepository = new DataRepository(application);
    }

    // Users
    public DocumentReference createUser(User user) throws Exception {
       return mDataRepository.createUser(user);
    }

    public void setAuthUser(User user) {
        this.mAuthUser = user;
    }

    public User getAuthUser() {
        return this.mAuthUser;
    }

    // Product
}