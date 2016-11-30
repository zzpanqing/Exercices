package com.example.qing.mysecondmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    GoogleMap mGoogleMap;
    @Override
    public void onMapReady(GoogleMap iGoogleMap) {
        mGoogleMap = iGoogleMap;
        LatLng paris = new LatLng(48.8769409, 2.3314306);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris,17));

    }


    public void ClickMap(View view) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void ClickSatellite(View view) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void ClickHybrid(View view) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
