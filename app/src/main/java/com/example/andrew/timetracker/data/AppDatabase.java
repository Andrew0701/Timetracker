package com.example.andrew.timetracker.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.andrew.timetracker.data.dao.TaskDao;
import com.example.andrew.timetracker.data.models.Task;

/**
 * Created by andrew on 3/9/18.
 */

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
