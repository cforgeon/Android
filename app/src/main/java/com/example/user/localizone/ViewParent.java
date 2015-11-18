package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewParent extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewparent);

        final Button identButton = (Button) findViewById(R.id.ident);
       identButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p) {
                Intent intent = new Intent(ViewParent.this, Identification.class);
                startActivity(intent);
            }
        });

        final Button inscrButton = (Button) findViewById(R.id.inscr);
        inscrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p) {
                Intent intent = new Intent(ViewParent.this, Inscription.class);
                startActivity(intent);
            }
        });

    }

}
