package com.example.otto.trabalhopratico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView txtNome, txtEmail, txtNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button nextPg = (Button) findViewById(R.id.buttonTeste);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNome = (TextView) findViewById(R.id.nomeOrga);
        txtEmail = (TextView) findViewById(R.id.emailOrga);
        txtNumero = (TextView) findViewById(R.id.telOrga);

        String orgName = getIntent().getStringExtra("nomeOrga");
        String orgEmail = getIntent().getStringExtra("emailOrga");
        String telOrg = getIntent().getStringExtra("telOrga");

        txtNome.setText(orgName);
        txtEmail.setText(orgEmail);
        txtNumero.setText(telOrg);

        nextPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mudarPg = new Intent(getApplicationContext(), listaConvidados.class);
                mudarPg.putExtra("nomeOrga", txtNome.getText().toString());
                mudarPg.putExtra("emailOrga", txtEmail.getText().toString());
                mudarPg.putExtra("telOrga", txtNumero.getText().toString());
                startActivity(mudarPg);
            }
        });
    }
}

