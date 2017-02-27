package com.qing.architect.google.todomvp.todomvp.taskdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Main UI for the task detail screen
 */
public class TaskDetailFragment extends Fragment
        implements TaskDetailContract.View{

    private static final String ARGUMENT_TASK_ID = "TASK_ID" ;

    public static TaskDetailFragment newInstance (@Nullable String taskId) {

        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMissingTask() {

    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle() {

    }

    @Override
    public void hideDescription() {

    }

    @Override
    public void showDescription() {

    }

    @Override
    public void showCompletionStatus(boolean complete) {

    }

    @Override
    public void showEditTask(String taskId) {

    }

    @Override
    public void showTaskDeleted() {

    }

    @Override
    public void showTaskMarkedComplete() {

    }

    @Override
    public void showTaskMarkedActive() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {

    }
}
