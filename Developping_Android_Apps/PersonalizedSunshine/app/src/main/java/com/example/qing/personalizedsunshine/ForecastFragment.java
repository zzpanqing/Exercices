package com.example.qing.personalizedsunshine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qingpan on 11/11/2016.
 */

public class ForecastFragment extends Fragment {

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


        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));

        ArrayAdapter forecastAdapter = new ArrayAdapter<String>(getContext(),
                                                        R.layout.list_item_forecast,
                                                        R.id.list_item_forecast_textview,
                                                        weekForecast);
        ListView list = (ListView) rootView.findViewById(R.id.listview_forecast);
        list.setAdapter(forecastAdapter);




        // get data from open weather map
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
           int permissionCheck = ContextCompat.checkSelfPermission(  getActivity(),
                                                Manifest.permission.INTERNET);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                AsyncTask fetchWeather = new FetchWeatherTask();
                fetchWeather.execute();
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    class FetchWeatherTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043,usa&units=metric&cnt=7";
                String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                URL url = new URL(baseUrl.concat(apiKey));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a string;
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.d("FetchWeatherTask", forecastJsonStr);
                return forecastJsonStr;
            } catch (MalformedURLException e) {
                Log.e("ForecastFragment", " new URL throw MalformedURLException " + e.getMessage());
            } catch (IOException ioe) {
                Log.e("ForecastFragment", "url.openConnection() throw IOException " + ioe.getMessage());
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
            }
            return null;
        }

    }
}
