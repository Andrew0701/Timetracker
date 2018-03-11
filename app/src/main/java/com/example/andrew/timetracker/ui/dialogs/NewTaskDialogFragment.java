package com.example.andrew.timetracker.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andrew.timetracker.R;
import com.example.andrew.timetracker.ui.dialogs.callbacks.FinishTaskClickListener;
import com.example.andrew.timetracker.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by andrew on 3/9/18.
 */

public class NewTaskDialogFragment extends DialogFragment
        implements NewTaskDialogFragmentContract.View {

    @BindView(R.id.ll_task_container)
    LinearLayout llTaskContainer;

    @BindView(R.id.et_task_name)
    TextInputEditText etTaskName;

    @BindView(R.id.et_task_description)
    TextInputEditText etTaskDescription;

    @BindView(R.id.tv_timer)
    TextView tvTimer;

    @BindView(R.id.btn_start_or_finish_task)
    Button btnStartOrFinishTask;

    private Unbinder unbinder;

    private FinishTaskClickListener finishTaskClickListener;

    private NewTaskDialogFragmentContract.Presenter presenter;

    public static NewTaskDialogFragment newInstance() {
        return new NewTaskDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_new_task, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.onPause();
    }

    @OnClick(R.id.btn_start_or_finish_task)
    public void onBtnStartOrFinishTaskClick() {
        Utils.hideKeyboard(getContext(), etTaskName);
        Utils.hideKeyboard(getContext(), etTaskDescription);

        presenter.onBtnStartOrFinishTaskClick();
    }

    @Override
    public void setButtonText(@StringRes int text) {
        btnStartOrFinishTask.setText(text);
    }

    @Override
    public void setTaskContainerVisibility(int visibility) {
        llTaskContainer.setVisibility(visibility);
    }

    @Override
    public void setTimerVisibility(int visibility) {
        tvTimer.setVisibility(visibility);
    }

    @Override
    public void setTimerText(String text) {
        tvTimer.setText(text);
    }

    @Override
    public void setDialogNonCancelable() {
        this.setCancelable(false);
    }

    @Override
    public String getTaskName() {
        return etTaskName.getText().toString();
    }

    @Override
    public String getTaskDescription() {
        return etTaskDescription.getText().toString();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    public void setFinishTaskClickListener(FinishTaskClickListener finishTaskClickListener) {
        this.finishTaskClickListener = finishTaskClickListener;
    }

    private void initPresenter() {
        presenter = new NewTaskDialogFragmentPresenter(getContext());
        presenter.setOnFinishTaskClickListener(finishTaskClickListener);

        presenter.onViewReady(this);
    }
}
