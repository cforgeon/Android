package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewEnfant extends AppCompatActivity {
    private EditText mToken;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewenfant);

        final Button mapButton = (Button) findViewById(R.id.accessMap);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText token = (EditText) findViewById(R.id.token);
                //HttpRequest.sendRequest(getApplicationContext(),"identificationChildren/"+token.getText().toString());
                Intent intent = new Intent(ViewEnfant.this, MapActivity.class);
                startActivity(intent);
            }
        });

        final Button tokenButton = (Button) findViewById(R.id.affiche_token);
        tokenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewEnfant.this, MapActivity.class);
                startActivity(intent);
            }
        });

        mToken = (EditText) findViewById(R.id.token);

        PreferenceManager.getInstance(this).saveToken(mToken.getText().toString());

    }




}