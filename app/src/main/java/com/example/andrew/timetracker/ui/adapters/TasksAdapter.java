package com.example.andrew.timetracker.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.timetracker.R;
import com.example.andrew.timetracker.data.models.Task;
import com.example.andrew.timetracker.ui.adapters.holders.TaskViewHolder;
import com.example.andrew.timetracker.utils.Utils;

import java.util.List;

/**
 * Created by andrew on 3/9/18.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> tasks;

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.tvTaskName.setText(task.getName());
        holder.tvTaskDescription.setText(task.getDescription());
        holder.tvTaskTime.setText(Utils.getTimeByTimestamp(task.getTime()));
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    public void update(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}
