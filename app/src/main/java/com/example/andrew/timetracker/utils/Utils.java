package com.example.andrew.timetracker.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by andrew on 3/11/18.
 */

public class Utils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getTimeByTimestamp(long timestamp) {
        long seconds = timestamp / 1000;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60));
    }
}
