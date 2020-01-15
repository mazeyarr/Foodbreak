package com.example.foodbeak.foodbreak.inc.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodbeak.foodbreak.inc.modules.user.database.UserDao;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "foodbreak_database";

    public abstract UserDao userDao();

    private static volatile AppDatabase DATABASE_INSTANCE;
    private static final int NUMBER_OF_AVAILABLE_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_AVAILABLE_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (DATABASE_INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (DATABASE_INSTANCE == null) {
                    DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
//                            .addMigrations() // TODO: implement
                            .build();
                }
            }
        }

        return DATABASE_INSTANCE;
    }

}
