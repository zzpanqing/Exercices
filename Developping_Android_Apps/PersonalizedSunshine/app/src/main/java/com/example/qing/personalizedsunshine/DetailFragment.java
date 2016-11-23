package com.example.qing.personalizedsunshine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ShareActionProvider mSharedActionProvider;
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";

    public DetailFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String detailText = intent.getExtras().getString(Intent.EXTRA_TEXT);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tv = (TextView)root.findViewById(R.id.detail_text);
        tv.setText(detailText);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        // Get the provider and hold onto it to set / change the share intent
        mSharedActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        // attach an intent to this ShareActionProvider. You can update this at any time
        // like when the user selects a new piece of data they might like to share
        if(mSharedActionProvider != null)
            mSharedActionProvider.setShareIntent(createShareForecastIntent());

    }

    private Intent createShareForecastIntent() {
        Intent intent = null;
        if(mSharedActionProvider != null){
            TextView tv = (TextView) getView().findViewById(R.id.detail_text);
            String tvString = tv.getText().toString();
            intent = new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); // back icon return to this app
                                                                // not to the app containing the intent
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, tvString+FORECAST_SHARE_HASHTAG);
        }
        return intent;
    }

}
