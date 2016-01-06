package com.example.user.localizone;

/**
 * Created by Romain on 21/11/2015.
 */

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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


public class MapActivityParent extends AppCompatActivity {

    private TextView locationText;
    private TextView addressText;
    private TextView checkzone;
    private GoogleMap map;
    private TextView tokenView;
    ListView listView;
    JSONArray the_json_array_notif = null;
    JSONArray the_json_array_area = null;
    Boolean InZone;
    AsyncTask counterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapparent);

        final Button zoneButton = (Button) findViewById(R.id.ajout_zone);
        zoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.15:8080/SpringMVC/home"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        ArrayList<String[]> arrayNotif = notification(token);
        tokenView = (TextView) findViewById(R.id.viewtoken);
        tokenView.setText(token);

        locationText = (TextView) findViewById(R.id.location);
        checkzone = (TextView) findViewById(R.id.checkzone);

        //replace GOOGLE MAP fragment in this Activity
        replaceMapFragment();
        displayAreaMap(token);

        listView = (ListView) findViewById(R.id.listView);

        List<Notification> Notif = genererNotifications(arrayNotif);

        NotificationAdapter adapter = new NotificationAdapter(MapActivityParent.this, Notif);
        listView.setAdapter(adapter);
    }

    private List<Notification> genererNotifications(ArrayList<String[]> arrayNotif) {
        List<Notification> Notifications = new ArrayList<Notification>();
        for (int i = 0; i < arrayNotif.size(); i++) {
            Notifications.add(new Notification(arrayNotif.get(i)[0], arrayNotif.get(i)[1], arrayNotif.get(i)[2]));
        }
        return Notifications;
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
            for (int i = 0; i < size; i++) {
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
    }

    public void displayAreaMap(String token) {
        StringBuilder area = HttpRequest.sendRequest(getApplicationContext(), "getArea/"+token);
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

               // Log.d("Myapp", latitude.toString());

                Circle circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(distance)
                        .strokeColor(Color.RED)
                        .fillColor(0x40ff0000));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Toast.makeText(getApplicationContext(), area.toString(), Toast.LENGTH_LONG).show();
    }



}