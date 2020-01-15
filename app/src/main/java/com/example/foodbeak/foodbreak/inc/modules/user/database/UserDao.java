package com.example.foodbeak.foodbreak.inc.modules.user.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foodbeak.foodbreak.inc.modules.user.entities.User.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertNewUser(UserEntity... userEntities);

    @Delete
    void deleteUser(UserEntity userEntity);

    @Query("SELECT * FROM user_table WHERE user_id = :userId")
    LiveData<UserEntity> loadUserById(Long userId);

    @Query("SELECT * FROM user_table ORDER BY email ASC")
    LiveData<List<UserEntity>> loadAllUsers();
}
