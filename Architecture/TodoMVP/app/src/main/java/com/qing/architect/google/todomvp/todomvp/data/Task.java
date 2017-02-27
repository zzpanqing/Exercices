package com.qing.architect.google.todomvp.todomvp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by qing on 15/02/17.
 */
public class Task {
    private final boolean mCompleted;
    @NonNull
    private final String mId;
    @Nullable
    private final String mTitle;
    @Nullable
    private final String mDescription;


    /**
     * Use this constructor to specify a completed Task if the Task
     * already has an id (copy of another Task)
     * @param title         title of the task
     * @param description   description of the task
     * @param id            id of the task
     * @param completed     true if the task is completed, false if it's
     *                      active
     */
    public Task(@Nullable String title, @Nullable String description,
                @NonNull String id, boolean completed){
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }


    /**
     * Use this constructor to create an active Task if the Task already
     * has an id (copy of another Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    public Task(@Nullable String title, @Nullable String description,
                @NonNull String id) {
        this(title, description, id, false);
    }

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    public Task(@Nullable String title, @Nullable String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's
     *                    active
     */
    public Task(@Nullable String title, @Nullable String description,
                boolean completed) {
        this(title, description, UUID.randomUUID().toString(),
                completed);
    }

    public boolean isCompleted() {
        return  mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public String getId() {
        return mId;
    }
}
