package com.example.foodbeak.foodbreak.inc.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foodbeak.foodbreak.inc.modules.user.database.UserDao;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User.UserEntity;

import java.util.List;

public class DataRepository {

    private UserDao mUserDao;
    private LiveData<List<UserEntity>> mAllUsers;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.loadAllUsers();
    }

    // Users

    public LiveData<List<UserEntity>> getAllUsers() {
        return mAllUsers;
    }

    void insertUser(UserEntity user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insertNewUser(user);
        });
    }
}
