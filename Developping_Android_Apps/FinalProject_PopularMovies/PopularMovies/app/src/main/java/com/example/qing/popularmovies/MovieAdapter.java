package com.example.qing.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by qingpan on 26/11/2016.
 */

public class MovieAdapter extends ArrayAdapter<MovieData> {
    private final static String LOG_TAG = MovieAdapter.class.getName();
    final static String TheMovieDBConfigFakeJsonStr = "{\"images\":{\"base_url\":\"http://image.tmdb.org/t/p/\",\"secure_base_url\":\"https://image.tmdb.org/t/p/\",\"backdrop_sizes\":[\"w300\",\"w780\",\"w1280\",\"original\"],\"logo_sizes\":[\"w45\",\"w92\",\"w154\",\"w185\",\"w300\",\"w500\",\"original\"],\"poster_sizes\":[\"w92\",\"w154\",\"w185\",\"w342\",\"w500\",\"w780\",\"original\"],\"profile_sizes\":[\"w45\",\"w185\",\"h632\",\"original\"],\"still_sizes\":[\"w92\",\"w185\",\"w300\",\"original\"]},\"change_keys\":[\"adult\",\"air_date\",\"also_known_as\",\"alternative_titles\",\"biography\",\"birthday\",\"budget\",\"cast\",\"certifications\",\"character_names\",\"created_by\",\"crew\",\"deathday\",\"episode\",\"episode_number\",\"episode_run_time\",\"freebase_id\",\"freebase_mid\",\"general\",\"genres\",\"guest_stars\",\"homepage\",\"images\",\"imdb_id\",\"languages\",\"name\",\"network\",\"origin_country\",\"original_name\",\"original_title\",\"overview\",\"parts\",\"place_of_birth\",\"plot_keywords\",\"production_code\",\"production_companies\",\"production_countries\",\"releases\",\"revenue\",\"runtime\",\"season\",\"season_number\",\"season_regular\",\"spoken_languages\",\"status\",\"tagline\",\"title\",\"translations\",\"tvdb_id\",\"tvrage_id\",\"type\",\"video\",\"videos\"]}";
    static String ImagesBaseUrl; // to do read from file
    static String ImageSize;
    static String RealImagesBaseUrl;
    ArrayList<MovieData> mMovieDatasList;
    public MovieAdapter(Context context, int resource, int textViewResourceId, List<MovieData> objects) {
        super(context, resource, textViewResourceId, objects);

        mMovieDatasList = (ArrayList<MovieData>) objects;

        try {
            extractDataFromConfiguration();
        } catch (JSONException e) {
            Log.e(LOG_TAG, "fail to get TheMovieDB's configuration");
        }
    }

    @Override
    public void clear() {
        mMovieDatasList.clear();
        super.clear();
    }

    @Override
    public void addAll(Collection<? extends MovieData> collection) {
        mMovieDatasList.addAll(collection);
        super.addAll(collection);
    }

    void extractDataFromConfiguration() throws JSONException {
        final String TMDB_CONFIG_IMAGES = "images";
        final String TMDB_CONFIG_IMAGES_BASE_URL = "base_url";
        final String TMDB_CONFIG_IMAGES_POSTER_SIZE = "logo_sizes";
        JSONObject configJson = new JSONObject(TheMovieDBConfigFakeJsonStr);
        JSONObject configJsonImage = configJson.getJSONObject(TMDB_CONFIG_IMAGES);
        ImagesBaseUrl = configJsonImage.getString(TMDB_CONFIG_IMAGES_BASE_URL);
        Log.v(LOG_TAG, "images' base url "+ ImagesBaseUrl);
        ImageSize = "w500";
        // example image url
        // https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
        RealImagesBaseUrl = ImagesBaseUrl+ImageSize;
    }

    String buildImageUrl(String iPosterPath){
        // example image url
        // https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
        String url = RealImagesBaseUrl + iPosterPath;
        Log.v(LOG_TAG, url);
        return url;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variagle
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_movie, null);
        }

        MovieData item = mMovieDatasList.get(position);
        if (item != null){

            TextView tv = (TextView) v.findViewById(R.id.image_title);
            tv.setText(item.mTitle);
            String imageUrl = buildImageUrl(item.mPoster_path);
            ImageView iv = (ImageView)v.findViewById(R.id.posterImage);

            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(iv);

        }

        return v;
    }

    @Override
    public int getCount() {
        if(mMovieDatasList == null)
            return 0;
        else
            return mMovieDatasList.size();
    }
}
