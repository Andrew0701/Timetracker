package com.example.andrew.timetracker.ui.dialogs;

import android.support.annotation.StringRes;

import com.example.andrew.timetracker.ui.dialogs.callbacks.FinishTaskClickListener;

/**
 * Created by andrew on 3/9/18.
 */

public interface NewTaskDialogFragmentContract {

    interface View {

        void setButtonText(@StringRes int text);

        void setTaskContainerVisibility(int visibility);

        void setTimerVisibility(int visibility);

        void setTimerText(String text);

        void setDialogNonCancelable();

        String getTaskName();

        String getTaskDescription();
    }

    interface Presenter {

        void onViewReady(View view);

        void onResume();

        void onPause();

        void onBtnStartOrFinishTaskClick();

        void setOnFinishTaskClickListener(FinishTaskClickListener finishTaskClickListener);
    }
}
