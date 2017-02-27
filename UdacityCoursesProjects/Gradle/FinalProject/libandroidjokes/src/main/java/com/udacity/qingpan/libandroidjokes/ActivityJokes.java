package com.udacity.qingpan.libandroidjokes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by qingpan on 01/11/2016.
 */

public class ActivityJokes extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.jokes_layout, container, false);
        Bundle args = getArguments();
        String joke = args.getString("GCEJoke");
        TextView tv = (TextView) root.findViewById(R.id.jokeText);
        tv.setText(joke);
        return root;
    }
}
