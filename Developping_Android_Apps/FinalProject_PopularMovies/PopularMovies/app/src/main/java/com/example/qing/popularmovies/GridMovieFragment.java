package com.example.qing.popularmovies;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridMovieFragment extends Fragment {

    final static String LOG_TAG = GridMovieFragment.class.getName();
    final static int MOVIES_NUM_PER_FETCH = 20;
    final static String MOVIE_CATEGORY_POPULAR = "popular";
    ArrayAdapter mMovieAdapter = null;


    JsonParser mJsonParser = new JsonParser() {
        @Override
        public MovieData[] extractData(String JsonStr, int number) throws JSONException {
            final String TMDB_PAGE = "page";
            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_ADULT = "adult";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_GENRE_IDS = "genre_ids";
            final String TMDB_ID = "id";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_ORIGINAL_LANGUAGE = "original_language";
            final String TMDB_TITLE = "title";
            final String TMDB_BACKDROP_PATH = "backdrop_path";
            final String TMDB_POPULARITY = "popularity";
            final String TMDB_VOTE_COUNT = "vote_count";
            final String TMDB_VIDEO = "video";
            final String TMDB_VOTE_AVERAGE = "vote_average";
            final String TMDB_TOTAL_RESULTS = "total_results";
            final String TMDB_TOTAL_PAGES = "total_pages";

            JSONObject response = new JSONObject(JsonStr);
            JSONArray movieArray = response.getJSONArray(TMDB_RESULTS);
            MovieData[] movieDatas = new MovieData[MOVIES_NUM_PER_FETCH];
            for(int i = 0; i<movieArray.length(); ++i){
                JSONObject movie = movieArray.getJSONObject(i);
                movieDatas[i].mPoster_path = movie.getString(TMDB_POSTER_PATH);
                movieDatas[i].mAdult = movie.getBoolean(TMDB_ADULT);
                movieDatas[i].mOverview = movie.getString(TMDB_OVERVIEW);
                movieDatas[i].mRelease_date = new Date(movie.getString(TMDB_RELEASE_DATE)); // TODO
                movieDatas[i].mGenre_ids = movie.getString(TMDB_GENRE_IDS);
                movieDatas[i].mId = movie.getString(TMDB_ID);
                movieDatas[i].mOriginal_title = movie.getString(TMDB_ORIGINAL_TITLE);
                movieDatas[i].mOriginal_language = movie.getString(TMDB_ORIGINAL_LANGUAGE);
                movieDatas[i].mTitle = movie.getString(TMDB_TITLE);
                movieDatas[i].mBackdrop_path = movie.getString(TMDB_BACKDROP_PATH);
                movieDatas[i].mPopularity = movie.getDouble(TMDB_POPULARITY);
                movieDatas[i].mVote_count = movie.getInt(TMDB_VOTE_COUNT);
                movieDatas[i].mVideo = movie.getString(TMDB_VIDEO);
                movieDatas[i].mVote_average = movie.getDouble(TMDB_VOTE_AVERAGE);
            }

            return movieDatas;
        }
    };

    OnTaskCompleted mMoviesDBFetchListener = new OnTaskCompleted() {
        @Override
        public void onTaskCompleted(String result) {}
        @Override
        public void onTaskCompleted(String[] result) {}
        @Override
        public void onTaskCompleted(Object[] ret) {

            if (ret == null){
                Log.e(LOG_TAG, "no data");
                Toast.makeText(getContext(), "no movie data", Toast.LENGTH_SHORT);
                return;
            }
            String[] movieTitles = new String[ret.length];
            for(int i= 0; i<ret.length; ++i){
                movieTitles[i] = ((MovieData)ret[i]).mTitle;
            }

            mMovieAdapter.clear();
            mMovieAdapter.addAll(movieTitles);
        }
    };
    public GridMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_grid_movie, container, false);

        String[] fakedata = {"first movies", "second movie", "third", "fourth", "sixth"};
        ArrayList<String> fakeArrayList = new ArrayList<String>(Arrays.asList(fakedata));

        mMovieAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.item_movie,
                R.id.description,
                fakeArrayList);

        gridView.setAdapter(mMovieAdapter);

        updateMoviesList();

        return gridView;
    }

    String getMoviesDBUri(String category){
        final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/"+category+"/?";
        final String LANGUAGE_PARAM = "language";
        final String APPID_PARAM = "api_key";


        Uri buildUri = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY_V3)
                .build();
        return  buildUri.toString();
    }

    void updateMoviesList(){

        final int MY_PERMISSIONS_REQUEST_INTERNET = 111;

        int permissionCheck = ContextCompat.checkSelfPermission(  getActivity(),
                Manifest.permission.INTERNET);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {

            new FetchList(mMoviesDBFetchListener, mJsonParser, MOVIES_NUM_PER_FETCH)
                    .execute(getMoviesDBUri(MOVIE_CATEGORY_POPULAR));;
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

}

