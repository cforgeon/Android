package com.example.user.localizone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Inscription extends AppCompatActivity {
    private EditText mail;
    private EditText confMail;
    private EditText password;
    private EditText confPassword;
    private TextView textError;
    private boolean boolMail;
    private boolean boolPass;

    public Inscription(){
        boolMail = false;
        boolPass = false;
    }

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
            }
        });

    }

    public void inscription() {
        confMail = (EditText) findViewById(R.id.inscrConfMail);
        confPassword = (EditText) findViewById(R.id.inscrConfpassword);
        mail = (EditText) findViewById(R.id.inscrMail);
        password = (EditText) findViewById(R.id.inscrPassword);
        textError = (TextView) findViewById(R.id.textErrorConf);

        if(password.getText().toString().length()< 6)
            textError.setText("password too short: 6 character minimum");
        else{
            if(confMail.getText().toString().equals(mail.getText().toString()) && confMail.getText().toString()!="" && confMail.getText().toString()!=" ") {
                boolMail = true;

                if(confPassword.getText().toString().equals(password.getText().toString()) && confPassword.getText().toString()!="" && confPassword.getText().toString()!=" ") {
                    boolPass = true;
                }
                else{
                    textError.setText("Error password confirmation");
                }
            }
            else{
                textError.setText("Error mail confirmation");
            }
        }

        if(boolMail == true && boolPass == true ){
            String res = (HttpRequest.sendRequest(getApplicationContext(), "createUser/" + mail.getText().toString() + "/" + password.getText().toString()).toString());
            boolMail = false;
            boolPass = false;
            if(res.equals("adresse email already exist")){
                textError.setText("Error adresse email already exist");
            }
            else{
                if (!res.isEmpty()) {
                    Intent intent = new Intent(Inscription.this, MapActivityParent.class);
                    startActivity(intent);
                }
            }
        }
    }
}




