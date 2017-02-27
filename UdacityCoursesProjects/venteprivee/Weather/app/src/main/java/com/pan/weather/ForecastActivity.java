package com.pan.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import io.fabric.sdk.android.Fabric;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_forecast);

        getSupportActionBar().setElevation(0);

        ForecastFragment forecastFragment = (ForecastFragment)(getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
        ff.updateWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(Uri contentUri) {

        Intent intent = new Intent(this, DetailActivity.class)
                .setData(contentUri);
        startActivity(intent);

    }
}
