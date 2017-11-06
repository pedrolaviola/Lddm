/*
*Otto Bittencourt
* LDDM
* 2/2017
 */
package com.example.otto.trabalhopratico1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class listaConvidados extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    EditText nameText,emailText,telText;
    ArrayList<String> itemList;
    private String orgName,orgEmail,telorg,horaInicio,horaFim,dataInicio,dataFim,nomeEvento;
    Button btAdd,addBoth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_convidados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle getInfo = getIntent().getExtras();

        orgName = getInfo.getString("nomeOrga");
        orgEmail = getInfo.getString("emailOrga");
        telorg = getInfo.getString("telOrga");
        horaInicio = getInfo.getString("horaInicio");
        horaFim = getInfo.getString("horaFim");
        dataFim = getInfo.getString("dataFim");
        dataInicio = getInfo.getString("dataInicio");
        nomeEvento = getInfo.getString("nomeEvento");

        nameText=(EditText)findViewById(R.id.nameInput);
        emailText=(EditText)findViewById(R.id.emailConvidado);
        telText=(EditText)findViewById(R.id.telefoneConvidado);
        btAdd=(Button)findViewById(R.id.btAdd);
        addBoth = (Button)findViewById(R.id.listaEAgenda);

        String organizador = "Organizador: "+orgName+"-"+telorg+"-"+orgEmail;

        String[] items={organizador};
        itemList=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<>(this,R.layout.list_item,R.id.infoView,itemList);
        ListView listV=(ListView)findViewById(R.id.list);
        listV.setAdapter(adapter);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList();
            }
        });

        addBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListAndAdd();
            }
        });
    }

    public void setList(){
        Context context = getApplicationContext();
        CharSequence text = "Campo Vazio!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        if (nameText.getText().length() == 0){
            nameText.setError("Ponha um nome!");
            toast.show();
        }
        else if(emailText.getText().length() == 0){
            emailText.setError("Informe um E-mail!");
            toast.show();
        }
        else if(telText.getText().length() == 0){
            telText.setError("Informe um telefone!");
            toast.show();
        }
        else {

            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String telefone = telText.getText().toString();
            String newItem = name+"-"+telefone+"-"+email;

            // add new item to arraylist
            itemList.add(newItem);
            // notify listview of data changed
            adapter.notifyDataSetChanged();

            nameText.setText("");
            emailText.setText("");
            telText.setText("");
        }
    }

    public void setListAndAdd(){
        Context context = getApplicationContext();
        CharSequence text = "Campo Vazio!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        if (nameText.getText().length() == 0){
            nameText.setError("Ponha um nome!");
            toast.show();
        }
        else if(emailText.getText().length() == 0){
            emailText.setError("Informe um E-mail!");
            toast.show();
        }
        else if(telText.getText().length() == 0){
            telText.setError("Informe um telefone!");
            toast.show();
        }
        else {

            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String telefone = telText.getText().toString();
            String newItem = name+"-"+telefone+"-"+email;

            // add new item to arraylist
            itemList.add(newItem);
            // notify listview of data changed
            adapter.notifyDataSetChanged();

            nameText.setText("");
            emailText.setText("");
            telText.setText("");

            Intent contato = new Intent(ContactsContract.Intents.Insert.ACTION);
            contato.setType(ContactsContract.RawContacts.CONTENT_TYPE);

            contato.putExtra(ContactsContract.Intents.Insert.NAME,name);
            contato.putExtra(ContactsContract.Intents.Insert.EMAIL,email);
            contato.putExtra(ContactsContract.Intents.Insert.PHONE,telefone);
            startActivity(contato);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
             case R.id.wpp:
                 String message = "Evento "+nomeEvento+" dia "+dataInicio+" as "+horaInicio+" horas, ate o dia "+dataFim+" as "+horaFim+" horas!";
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_SEND);
                 intent.putExtra(Intent.EXTRA_TEXT,message);
                 intent.setType("text/plain");
                 intent.setPackage("com.whatsapp");
                 startActivity(Intent.createChooser(intent,""));
                 return true;


            case R.id.linkedin:
                String messagelink = "Evento "+nomeEvento+" dia "+dataInicio+" as "+horaInicio+" horas, ate o dia "+dataFim+" as "+horaFim+" horas!";
                Intent intentLink = ShareCompat.IntentBuilder.from(listaConvidados.this)
                        .setType("text/plain")
                        .setText(messagelink)
                        .getIntent();
                intentLink.setPackage("com.linkedin.android");
                intentLink.setAction(Intent.ACTION_SEND);
                if (intentLink.resolveActivity(getPackageManager()) != null){
                    startActivity(intentLink);
                }
                return true;

         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compartilhar,menu);
        return true;
    }
}
