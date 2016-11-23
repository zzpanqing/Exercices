package com.example.qing.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridMovieFragment extends Fragment {

    ArrayAdapter<String> mMovieAdapter = null;
    public GridMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_grid_movie, container, false);

        String[] fakedata = {"first movies", "second movie"};
        ArrayList<String> fakeArrayList = new ArrayList<String>(Arrays.asList(fakedata));

        mMovieAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.item_movie,
                R.id.description,
                fakeArrayList);


        gridView.setAdapter(mMovieAdapter);

        return gridView;
    }
}
