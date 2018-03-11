package com.example.andrew.timetracker;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.andrew.timetracker.data.AppDatabase;

/**
 * Created by andrew on 3/9/18.
 */

public class BaseApplication extends Application {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        initDatabase();
    }

    private void initDatabase() {
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, Constants.DATABASE_NAME).build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
