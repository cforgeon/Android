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
    private boolean flag;


    public Inscription(){
        boolMail = false;
        boolPass = false;
        boolean flag = false;
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



        if (mail.getText().toString().matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$")) {
            flag = true;

           if(password.getText().toString().length()< 6)
                textError.setText("Mot de passe trop court: 6 caractères minimum");
            else{
                if(confMail.getText().toString().equals(mail.getText().toString()) && confMail.getText().toString()!="" && confMail.getText().toString()!=" ") {
                    boolMail = true;

                    if(confPassword.getText().toString().equals(password.getText().toString()) && confPassword.getText().toString()!="" && confPassword.getText().toString()!=" ") {
                        boolPass = true;
                    }
                    else{
                        textError.setText("Confirmation de mot de passe erronée");
                    }
                }
                else{
                    textError.setText("Confirmation de mail erronée");
                }
            }
        }
        else{
            textError.setText("Votre adresse mail n'est pas correcte");
        }

        if(boolMail == true && boolPass == true ){
            String res = (HttpRequest.sendRequest(getApplicationContext(), "createUser/" + mail.getText().toString() + "/" + password.getText().toString()).toString());
            boolMail = false;
            boolPass = false;
            if(res.equals("adresse email already exist")){
                textError.setText("Adresse mail dejà existante");
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




