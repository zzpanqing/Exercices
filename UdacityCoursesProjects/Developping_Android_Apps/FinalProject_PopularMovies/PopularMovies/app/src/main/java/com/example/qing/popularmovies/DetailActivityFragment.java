package com.example.qing.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        String extraTitle = intent.getStringExtra(MovieAdapter.PARAM_MOVIE_TITLE);
        String extraImageUrl = intent.getStringExtra(MovieAdapter.PARAM_POSTER_URL);
        String extraPlotSynops = intent.getStringExtra(MovieAdapter.PARAM_OVERVIEW);
        double extraUserRating = intent.getDoubleExtra(MovieAdapter.PARAM_USER_RATING,0.f);
        String extraReleaseDate = intent.getStringExtra(MovieAdapter.PARAM_RELEASE_DATE);

        TextView tvTitle = (TextView) root.findViewById(R.id.Title);
        tvTitle.setText(extraTitle);

        ImageView IvPoster = (ImageView) root.findViewById(R.id.poster);
        Picasso.with(getContext())
                .load(extraImageUrl)
                .into(IvPoster);

        TextView tvPlotSynopsis = (TextView) root.findViewById(R.id.plotSynopsis);
        tvPlotSynopsis.setText(extraPlotSynops);

        TextView tvUserRating = (TextView) root.findViewById(R.id.userRating);
        tvUserRating.setText(Double.valueOf(extraUserRating).toString());

        TextView tvReleaseDate = (TextView) root.findViewById(R.id.releaseDate);
        tvReleaseDate.setText(extraReleaseDate);

        return root;
    }
}
