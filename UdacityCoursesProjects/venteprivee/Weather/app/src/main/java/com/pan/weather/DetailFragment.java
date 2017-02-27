package com.pan.weather;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pan.weather.data.WeatherContract.*;
import com.squareup.picasso.Picasso;

/**
 * Created by Qing on 12/01/17.
 */
public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = DetailFragment.class.getName();
    final static int DETAIL_LOADER = 0;

    static final String DETAIL_URI = "URI";

    ShareActionProvider mShareActionProvider;
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";

    private final String[] FORECAST_COLUMNS = {
           WeatherEntry.TABLE_NAME + "." +WeatherEntry._ID,
           WeatherEntry.COLUMN_DATE,
           WeatherEntry.COLUMN_DEGREES,
           WeatherEntry.COLUMN_HUMIDITY,
           WeatherEntry.COLUMN_LOC_KEY,
           WeatherEntry.COLUMN_MAX_TEMP,
           WeatherEntry.COLUMN_MIN_TEMP,
           WeatherEntry.COLUMN_PRESSURE,
           WeatherEntry.COLUMN_SHORT_DESC,
           WeatherEntry.COLUMN_WIND_SPEED,
            // This works because the WeatherProvider returns location data joined with
            // weather data, even though they're stored in two different tables.
            WeatherEntry.COLUMN_WEATHER_CONDITION_ID,
            WeatherEntry.COLUMN_WEATHER_CONDITION_ICON
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
    static final int COL_WEATHER_CONDITION_ID = 10;
    static final int COL_WEATHER_CONDITION_ICON = 11;

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
    private Uri mUri;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        mDayView = (TextView) root.findViewById(R.id.detail_day_textview);
        mDateView = (TextView) root.findViewById(R.id.detail_date_textview);
        mHighView = (TextView) root.findViewById(R.id.detail_high_textview);
        mLowView = (TextView) root.findViewById(R.id.detail_low_textview);
        mIcon = (ImageView) root.findViewById(R.id.detail_icon);
        mForecastView = (TextView) root.findViewById(R.id.detail_forecast_textview);
        mHumidityView = (TextView) root.findViewById(R.id.detail_humidity_textview);
        mWindView = (TextView) root.findViewById(R.id.detail_wind_textview);
        mPressureView = (TextView) root.findViewById(R.id.detail_pressure_textview);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private Intent createShareForecastIntent() {
        Intent intent = null;
        if (mShareActionProvider != null) {

            intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // back icon return to this app
            // not to the app containing the intent
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mForecast + FORECAST_SHARE_HASHTAG);

        }
        return intent;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (null != mUri) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed
            return new CursorLoader(getContext(),
                    mUri,
                    FORECAST_COLUMNS,
                    null,
                    null,
                    null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            return;
        }
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
        // Read weather condition ID from cursor
        String icon = data.getString(COL_WEATHER_CONDITION_ICON);
        Picasso.with(getContext())
                .load(Utility.getIconUrl(icon))
                .error(R.mipmap.ic_launcher)
                .into(mIcon);
        mIcon.setContentDescription(weatherDescription);
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

    public void onLocationChanged(String newLocation) {
        // replace the uri, since the location has changed
        Uri uri = mUri;
        if (null != uri) {
            long date = WeatherEntry.getDateFromUri(uri);
            Uri updatedUri = WeatherEntry.buildWeatherLocationWithDate(newLocation, date);
            mUri = updatedUri;
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        }
    }

}