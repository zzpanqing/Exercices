package com.example.qing.popularmovies;

import java.util.Objects;

/**
 * Created by Qing on 24/11/16.
 */

public interface OnTaskCompleted{
    void onTaskCompleted(String result);
    void onTaskCompleted(String[] result);
    void onTaskCompleted(Object[] ret);

}
