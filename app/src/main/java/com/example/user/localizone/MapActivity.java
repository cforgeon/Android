package com.example.user.localizone;

/**
 * Created by Romain on 21/11/2015.
 */
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;



public class MapActivity extends AppCompatActivity {

    private TextView locationText;
    private TextView addressText;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        locationText = (TextView) findViewById(R.id.location);
        addressText = (TextView) findViewById(R.id.address);

        //replace GOOGLE MAP fragment in this Activity
        replaceMapFragment();
    }

    private void replaceMapFragment() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        // Enable Zoom
        map.getUiSettings().setZoomGesturesEnabled(true);

        //set Map TYPE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //enable Current location Button
        map.setMyLocationEnabled(true);

        //set "listener" for changing my location
        map.setOnMyLocationChangeListener(myLocationChangeListener());
        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(-33.87365, 151.20689))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                //Marker marker;
                //marker = map.addMarker(new MarkerOptions().position(loc));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                locationText.setText("You are at [" + longitude + " ; " + latitude + " ]");

                //get current address by invoke an AsyncTask object
                new GetAdressTask(MapActivity.this).execute(String.valueOf(latitude), String.valueOf(longitude));
            }
        };
    }

    public void callBackDataFromAsyncTask(String address) {
        addressText.setText(address);
    }

}