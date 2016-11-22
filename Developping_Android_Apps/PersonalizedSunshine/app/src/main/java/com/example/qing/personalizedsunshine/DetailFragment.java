package com.example.qing.personalizedsunshine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


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

}
