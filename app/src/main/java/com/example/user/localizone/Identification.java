package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Identification extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification);
        final Button mapButton = (Button) findViewById(R.id.identification);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText token = (EditText) findViewById(R.id.token);
               identification();
            }
        });

    }


    public void identification(){
        EditText inscrMail = (EditText) findViewById(R.id.identMail);
        EditText inscrPassword = (EditText) findViewById(R.id.identPassword);
        String res = (HttpRequest.sendRequest(getApplicationContext(), "identificationParent/" + inscrMail.getText().toString() + "/" + inscrPassword.getText().toString()).toString());
        if(!res.equals("UNAUTHORIZED")) {
            Intent intent = new Intent(Identification.this, MapActivityParent.class);
            intent.putExtra("token", res);
            startActivity(intent);
        }
    }
}
