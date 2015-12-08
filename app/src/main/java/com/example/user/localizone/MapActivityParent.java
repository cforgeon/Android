package com.example.user.localizone;

/**
 * Created by Romain on 21/11/2015.
 */

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
    ListView listView;
    JSONArray the_json_array_area=null;
    Boolean InZone;
    AsyncTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapparent);

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
      //  map.setOnMyLocationChangeListener(myLocationChangeListener());
    }

/*    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                double longitudeCurrent = location.getLongitude();
                double latitudeCurrent = location.getLatitude();

                //Marker marker;
                //marker = map.addMarker(new MarkerOptions().position(loc));
                //map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                locationText.setText("You are at [" + longitudeCurrent + " ; " + latitudeCurrent + " ]");

                ////////////////////////////
                // functioncheck in zone
                if(the_json_array_area!=null){
                    InZone=checkZone(latitudeCurrent,longitudeCurrent);
                }
                //si l'enfant n'est pas dans la zone, on commence le compteur des 30s


                    if(!InZone && counterTask.getStatus() == AsyncTask.Status.RUNNING){
                       // rien
                    }else if(!InZone && counterTask.getStatus() != AsyncTask.Status.RUNNING) {
                        counterTask = new Counter(MapActivityParent.this).execute();
                    }else if(InZone && counterTask.getStatus() == AsyncTask.Status.RUNNING){
                        counterTask.cancel(true);
                    }

                //get current address by invoke an AsyncTask object
                //new GetAdressTask(MapActivity.this).execute(String.valueOf(latitudeCurrent), String.valueOf(longitudeCurrent));
            }
        };
    }*/

   /* public void callBackDataFromAsyncTask(String address) {
        addressText.setText(address);
    }*/

    public void displayAreaMap(){
        StringBuilder area =HttpRequest.sendRequest(getApplicationContext(), "getArea/HJBUIB688G8G8");
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(area.toString());
            the_json_array_area = jsonObject.getJSONArray("area");
            int size = the_json_array_area.length();
            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = the_json_array_area.getJSONObject(i);
                //Blah blah blah...
                arrays.add(another_json_object);
                Double latitude= another_json_object.getDouble("latitude");
                Double longitude= another_json_object.getDouble("longitude");
                Integer distance= another_json_object.getInt("distance");

                Log.d("Myapp", distance.toString());

                Circle circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(latitude,longitude))
                        .radius(distance)
                        .strokeColor(Color.RED)
                        .fillColor(0x40ff0000));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(getApplicationContext(), area.toString(), Toast.LENGTH_LONG).show();
    }


   /* public Boolean checkZone(double latitudeCurrent, double longitudeCurrent){
        Boolean checkinzone=false;
        int size_array =the_json_array_area.length();
        if(size_array>0){
            Log.d("TEST IF", " ok ko ok ok");
            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
            for (int i = 0; i < size_array; i++) {
                //Log.d(" i ; ",String.valueOf(i));
                JSONObject another_json_object = null;
                try {
                    another_json_object = the_json_array_area.getJSONObject(i);
                    //Blah blah blah...
                    arrays.add(another_json_object);
                    double latitude2= another_json_object.getDouble("latitude");
                    double longitude2= another_json_object.getDouble("longitude");
                    Integer distance2= another_json_object.getInt("distance");

                    double distancecalcule=Distance.distance(latitude2, longitude2, latitudeCurrent, longitudeCurrent, "metre");

                    //Log.d(" latitude2 ; ",String.valueOf(latitude2));
                    //Log.d(" longitude2 ; ",String.valueOf(longitude2));
                    //Log.d(" distance2 ; ",String.valueOf(distance2));

                    //Log.d(" distancecalcule ; ",String.valueOf(i)+" : "+String.valueOf(distancecalcule));
                    if(distancecalcule<distance2){
                        checkinzone=true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(checkinzone){
                Log.d(" functioncheck" , "dans la zone");
                checkzone.setText("You are good");
            }else{
                Log.d(" functioncheck" , " pas  dans la zone");
                checkzone.setText("You are not good mec ! Bouge de la");
            }
        }
        return checkinzone;
    }*/


}