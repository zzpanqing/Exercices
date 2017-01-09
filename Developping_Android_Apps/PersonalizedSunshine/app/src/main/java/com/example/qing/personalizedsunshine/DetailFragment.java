package com.example.qing.personalizedsunshine;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qing.personalizedsunshine.data.WeatherContract;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment
                            implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = DetailFragment.class.getName();
    final static int DETAIL_LOADER = 0;

    ShareActionProvider mShareActionProvider;
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";

    private final String[] FORECAST_COLUMNS = {
            WeatherContract.WeatherEntry.TABLE_NAME+"."+ WeatherContract.WeatherEntry._ID,
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_LOC_KEY,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED
    };

    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DEGREES = 2;
    static final int COL_WEATHER_HUMIDITY = 3;
    static final int COL_WEATHER_LOC_KEY = 4;
    static final int COL_WEATHER_MAX_TEMP = 5;
    static final int COL_WEATHER_MIN_TEMP = 6;
    static final int COL_WEATHER_PRESSURE = 7;
    static final int COL_WEATHER_DESC = 8;
    static final int COL_WEATHER_WIND_SPEED = 9;
    private String mForecast;

    TextView mDayView;
    TextView mDateView;
    TextView mHighView;
    TextView mLowView;
    ImageView mIcon;
    TextView mForecastView;
    TextView mHumidityView;
    TextView mWindView;
    TextView mPressureView;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        mDayView = (TextView)root.findViewById(R.id.detail_day_textview);
        mDateView = (TextView)root.findViewById(R.id.detail_date_textview);
        mHighView = (TextView)root.findViewById(R.id.detail_high_textview);
        mLowView = (TextView)root.findViewById(R.id.detail_low_textview);
        mIcon = (ImageView)root.findViewById(R.id.detail_icon);
        mForecastView =(TextView)root.findViewById(R.id.detail_forecast_textview);
        mHumidityView = (TextView)root.findViewById(R.id.detail_humidity_textview);
        mWindView = (TextView)root.findViewById(R.id.detail_wind_textview);
        mPressureView  = (TextView)root.findViewById(R.id.detail_pressure_textview);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set / change the share intent
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        // attach an intent to this ShareActionProvider. You can update this at any time
        // like when the user selects a new piece of data they might like to share
        if(mForecast != null && mShareActionProvider != null)
            mShareActionProvider.setShareIntent(createShareForecastIntent());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private Intent createShareForecastIntent() {
        Intent intent = null;
        if(mShareActionProvider != null){

            intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // back icon return to this app
                                                                // not to the app containing the intent
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mForecast+FORECAST_SHARE_HASHTAG);

        }
        return intent;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.i(LOG_TAG, "In onCreateLoader");
        Intent intent = getActivity().getIntent();
        if (intent == null)
            return null;
        return new CursorLoader(getContext(),
                                intent.getData(),
                                FORECAST_COLUMNS,
                                null,
                                null,
                                null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) { return; }
        Long dateL = data.getLong(COL_WEATHER_DATE);
        mDayView.setText(Utility.getDayName(getContext(), dateL));
        String dateString = Utility.formatDate(dateL);
        mDateView.setText(dateString);
        String weatherDescription = data.getString(COL_WEATHER_DESC);
        mForecastView.setText(weatherDescription);
        boolean isMetric = Utility.isMetric(getActivity());
        String highTemp = Utility.formatTemperature(getContext(),
                data.getDouble(COL_WEATHER_MAX_TEMP), isMetric);
        String lowTemp = Utility.formatTemperature(getContext(),
                data.getDouble(COL_WEATHER_MIN_TEMP), isMetric);
        mHighView.setText(highTemp);
        mLowView.setText(lowTemp);
        mIcon.setImageResource(R.mipmap.ic_launcher);
        // Read humidity from cursor and update view
        float humitityValue = data.getFloat(COL_WEATHER_HUMIDITY);
        mHumidityView.setText(getActivity().getString(R.string.format_humidity, humitityValue));
        // Read wind speed and direction from cursor and update view
        float windSpeedStr = data.getFloat(COL_WEATHER_WIND_SPEED);
        float windDirStr = data.getFloat(COL_WEATHER_DEGREES);
        mWindView.setText(Utility.getFormattedWind(getActivity(), windSpeedStr, windDirStr));

        // Read pressure from cursor and update view
        float pressure = data.getFloat(COL_WEATHER_PRESSURE);
        mPressureView.setText(getActivity().getString(R.string.format_pressure, pressure));
        mForecast = String.format("%s - %s - %s/%s", dateString, weatherDescription, highTemp, lowTemp);


        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
            }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }
}
