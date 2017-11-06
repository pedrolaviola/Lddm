package com.example.otto.trabalhopratico2;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
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

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.R.attr.data;

public class pdf_list extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;
    String subjectname,nomeSalvar;
    private static final int PDF_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);

        Bundle getInfo = getIntent().getExtras();
        subjectname = getInfo.getString("nomeMateria");
        nomeSalvar = subjectname+"pdf's.txt";

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.infoView, itemList);
        ListView listV = (ListView) findViewById(R.id.listPdf);
        listV.setAdapter(adapter);

        update();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addPdf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, PDF_CODE);

            }
        });

        FloatingActionButton del = (FloatingActionButton) findViewById(R.id.deletePdf);
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
                Intent mudarPg = new Intent(getApplicationContext(),OpenPdf.class);
                mudarPg.putExtra("caminho",itemList.get(i));
                startActivity(mudarPg);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PDF_CODE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            String uristring  = uri.toString();
            try {
                if (notInMenu(uristring)){
                    itemList.add(uristring);
                    addInFile(uristring);
                    adapter.notifyDataSetChanged();
                    Toast toast = Toast.makeText(getApplicationContext(), "Pdf adicionado com sucesso!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Erro! Pdf ja adicionado!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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



