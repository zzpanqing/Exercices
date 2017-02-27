package com.qing.architect.google.todomvp.todomvp;

/**
 * Created by qing on 16/02/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.qing.architect.google.todomvp.todomvp.data.FakeTasksRemoteDataSource;
import com.qing.architect.google.todomvp.todomvp.data.source.TasksRepository;


import com.qing.architect.google.todomvp.todomvp.data.source.local
        .TasksLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for {@link TasksDataSource}
 * at compile time. This is useful for testing, since it allows us to
 * use a fake instance of the class to isolate the dependencies and run
 * a test hermetically 密封地
 */
public class Injection {
    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(context));
    }
}
