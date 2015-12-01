package com.example.user.localizone;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import javax.xml.transform.Result;


public class Counter extends AsyncTask<Void, Void, String> {

    private MapActivity activity;

    public Counter(MapActivity activity) {
        super();
        this.activity = activity;
    }




    @Override
    protected String doInBackground(Void... params) {

            for (int i = 0; i < 30; i++) {
                try {
                    Thread.sleep(1000);
                    Log.d("thread count : ", String.valueOf(i));
                    if (isCancelled()) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    /**
     * When the task finishes, onPostExecute() call back data to Activity UI and displays the address.
     * @param address
     */
    @Override
    protected void onPostExecute(String address) {
        // Call back Data and Display the current address in the UI
        Log.d("thread count : ", "on envoie l'alerte !! ! ");
    }


    @Override
    protected void onCancelled(String result) {
        // Runs on UI thread after cancel() is invoked
        // and doInBackground() has finished/returned
    }
}