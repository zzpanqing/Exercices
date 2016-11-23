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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ShareActionProvider mSharedActionProvider;

    public DetailFragment() {
        // Required empty public constructor
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
        inflater.inflate(R.menu.detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mSharedActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateShareIntent() {
        if(mSharedActionProvider != null){
            TextView tv = (TextView) getView().findViewById(R.id.detail_text);
            String tvString = tv.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, tvString);
            mSharedActionProvider.setShareIntent(intent);
        }
    }

}
