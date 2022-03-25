package com.lavertis.project2.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Phone.class, exportSchema = false, version = 1)
public abstract class PhoneDatabase extends RoomDatabase {
    private static final String DB_NAME = "phone_db";
    private static final int THREAD_COUNT = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
    private static volatile PhoneDatabase INSTANCE;
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao phoneDao = INSTANCE.phoneDao();
                phoneDao.insertAll(PhoneSeedData.phoneList);
            });
        }
    };

    public static synchronized PhoneDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhoneDatabase.class, DB_NAME)
                    .addCallback(roomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract PhoneDao phoneDao();
}
