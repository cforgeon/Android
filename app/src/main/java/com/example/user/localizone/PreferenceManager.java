package com.example.user.localizone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.EditText;


public class PreferenceManager extends PreferenceActivity {

    //private String token;
    public static EditText editTextToken;
    private SharedPreferences TokenPreference;
    private SharedPreferences TokenPrefEdit;
    private Boolean saveToken;



    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_map);

        editTextToken = (EditText) findViewById(R.id.token);
        TokenPreference = getSharedPreferences("TokenPref", MODE_PRIVATE);
        TokenPrefEdit = (SharedPreferences) TokenPreference.edit();

        saveToken = TokenPreference.getBoolean("saveToken", false);

        if (saveToken == true){
            editTextToken.setText(TokenPreference.getString("token",""));
        }


    }

    public static SharedPreferences getSharedPreferences(Token token) {
        return null;
    }
}
