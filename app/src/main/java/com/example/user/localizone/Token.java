package com.example.user.localizone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class Token extends AppCompatActivity {
    private String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.token);

        SharedPreferences preferences = PreferenceManager.getSharedPreferences(this);
        // Consulter la préférence
        SharedPreferences sp = getSharedPreferences(token, 0);
        boolean silent = sp.getBoolean("silentMode", false);
    }
}