package com.example.otto.trabalhopratico1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView txtnome,txtemail,txtnumero;
    private String orgName,orgEmail,telorg;
    private Button nextPg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nextPg = (Button)findViewById(R.id.buttonTeste);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtnome = (TextView)findViewById(R.id.nomeOrga);
        txtemail = (TextView)findViewById(R.id.emailOrga);
        txtnumero = (TextView)findViewById(R.id.telOrga);

        Bundle getInfo = getIntent().getExtras();

        orgName = getInfo.getString("nomeOrga");
        orgEmail = getInfo.getString("emailOrga");
        telorg = getInfo.getString("telOrga");

        txtnome.setText(orgName);
        txtemail.setText(orgEmail);
        txtnumero.setText(telorg);

        nextPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mudarPg = new Intent(getApplicationContext(),listaConvidados.class);
                mudarPg.putExtra("nomeOrga",txtnome.getText().toString());
                mudarPg.putExtra("emailOrga",txtemail.getText().toString());
                mudarPg.putExtra("telOrga",txtnumero.getText().toString());
                startActivity(mudarPg);
            }
        });
    }
}

