package com.qing.architect.google.todomvp.todomvp.taskdetail;

import com.qing.architect.google.todomvp.todomvp.BasePresenter;
import com.qing.architect.google.todomvp.todomvp.BaseView;
import com.qing.architect.google.todomvp.todomvp.tasks.TasksContract;

/**
 * This specifies the contract between the view and the presenter
 */
public interface TaskDetailContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle();

        void hideDescription();

        void showDescription();

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}
