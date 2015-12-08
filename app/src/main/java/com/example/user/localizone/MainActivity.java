package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);




            final Button enfantButton = (Button) findViewById(R.id.enfant);
            enfantButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(Preferences.getRecord("token",getApplicationContext())=="") {
                    Intent intent = new Intent(MainActivity.this, ViewEnfant.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);

                }
            }

        });

            final Button parentButton = (Button) findViewById(R.id.parent);
            parentButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ViewParent.class);
                    startActivity(intent);
                }
            });

            };

        }




