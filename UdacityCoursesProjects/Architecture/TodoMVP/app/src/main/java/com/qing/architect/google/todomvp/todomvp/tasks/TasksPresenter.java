package com.qing.architect.google.todomvp.todomvp.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qing.architect.google.todomvp.todomvp.data.Task;
import com.qing.architect.google.todomvp.todomvp.data.source.TasksRepository;
import com.qing.architect.google.todomvp.todomvp.data.source.TasksDataSource;


import com.qing.architect.google.todomvp.todomvp.utils
        .EspressoIdlingResource;


import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link TasksFragment}), retrieves
 * the data and updates the UI as required.
 */
public class TasksPresenter implements TasksContract.Presenter{

    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;


    private boolean mFirstLoad = true;

    public TasksPresenter(@NonNull TasksRepository tasksRepository,
                          @NonNull TasksContract.View tasksView){
        mTasksRepository = checkNotNull(tasksRepository,
                "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView,
                "tasksView cannot be null!");
        mTasksView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced
        // on first load
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     *
     * @param forceUpdate Pass in true to refresh the data in the
     * {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in
     * the UI
     */
    private void loadTasks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }
        if (forceUpdate)
            mTasksRepository.refreshTasks();

        // The network request might be handled in a different thread
        // so make sure Espresso knows that the app is busy until the
        // reponse is handled
        EspressoIdlingResource.increment(); // App is busy until further
                                            // notice
        mTasksRepository.getTasks(
            new TasksDataSource.LoadTasksCallback() {
                @Override
                public void onTasksLoaded(List<Task> tasks) {
                    List<Task> tasksToShow = new ArrayList<Task>();

                    // This callback may be called twice, once for the
                    // cache and once for loading the data from the
                    // sever API, so we check before decrementing,
                    // otherwise it throws "Counter has been corrupted!"
                    // exception.
                    if ( ! EspressoIdlingResource.getIdlingResource().isIdleNow())
                        EspressoIdlingResource.decrement(); // Set app as idle

                    // We filter the tasks based on the requestType
                    for (Task task : tasks){
                        switch (mCurrentFiltering){
                            case ALL_TASKS:
                                tasksToShow.add(task);
                                break;
                            case ACTIVE_TASKS:
                                if (task.isActive())
                                    tasksToShow.add(task);
                                break;
                            case COMPLETED_TASKS:
                                if (task.isCompleted())
                                    tasksToShow.add(task);
                                break;
                            default:
                                tasksToShow.add(task);
                                break;
                        }
                    }
                    // The view may not be able to handle UI update anymore
                    if ( ! mTasksView.isActive())
                        return;

                    if (showLoadingUI) {
                        mTasksView.setLoadingIndicator(false);
                    }

                    processTasks(tasksToShow);
                }

                @Override
                public void onDataNotAvailable() {
                    // This view may not be able to handle UI updates
                    // anymore
                    if ( ! mTasksView.isActive() ){
                        return;
                    }
                    mTasksView.showLoadingTasksError();
                }
            });
    }

    private void processTasks(List<Task> tasksToShow) {
        if (tasksToShow.isEmpty())
            // Show a message indicating there are no tasks for that
            // filter type
            processEmptyTasks();
        else {
            // Show the list of tasks
            mTasksView.showTasks(tasksToShow);
            // Set the filter label's text
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering){
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering){
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }


    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }

    @Override
    public void openTaskDetails(@Nullable Task requestedTask) {
        checkNotNull(requestedTask, "requestedTask cannot be null");
        mTasksView.showTaskDetailsUi(requestedTask.getId());
    }

    @Override
    public void completeTask(@Nullable Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void setFiltering(TasksFilterType requestType) {

    }

    @Override
    public TasksFilterType getFiltering() {
        return null;
    }

    @Override
    public void start() {
        loadTasks(false);
    }
}
