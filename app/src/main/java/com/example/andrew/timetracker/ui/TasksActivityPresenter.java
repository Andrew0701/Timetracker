package com.example.andrew.timetracker.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.andrew.timetracker.BaseApplication;
import com.example.andrew.timetracker.R;
import com.example.andrew.timetracker.data.AppDatabase;
import com.example.andrew.timetracker.data.models.Task;
import com.example.andrew.timetracker.ui.adapters.TasksAdapter;
import com.example.andrew.timetracker.ui.dialogs.NewTaskDialogFragment;
import com.example.andrew.timetracker.ui.dialogs.callbacks.FinishTaskClickListener;
import com.example.andrew.timetracker.utils.MailSender;
import com.example.andrew.timetracker.utils.PrefUtils;
import com.example.andrew.timetracker.utils.TasksToHtmlParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andrew on 3/9/18.
 */

public class TasksActivityPresenter implements TasksActivityContract.Presenter,
        FinishTaskClickListener {

    private Context context;
    private TasksActivityContract.View view;
    private FragmentManager fragmentManager;

    private NewTaskDialogFragment dialogFragment;

    private AppDatabase database;

    private TasksAdapter tasksAdapter;

    private List<Task> tasks;

    public TasksActivityPresenter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onViewReady(TasksActivityContract.View view) {
        this.view = view;

        init();

        if (PrefUtils.isTimerRunning(context)) {
            openNewTaskDialog();
        } else {
            loadTasks();
        }
    }

    @Override
    public void onFabNewTaskClick() {
        openNewTaskDialog();
    }

    @Override
    public void onBtnShareClick() {
        if (tasks.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.no_tasks), Toast.LENGTH_SHORT).show();
        } else {
            String html = TasksToHtmlParser.createHtml(tasks, context);
            MailSender.send(html, context);
        }
    }

    @Override
    public void onFinishTaskClick() {
        dialogFragment.dismiss();

        Task task = PrefUtils.getTempTask(context);
        task.setTime(System.currentTimeMillis() - task.getStartTime());

        saveTask(task);
    }

    private void openNewTaskDialog() {
        dialogFragment = NewTaskDialogFragment.newInstance();
        dialogFragment.setFinishTaskClickListener(this);

        dialogFragment.show(fragmentManager, NewTaskDialogFragment.class.getSimpleName());
    }

    private void init() {
        initDatabase();
        initTasksAdapter();
    }

    private void initDatabase() {
        database = ((BaseApplication) context.getApplicationContext()).getDatabase();
    }

    private void initTasksAdapter() {
        tasksAdapter = new TasksAdapter();
        view.setupTasksAdapter(tasksAdapter);
    }

    private void saveTask(Task task) {
        Observable.fromCallable(() -> database.taskDao().insert(task))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTaskSaved, Throwable::printStackTrace);
    }

    private void onTaskSaved(Long aLong) {
        loadTasks();
    }

    private void loadTasks() {
        view.toggleLoadingAnimation(true);

        database.taskDao().getAllTasks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTasksLoaded, Throwable::printStackTrace);
    }

    private void onTasksLoaded(List<Task> tasks) {
        view.toggleLoadingAnimation(false);

        this.tasks = tasks;

        if (!tasks.isEmpty()) {
            view.showTasks();
            tasksAdapter.update(tasks);
        } else {
            view.showNoTasksText();
        }
    }
}
