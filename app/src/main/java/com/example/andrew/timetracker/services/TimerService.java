package com.example.andrew.timetracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.andrew.timetracker.Constants;
import com.example.andrew.timetracker.utils.PrefUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by andrew on 3/10/18.
 */

public class TimerService extends Service {

    private Disposable intervalDisposable;

    private long startTime;

    @Override
    public void onCreate() {
        super.onCreate();

        startTime = PrefUtils.getTempTask(getBaseContext()).getStartTime();
        startTimerUpdate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        intervalDisposable.dispose();
    }

    private void startTimerUpdate() {
        intervalDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(aLong -> updateUI(), Throwable::printStackTrace);
    }

    private void updateUI() {
        Intent intent = new Intent(Constants.ACTION_UPDATE_TIMER);
        intent.putExtra(Constants.CURRENT_TIMER_TIME, System.currentTimeMillis() - startTime);

        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
