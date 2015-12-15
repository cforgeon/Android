package com.example.user.localizone;

/**
 * Created by Romain on 21/11/2015.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MapActivityParent extends AppCompatActivity {

    private TextView locationText;
    private TextView addressText;
    private TextView checkzone;
    private GoogleMap map;
    private TextView tokenView;
    ListView listView;
    JSONArray the_json_array_area = null;
    Boolean InZone;
    AsyncTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapparent);
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        notification();
        tokenView = (TextView) findViewById(R.id.viewtoken);
        tokenView.setText(token);

        locationText = (TextView) findViewById(R.id.location);
        //addressText = (TextView) findViewById(R.id.address);
        checkzone = (TextView) findViewById(R.id.checkzone);

        // counterTask=new Counter(MapActivityParent.this);

        //replace GOOGLE MAP fragment in this Activity
        replaceMapFragment();
        displayAreaMap();
        listView = (ListView) findViewById(R.id.list);
        String[] alertes = new String[]{"Alerte 1", "Alerte 2", "Alerte 3", "Alerte 4"};

        // Définition de l'adapter
        // Premier Paramètre - Context
        // Second Paramètre - le Layout pour les Items de la Liste
        // Troisième Paramètre - l'ID du TextView du Layout des Items
        // Quatrième Paramètre - le Tableau de Données
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapActivityParent.this, android.R.layout.simple_list_item_1, android.R.id.text1, alertes);

        // on assigne l'adapter à notre liste
        listView.setAdapter(adapter);
    }

    private void notification(){
     //   String notif = (HttpRequest.sendRequest(getApplicationContext(), "getNotifications"/"HJBUIB688G8G8".toString()).toString());
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
    }

    public void displayAreaMap() {
        StringBuilder area = HttpRequest.sendRequest(getApplicationContext(), "getArea/HJBUIB688G8G8");
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(area.toString());
            the_json_array_area = jsonObject.getJSONArray("area");
            int size = the_json_array_area.length();
            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = the_json_array_area.getJSONObject(i);
                //Blah blah blah...
                arrays.add(another_json_object);
                Double latitude = another_json_object.getDouble("latitude");
                Double longitude = another_json_object.getDouble("longitude");
                Integer distance = another_json_object.getInt("distance");

                Log.d("Myapp", distance.toString());

                Circle circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(distance)
                        .strokeColor(Color.RED)
                        .fillColor(0x40ff0000));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(getApplicationContext(), area.toString(), Toast.LENGTH_LONG).show();
    }
}