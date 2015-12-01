package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Inscription extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        final Button mapButton = (Button) findViewById(R.id.inscription);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText token = (EditText) findViewById(R.id.token);
                inscription();
                Intent intent = new Intent(Inscription.this, Inscription.class);
                startActivity(intent);
            }
        });

    }


    public void inscription(){
        EditText inscrMail = (EditText) findViewById(R.id.inscrMail);
        EditText inscrPassword = (EditText) findViewById(R.id.inscrPassword);
      //  Log.d("toto", inscrMail.getText().toString());
        HttpRequest.sendRequest(getApplicationContext(), "createUser/" + inscrMail.getText().toString() + "/" + inscrPassword.getText().toString());
    }
}
