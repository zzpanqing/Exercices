package com.qing.architect.google.todomvp.todomvp.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.qing.architect.google.todomvp.todomvp.data.Task;
import com.qing.architect.google.todomvp.todomvp.data.source
        .TasksDataSource;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db
 */
public class TasksLocalDataSource implements TasksDataSource{

    private static TasksLocalDataSource INSTANCE;

    // Prevent direct instantiation
    private TasksLocalDataSource(@NonNull Context context) {
        checkNotNull(context);

    }

    public static TasksLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(
            @NonNull LoadTasksCallback callback) {

    }

    @Override
    public void getTask(@NonNull String taskId,
                        @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
