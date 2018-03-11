package com.example.andrew.timetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.andrew.timetracker.Constants;
import com.example.andrew.timetracker.data.models.Task;

/**
 * Created by andrew on 3/10/18.
 */

public class PrefUtils {

    public static void setTempTask(Context context, Task task) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(Constants.TEMP_TASK, Context.MODE_PRIVATE).edit();

        editor.putString(Constants.TASK_NAME, task.getName());
        editor.putString(Constants.TASK_DESCRIPTION, task.getDescription());
        editor.putLong(Constants.START_TIME, task.getStartTime());

        editor.apply();
    }

    public static Task getTempTask(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.TEMP_TASK, Context.MODE_PRIVATE);

        Task task = new Task();
        task.setName(sp.getString(Constants.TASK_NAME, ""));
        task.setDescription(sp.getString(Constants.TASK_DESCRIPTION, ""));
        task.setStartTime(sp.getLong(Constants.START_TIME, 0));

        return task;
    }

    public static boolean isTimerRunning(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        return sp.getBoolean(Constants.IS_TIMER_RUNNING, false);
    }

    public static void setTimerIsRunning(Context context, boolean isTimerRunning) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(Constants.IS_TIMER_RUNNING, isTimerRunning);

        editor.apply();
    }
}
