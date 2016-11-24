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

            AsyncTask<String, Void, String[]> fetchWeather = new FetchWeatherTask();
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




    class FetchWeatherTask extends AsyncTask<String, Void, String []>{


        @Override
        protected String[] doInBackground(String... strings) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                //String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043,usa&units=metric&cnt=7";
                //String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                //URL url = new URL(baseUrl.concat(apiKey));


                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, strings[0]+",fr")
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                        .build();

                URL url = new URL(buildUri.toString());

                Log.v("FetchWeatherTask.doInBa", url.toString());

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


               // Log.d("FetchWeatherTask", forecastJsonStr);


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

            try {
                String[] ret = getWeatherDataFromJson(forecastJsonStr, numDays);
                return  ret;
            } catch (JSONException iE) {
                iE.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] iStrings) {

            if(iStrings != null){
                mForecastAdapter.clear();
                mForecastAdapter.addAll(iStrings);
            }

        }

        /**
         * The date/time conversion code is going to be moved
         * outside the asynctask later so for convienience
         * we're breakingit out into its own method now
         */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp
            // (measured in seconds), it must be converted
            // to milliseconds in order to be converted
            // to valid date
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation
         */
        private String formatHighLows(double high, double low){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            String unitsType = sharedPref.getString( getString(R.string.pref_units_key),
                                                                      getString(R.string.pref_units_metric ));

            if(unitsType.equals(getString(R.string.pref_units_imperial)))
            {
                high = (high * 1.8) + 32;
                low  = (low * 1.8) + 32;
            } else if ( !unitsType.equals(getString(R.string.pref_units_metric) ))
                Log.d(LOG_TAG, "Unit type not found:" + unitsType);



            // Fore presentation, assume the user doesn't care about tenths of degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /**
         * Take the String representing the complete forecast in JSON Format
         * and pull out the data we need to construct the String needed for
         * the wireframes
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws  JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            GregorianCalendar calender = new GregorianCalendar();

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for (int i = 0; i < weatherArray.length(); i++){
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, whic hi what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject tempratureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = tempratureObject.getDouble(OWM_MAX);
                double low = tempratureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Forecast entry: " + s);
//            }

            return resultStrs;
        }
    }
}
