package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewEnfant extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewenfant);

        final Button mapButton = (Button) findViewById(R.id.accessMap);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewEnfant.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }


}
