package com.qing.architect.google.todomvp.todomvp.addedittask;

import com.qing.architect.google.todomvp.todomvp.BasePresenter;
import com.qing.architect.google.todomvp.todomvp.BaseView;

/**
 * This specifies the contract between the view and the presenter
 */

public class AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }

}
