package com.example.andrew.timetracker.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andrew.timetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity
        implements TasksActivityContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_new_task)
    FloatingActionButton fabNewTask;

    @BindView(R.id.ll_tasks_container)
    LinearLayout llTasksContainer;

    @BindView(R.id.rv_tasks)
    RecyclerView rvTasks;

    @BindView(R.id.tv_no_tasks)
    TextView tvNoTasks;

    @BindView(R.id.fl_progress_bar)
    FrameLayout flProgressBar;

    private TasksActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            presenter.onBtnShareClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupTasksAdapter(RecyclerView.Adapter adapter) {
        rvTasks.setAdapter(adapter);
    }

    @Override
    public void showNoTasksText() {
        llTasksContainer.setVisibility(View.GONE);
        tvNoTasks.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTasks() {
        llTasksContainer.setVisibility(View.VISIBLE);
        tvNoTasks.setVisibility(View.GONE);
    }

    @Override
    public void toggleLoadingAnimation(boolean isLoading) {
        if (isLoading) {
            flProgressBar.setVisibility(View.VISIBLE);
        } else {
            flProgressBar.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        fabNewTask.setOnClickListener(view -> presenter.onFabNewTaskClick());
    }

    private void initPresenter() {
        presenter = new TasksActivityPresenter(this, getSupportFragmentManager());

        presenter.onViewReady(this);
    }
}
