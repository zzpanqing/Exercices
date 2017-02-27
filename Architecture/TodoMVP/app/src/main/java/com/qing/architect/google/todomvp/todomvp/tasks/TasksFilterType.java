package com.qing.architect.google.todomvp.todomvp.tasks;

/**
 * Created by qing on 15/02/17.
 */

/**
 * Used with the filter spinner in the tasks list.
 */
public enum TasksFilterType {

    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS

}
