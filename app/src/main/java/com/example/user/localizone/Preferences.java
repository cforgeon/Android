package com.example.user.localizone;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Romain on 08/12/2015.
 */


public class Preferences {

    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public static void recordValue(String key, String value,Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getRecord(String key,Context context) {
        SharedPreferences settings = context.getSharedPreferences(MyPREFERENCES, 0);
        String StringToReturn = settings.getString(key, "");
        return StringToReturn;
    }
}
