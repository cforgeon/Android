package com.example.user.localizone;

/**
 * Created by Romain on 21/11/2015.
 */

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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
import java.util.List;


public class MapActivity extends AppCompatActivity {

    private TextView locationText;
    private TextView addressText;
    private TextView checkzone;
    private GoogleMap map;
    private ListView listViewChild;
    private TextView tokenView;
    private JSONArray the_json_array_area=null;
    private JSONArray the_json_array_notif = null;
    private Boolean InZone;
    private AsyncTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String token = Preferences.getRecord("token", getApplicationContext());
        ArrayList<String[]> arrayNotif = notification(token);

        //Toast.makeText(getApplicationContext(),Preferences.getRecord("token",getApplicationContext()), Toast.LENGTH_LONG).show();

        locationText = (TextView) findViewById(R.id.location);
        checkzone = (TextView) findViewById(R.id.checkzone);

        counterTask=new Counter(MapActivity.this);

        //replace GOOGLE MAP fragment in this Activity
        replaceMapFragment();
        displayAreaMap();

        listViewChild = (ListView) findViewById(R.id.list);
        List<Notification> notifArrayList = genererNotifications(arrayNotif);
        NotificationAdapter adapter = new NotificationAdapter(MapActivity.this, notifArrayList);
        listViewChild.setAdapter(adapter);
    }

    private List<Notification> genererNotifications(ArrayList<String[]> arrayNotif) {
        List<Notification> notifications = new ArrayList<Notification>();
        for (int i = 0; i <= arrayNotif.size()-1; i++) {
            notifications.add(new Notification(arrayNotif.get(i)[0], arrayNotif.get(i)[1], arrayNotif.get(i)[2]));
        }
        return notifications;
    }

    private ArrayList<String[]> notification(String token) {
        StringBuilder notification = HttpRequest.sendRequest(getApplicationContext(), "getNotifications/"+token);
        JSONObject jsonObject = null;
        ArrayList<String[]> listNotif = new ArrayList<String[]>();

        String latitude = "";
        String longitude = "";
        String date = "";

        try {
            jsonObject = new JSONObject(notification.toString());
            System.out.println(jsonObject);
            the_json_array_notif = jsonObject.getJSONArray("notification");
            int size = the_json_array_notif.length();
            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
            for (int i = size-4; i < size; i++) {
                JSONObject another_json_object = the_json_array_notif.getJSONObject(i);
                String[] tabNotif = new String[3];

                arrays.add(another_json_object);
                latitude = String.valueOf(another_json_object.getDouble("latitude"));
                longitude = String.valueOf(another_json_object.getDouble("longitude"));
                date = String.valueOf(another_json_object.getString("date"));

                tabNotif[0] = latitude;
                tabNotif[1] = longitude;
                tabNotif[2] = date;

                listNotif.add(tabNotif);
            }
            return listNotif;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listNotif;
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
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
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

                Preferences.recordValue("latitude", String.valueOf(latitudeCurrent), getApplicationContext());
                Preferences.recordValue("longitude", String.valueOf(longitudeCurrent), getApplicationContext());

                ////////////////////////////
                // functioncheck in zone
                if(the_json_array_area!=null){
                    InZone=checkZone(latitudeCurrent,longitudeCurrent);
                }
                //si l'enfant n'est pas dans la zone, on commence le compteur des 30s


                    if(!InZone && counterTask.getStatus() == AsyncTask.Status.RUNNING){
                        //si il n'est pas dans la zone et que le compteur tourne
                    }else if(!InZone && counterTask.getStatus() != AsyncTask.Status.RUNNING) {
                        //si il n'est pas dans la zone et que le compteur ne tourne pas
                        counterTask = new Counter(MapActivity.this).execute();
                    }else if(InZone && counterTask.getStatus() == AsyncTask.Status.RUNNING){
                        // cela signifie que la personne revient dans une zone
                        // on va doit envoyer une requete au serveur pour avertir la fin de sortie de zone
                        String token= Preferences.getRecord("token",getApplicationContext());
                        counterTask.cancel(true);
                        HttpRequest.sendRequest(null, "createNotifications/" + token + "/" + latitudeCurrent + "/" + longitudeCurrent + "/over");
                    }

                //get current address by invoke an AsyncTask object
                //new GetAdressTask(MapActivity.this).execute(String.valueOf(latitudeCurrent), String.valueOf(longitudeCurrent));
            }
        };
    }

   /* public void callBackDataFromAsyncTask(String address) {
        addressText.setText(address);
    }*/

    public void displayAreaMap(){
        StringBuilder area =HttpRequest.sendRequest(getApplicationContext(), "getArea/"+Preferences.getRecord("token",getApplicationContext()));
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

                //Log.d("Myapp", distance.toString());

                Circle circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(latitude,longitude))
                        .radius(distance)
                        .strokeColor(Color.RED)
                        .fillColor(0x40ff0000));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Toast.makeText(getApplicationContext(), area.toString(), Toast.LENGTH_LONG).show();
    }


    public Boolean checkZone(double latitudeCurrent, double longitudeCurrent){
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
    }


}