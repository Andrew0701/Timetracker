package com.example.andrew.timetracker.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.andrew.timetracker.data.models.Task;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by andrew on 3/9/18.
 */

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    Flowable<List<Task>> getAllTasks();

    @Insert
    Long insert(Task task);
}
