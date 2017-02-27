package com.qing.architect.google.todomvp.todomvp.data;

import android.support.annotation.NonNull;

import com.qing.architect.google.todomvp.todomvp.data.source
        .TasksDataSource;

/**
 * Implementation of a remote data source with static access to the
 * data for easy testing
 */
public class FakeTasksRemoteDataSource implements TasksDataSource{

    private static FakeTasksRemoteDataSource INSTANCE;


    // Prevent direct instantiation.
    private FakeTasksRemoteDataSource() {}

    public static FakeTasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTasksRemoteDataSource();
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
