package com.example.foodbeak.foodbreak.inc.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.foodbeak.foodbreak.inc.modules.user.database.UserDao;
import com.example.foodbeak.foodbreak.inc.modules.user.entities.User.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                UserEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";

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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return DATABASE_INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "onCreate: Callback has been called");

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDao dao = DATABASE_INSTANCE.userDao();
                dao.deleteUserAll();

                UserEntity user = new UserEntity(
                        1L,
                        "Mazeyar",
                        "Rezaei",
                        "mazeyarr@gmail.com",
                        "01-09-1997",
                        "12345"

                );

                dao.insertNewUser(user);
            });
        }
    };

}
