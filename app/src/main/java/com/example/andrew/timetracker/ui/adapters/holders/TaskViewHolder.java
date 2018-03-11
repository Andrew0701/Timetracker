package com.example.andrew.timetracker.ui.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andrew.timetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrew on 3/9/18.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_task_name)
    public TextView tvTaskName;

    @BindView(R.id.tv_task_description)
    public TextView tvTaskDescription;

    @BindView(R.id.tv_task_time)
    public TextView tvTaskTime;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
