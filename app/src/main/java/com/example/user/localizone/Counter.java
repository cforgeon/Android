package com.example.user.localizone;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;


public class Counter extends AsyncTask<Void, Void, String> {

    private MapActivity activity;

    public Counter(MapActivity activity) {
        super();
        this.activity = activity;
    }




    @Override
    protected String doInBackground(Void... params) {

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String token= Preferences.getRecord("token",activity.getApplicationContext());
        String latitude= Preferences.getRecord("latitude", activity.getApplicationContext());
        String longitude= Preferences.getRecord("longitude", activity.getApplicationContext());

        StringBuilder area =HttpRequest.sendRequest(null, "createNotifications/"+token+"/"+latitude+"/"+longitude+"/processing");


        return null;
    }

    /**
     * When the task finishes, onPostExecute() call back data to Activity UI and displays the address.
     * @param address
     */
    @Override
    protected void onPostExecute(String address) {
        // Call back Data and Display the current address in the UI

    }


    @Override
    protected void onCancelled(String result) {
        // Runs on UI thread after cancel() is invoked
        // and doInBackground() has finished/returned
    }
}