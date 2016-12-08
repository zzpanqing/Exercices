package com.example.qing.personalizedsunshine;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by qingpan on 11/11/2016.
 */

public class ForecastFragment extends Fragment {
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

    public ArrayAdapter mForecastAdapter;
    final int MY_PERMISSIONS_REQUEST_INTERNET = 111;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mForecastAdapter = new ArrayAdapter<String>(getContext(),
                                                        R.layout.list_item_forecast,
                                                        R.id.list_item_forecast_textview,
                                                        new ArrayList<String>());
        ListView list = (ListView) rootView.findViewById(R.id.listview_forecast);
        list.setAdapter(mForecastAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String detailString = (String) mForecastAdapter.getItem(i);
                Toast.makeText(getContext(), detailString,Toast.LENGTH_LONG).show();

                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra(Intent.EXTRA_TEXT, detailString);
                startActivity(detailIntent);
            }
        });


        // get data from open weather map
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_refresh:
                    updateWeather();
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }



    private void updateWeather() {
        int permissionCheck = ContextCompat.checkSelfPermission(  getActivity(),
                                             Manifest.permission.INTERNET);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            String defaultLocation = "78800";
            String location = sharedPref.getString(getString(R.string.pref_location_key), defaultLocation);

            AsyncTask<String, Void, String[]> fetchWeather = new FetchWeatherTask(getContext(), mForecastAdapter);
            fetchWeather.execute(location);
        }
        else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.INTERNET)){
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String [] {Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);
                // MY_PERMISSIONS_REQUEST_INTERNET is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

}
