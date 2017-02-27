package com.qing.architect.google.todomvp.todomvp.data.source;

import android.support.annotation.NonNull;

import com.qing.architect.google.todomvp.todomvp.data.Task;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation to load tasks from the data sources into a
 * cache.
 * For simplicity, this implements a dumb synchronisation between
 * local persisted data and data obtained from the server, by using
 * the remote data source only if the local database doesn't exist
 * or is empty.
 */
public class TasksRepository implements  TasksDataSource {

    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;

    private final TasksDataSource mTasksLocalDataSource;

    // prevent direct instantiation.
    private  TasksRepository(@NonNull TasksDataSource
                                     tasksRemoteDataSource,
                             @NonNull TasksDataSource
                                     tasksLocalDataSource){
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);

    }

    /**
     * Returns the single instance of this class, creating it if
     * necessary.
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource the device storage data source
     * @return the {@link TasksRepository} instance
     */
    public static TasksRepository getInstance(
            TasksDataSource tasksRemoteDataSource,
            TasksDataSource tasksLocalDataSource ){
        if (INSTANCE == null){
            INSTANCE = new TasksRepository(tasksRemoteDataSource,
                    tasksRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force
     * {@link #getInstance(TasksDataSource, TasksDataSource)}
     * to create a new instance next time it's called
     */
    public static void destroyInstance() {INSTANCE = null;}
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
