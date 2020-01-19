package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.modules.user.entities.User.UserEntity;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    private LiveData<List<UserEntity>> mAllUsers;

    public DataViewModel(Application application) {
        super(application);

        mDataRepository = new DataRepository(application);
        mAllUsers = mDataRepository.getAllUsers();
    }

    // Users
    public LiveData<List<UserEntity>> getAllUsers() {
        return mAllUsers;
    }

    public void insertUser(UserEntity user) {
        mDataRepository.insertUser(user);
    }
}
