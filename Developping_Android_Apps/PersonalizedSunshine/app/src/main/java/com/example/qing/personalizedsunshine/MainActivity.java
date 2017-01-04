package com.example.qing.personalizedsunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate is called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        Log.v(LOG_TAG, "onStart is called");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "onStop is called");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG, "onPause is called");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "onResume is called");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "onDestroy is called");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_locate_on_map:
                openPreferredLocationInMap();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String location = sharedPref.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q",location)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {
            Log.i(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }
}
