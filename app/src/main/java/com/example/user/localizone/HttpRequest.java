package com.example.user.localizone;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Romain on 17/11/2015.
 */
public class HttpRequest {

    private static String IP_PC="172.16.231.205";

    public static StringBuilder sendRequest(Context context,String urlLast){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        int duration = Toast.LENGTH_SHORT;
        StringBuilder total = new StringBuilder();
        URL url = null;
        try {
            //url = new URL("http://192.168.0.38:8080/SpringMVC/createNotifications/HJBUIB688G8G8/1.8877/8.79988/");
            url = new URL("http://"+IP_PC+":8080/SpringMVC/"+urlLast);

        } catch (MalformedURLException e) {
            Toast.makeText(context, "MalformedURLException", duration).show();
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast.makeText(context, "HttpURLConnection", duration).show();
            e.printStackTrace();
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            Toast.makeText(context, total, duration).show();
        } catch (IOException e) {
            Toast.makeText(context, "server not found", duration).show();
            e.printStackTrace();
        }

        urlConnection.disconnect();

        return total;
    }


}
