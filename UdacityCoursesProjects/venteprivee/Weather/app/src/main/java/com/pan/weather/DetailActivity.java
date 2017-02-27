package com.pan.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Qing on 12/01/17.
 */
public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

    if(savedInstanceState == null){

        // create the detain fragment and add it to the activity
        // using a fragment transaction
        Bundle arguments = new Bundle();
        arguments.putParcelable(DetailFragment.DETAIL_URI, getIntent().getData());

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.weather_detail_container, fragment)
                .commit();
        }
    }
}
