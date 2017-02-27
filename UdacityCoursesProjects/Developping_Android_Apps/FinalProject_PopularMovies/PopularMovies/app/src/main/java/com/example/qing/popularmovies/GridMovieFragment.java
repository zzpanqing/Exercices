package com.example.qing.popularmovies;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridMovieFragment extends Fragment {

    final static String LOG_TAG = GridMovieFragment.class.getName();
    final static int MOVIES_NUM_PER_FETCH = 20;
    final static String MOVIE_SORT_BY_POPULAR = "popular";
    final static String MOVIE_SORT_BY_TOP_RATED = "top_rated";
    MovieAdapter mMovieAdapter = null;


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
                MovieData movieData = new MovieData();
                movieData.mPoster_path = movie.getString(TMDB_POSTER_PATH);
                movieData.mAdult = movie.getBoolean(TMDB_ADULT);
                movieData.mOverview = movie.getString(TMDB_OVERVIEW);
                String StrDate = movie.getString(TMDB_RELEASE_DATE);
                movieData.mRelease_date = StrDate; // TODO use  DateFormat.parse(String s)
                movieData.mGenre_ids = movie.getString(TMDB_GENRE_IDS);
                movieData.mId = movie.getString(TMDB_ID);
                movieData.mOriginal_title = movie.getString(TMDB_ORIGINAL_TITLE);
                movieData.mOriginal_language = movie.getString(TMDB_ORIGINAL_LANGUAGE);
                movieData.mTitle = movie.getString(TMDB_TITLE);
                movieData.mBackdrop_path = movie.getString(TMDB_BACKDROP_PATH);
                movieData.mPopularity = movie.getDouble(TMDB_POPULARITY);
                movieData.mVote_count = movie.getInt(TMDB_VOTE_COUNT);
                movieData.mVideo = movie.getString(TMDB_VIDEO);
                movieData.mVote_average = movie.getDouble(TMDB_VOTE_AVERAGE);
                movieDatas[i] = movieData;
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

            mMovieAdapter.clear();
            mMovieAdapter.addAll((MovieData[]) ret);

        }
    };
    public GridMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_grid_movie, container, false);

        mMovieAdapter = new MovieAdapter(getContext(),
                R.layout.item_movie,
                R.id.image_title,
                new ArrayList<MovieData>());

        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(mMovieAdapter.mItemClickListener);

        updateMoviesList();

        return gridView;
    }

    String getMoviesDBUri(String sortBy){
        final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/"+sortBy+"/?";
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

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sortby = sharedPref.getString(getString(R.string.pref_sortBy_key),
                                            getString(R.string.pref_sortBy_popular_value));

            new FetchList(mMoviesDBFetchListener, mJsonParser, MOVIES_NUM_PER_FETCH)
                    .execute(getMoviesDBUri(sortby));
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

