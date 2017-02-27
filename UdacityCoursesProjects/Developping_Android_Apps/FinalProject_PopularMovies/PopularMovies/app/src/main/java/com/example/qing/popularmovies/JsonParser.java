package com.example.qing.popularmovies;

import org.json.JSONException;

/**
 * Created by Qing on 24/11/16.
 */

public interface JsonParser {
    MovieData[] extractData(String Json, int number) throws JSONException;
}
