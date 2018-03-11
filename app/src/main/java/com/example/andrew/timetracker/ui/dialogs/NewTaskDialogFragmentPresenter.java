package com.example.andrew.timetracker.ui.dialogs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.example.andrew.timetracker.Constants;
import com.example.andrew.timetracker.R;
import com.example.andrew.timetracker.data.models.Task;
import com.example.andrew.timetracker.services.TimerService;
import com.example.andrew.timetracker.ui.dialogs.callbacks.FinishTaskClickListener;
import com.example.andrew.timetracker.utils.PrefUtils;
import com.example.andrew.timetracker.utils.Utils;

/**
 * Created by andrew on 3/9/18.
 */

public class NewTaskDialogFragmentPresenter implements NewTaskDialogFragmentContract.Presenter {

    private Context context;

    private NewTaskDialogFragmentContract.View view;

    private BroadcastReceiver timerBroadcastReceiver;

    private FinishTaskClickListener finishTaskClickListener;

    public NewTaskDialogFragmentPresenter(Context context) {
        this.context = context;

        initBroadcastReceiver();
    }

    @Override
    public void onViewReady(NewTaskDialogFragmentContract.View view) {
        this.view = view;

        if (PrefUtils.isTimerRunning(context)) {
            restoreTimer();
        }
    }

    @Override
    public void onResume() {
        registerTimerReceiver();
    }

    @Override
    public void onPause() {
        unregisterTimerReceiver();
    }

    @Override
    public void onBtnStartOrFinishTaskClick() {
        if (!PrefUtils.isTimerRunning(context)) {
            startTimer();
        } else {
            finishTimer();
        }
    }

    @Override
    public void setOnFinishTaskClickListener(FinishTaskClickListener finishTaskClickListener) {
        this.finishTaskClickListener = finishTaskClickListener;
    }

    private void startTimer() {
        PrefUtils.setTimerIsRunning(context, true);

        saveTempTask();
        setupViews();

        context.startService(new Intent(context, TimerService.class));
    }

    private void finishTimer() {
        PrefUtils.setTimerIsRunning(context, false);

        context.stopService(new Intent(context, TimerService.class));

        if (finishTaskClickListener != null) {
            finishTaskClickListener.onFinishTaskClick();
        }
    }

    private void restoreTimer() {
        setupViews();

        context.startService(new Intent(context, TimerService.class));
    }

    private void setupViews() {
        view.setTaskContainerVisibility(View.GONE);
        view.setTimerVisibility(View.VISIBLE);
        view.setButtonText(R.string.finish_task);
        view.setTimerText("00:00:00");
        view.setDialogNonCancelable();
    }

    private void saveTempTask() {
        Task task = new Task();

        task.setName(view.getTaskName());
        task.setDescription(view.getTaskDescription());
        task.setStartTime(System.currentTimeMillis());

        PrefUtils.setTempTask(context, task);
    }

    private void initBroadcastReceiver() {
        timerBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long time = intent.getLongExtra(Constants.CURRENT_TIMER_TIME, 0);

                updateTimer(time);
            }
        };
    }

    private void updateTimer(long time) {
        view.setTimerText(Utils.getTimeByTimestamp(time));
    }

    private void registerTimerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_UPDATE_TIMER);
        context.registerReceiver(timerBroadcastReceiver, intentFilter);
    }

    private void unregisterTimerReceiver() {
        context.unregisterReceiver(timerBroadcastReceiver);
    }
}
