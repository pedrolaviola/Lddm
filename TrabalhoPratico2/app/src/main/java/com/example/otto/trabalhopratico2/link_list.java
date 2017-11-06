package com.example.otto.trabalhopratico2;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class link_list extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;
    String subjectname;
    String nomeSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_list);

        Bundle getInfo = getIntent().getExtras();
        subjectname = getInfo.getString("nomeMateria");
        nomeSalvar = subjectname+"Links.txt";

        itemList=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.list_item,R.id.infoView,itemList);
        final ListView listV=(ListView)findViewById(R.id.list);
        listV.setAdapter(adapter);

        update();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addLink);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(link_list.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_link, null);
                final EditText materia = (EditText) mView.findViewById(R.id.infoLink);
                Button addBtn = (Button) mView.findViewById(R.id.btnAddLink);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!materia.getText().toString().isEmpty()){
                            if (notInMenu(materia.getText().toString())){
                                itemList.add(materia.getText().toString());
                                adapter.notifyDataSetChanged();
                                addInFile(materia.getText().toString());
                                Toast toast = Toast.makeText(getApplicationContext(), "Link adicionado com sucesso!", Toast.LENGTH_SHORT);
                                toast.show();
                                dialog.dismiss();
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Erro! Link ja adicionado!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else {
                            materia.setError("Insira um Link!");
                            Toast toast = Toast.makeText(getApplicationContext(), "Erro! Campo vazio!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

        FloatingActionButton del = (FloatingActionButton) findViewById(R.id.deleteLinks);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
                update();
                finish();
                startActivity(getIntent());
            }
        });

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = itemList.get(i).toString();
                if (checkhttps(url)){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                else{
                    String url2 = "https://"+url;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url2));
                    startActivity(intent);
                }

            }
        });
    }

    private boolean checkhttps(String url){
        boolean resp = false;
        if (url.charAt(0) == 'h'){
            if (url.charAt(1) == 't'){
                if (url.charAt(2) == 't'){
                    if (url.charAt(3) == 'p'){
                        if (url.charAt(4) == 's'){
                            if (url.charAt(5) == ':'){
                                if (url.charAt(6) == '/'){
                                    if (url.charAt(7) == '/'){
                                        resp = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resp;
    }

    public void writeFile() {

        try {
            FileOutputStream fos = openFileOutput(nomeSalvar, MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(fos);

            for (int i = 0; i < itemList.size(); i++) {
                outputFile.write(itemList.get(i).toString()+ "\n");
            }
            outputFile.flush();
            outputFile.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addInFile(String newInput){
        try{
            FileOutputStream file = openFileOutput(nomeSalvar,MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
            outputStreamWriter.write(newInput + "\n");
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
        }
    }


    public void update(){
        File file = getApplicationContext().getFileStreamPath(nomeSalvar);
        String lineFromFile;
        if(file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(nomeSalvar)));
                lineFromFile = reader.readLine();
                while (lineFromFile != null) {
                    itemList.add(lineFromFile);
                    adapter.notifyDataSetChanged();
                    lineFromFile = reader.readLine();
                }
            }
            catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "File does not exist!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop () {
        super.onStop();
        writeFile();
    }

    public void deleteFile () {
        File file = new File(getFilesDir(),nomeSalvar);
        if (file.exists()) {
            deleteFile(nomeSalvar);
        }
    }

    public boolean notInMenu (String newInput) {
        boolean resp = true;
        if (itemList.size() == 0) {
            resp = true;
        }
        else {
            for (int i = 0; i < itemList.size(); i ++) {
                if ( newInput.equals(itemList.get(i).toString())) {
                    resp = false;
                }
            }
        }
        return resp;
    }
}
