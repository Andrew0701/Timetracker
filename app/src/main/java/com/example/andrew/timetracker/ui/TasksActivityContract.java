package com.example.andrew.timetracker.ui;

import android.support.v7.widget.RecyclerView;

/**
 * Created by andrew on 3/9/18.
 */

public interface TasksActivityContract {

    interface View {

        void setupTasksAdapter(RecyclerView.Adapter adapter);

        void showNoTasksText();

        void showTasks();

        void toggleLoadingAnimation(boolean show);
    }

    interface Presenter {

        void onViewReady(TasksActivityContract.View view);

        void onFabNewTaskClick();

        void onBtnShareClick();
    }
}

