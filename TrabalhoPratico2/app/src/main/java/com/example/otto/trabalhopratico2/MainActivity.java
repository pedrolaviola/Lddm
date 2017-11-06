package com.example.otto.trabalhopratico2;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    //ArrayList<String> subjects = new ArrayList<>(Arrays.asList("LDDM", "Grafos", "AED 2"));
    //LinkedList<String> anotherSubjects = new LinkedList<>(Arrays.asList("Banco de Dados", "Calculo I", "Cálculo II", "Cálculo III", "Algebra Linear", "Matemática Discreta", "Estatística", "AED 1", "AED 3", "LP", "PAA", "IA", "Compiladores", "PID", "Comp. Paralela", "Comp. Gráfica", "Otimização"));
    Menu menu = null;
    SubMenu SubMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        botao();
        iniciarDrawer(toolbar);
        iniciarNavMenu();
        //deleteFile();
    }

    public void writeFile() {

        try {
            FileOutputStream fos = openFileOutput("materias.txt", MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(fos);

            for (int i = 0; i < SubMenu.size(); i++) {
                outputFile.write(SubMenu.getItem(i).getTitle().toString()+ "\n");
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
            FileOutputStream file = openFileOutput("materias.txt",MODE_APPEND);
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
        File file = getApplicationContext().getFileStreamPath("materias.txt");
        String lineFromFile;
        if(file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("materias.txt")));
                lineFromFile = reader.readLine();
                while (lineFromFile != null) {
                    SubMenu.add(lineFromFile).setOnMenuItemClickListener(onMenuItemClick("Materias"));
                    lineFromFile = reader.readLine();
                }
            }
            catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try {
                FileOutputStream fos = openFileOutput("materias.txt", MODE_APPEND);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                outputStreamWriter.write("LDDM\n");
                outputStreamWriter.write("Grafos\n");
                outputStreamWriter.write("AED 2\n");
                outputStreamWriter.flush();
                outputStreamWriter.close();
                SubMenu.add("LDDM").setOnMenuItemClickListener(onMenuItemClick("Materias"));
                SubMenu.add("Grafos").setOnMenuItemClickListener(onMenuItemClick("Materias"));
                SubMenu.add("AED 2").setOnMenuItemClickListener(onMenuItemClick("Materias"));

            } catch (Exception e ) {
                e.printStackTrace();
            }

        }
    }

    public void onStop () {
        super.onStop();
        writeFile();
    }

    public void deleteFile () {
        File file = new File(getFilesDir(),"materias.txt");
        if (file.exists()) {
            deleteFile("materias.txt");
        }
    }

    public boolean notInMenu (String newInput) {
        boolean resp = true;
        if (SubMenu.size() == 0) {
            resp = true;
        }
        else {
            for (int i = 0; i < SubMenu.size(); i ++) {
                if ( newInput.equals(SubMenu.getItem(i).getTitle().toString())) {
                    resp = false;
                }
            }
        }
        return resp;
    }

    private void botao(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_info, null);
                final EditText materia = (EditText) mView.findViewById(R.id.infoMat);
                Button addBtn = (Button) mView.findViewById(R.id.btnAddMat);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!materia.getText().toString().isEmpty()){

                            String subject = materia.getText().toString();
                            if (notInMenu(subject)){
                                addInFile(subject);
                                SubMenu.add(subject).setOnMenuItemClickListener(onMenuItemClick("Materias"));
                                Toast toast = Toast.makeText(getApplicationContext(), "Materia adicionada com sucesso!", Toast.LENGTH_SHORT);
                                toast.show();
                                dialog.dismiss();
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Erro! Materia ja adicionada!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else {
                            materia.setError("Insira uma materia!");
                            Toast toast = Toast.makeText(getApplicationContext(), "Erro! Campo vazio!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });


            }
        });
    }

    private void iniciarDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void iniciarNavMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        SubMenu = menu.addSubMenu("Materias");
        update();
        /*
        for (String subject : subjects) {
            SubMenu.add(subject).setOnMenuItemClickListener(onMenuItemClick("Materias"));
        }
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteMenus) {
            deleteFile();
            update();
            finish();
            startActivity(getIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public MenuItem.OnMenuItemClickListener onMenuItemClick(final String fileType) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();

                switch (fileType) {
                    case "Materias":
                        PdfFragment pdfFragment = new PdfFragment();

                        args.putString("subjectName", item.getTitle().toString());
                        //args.putString("fileType", fileType);
                        pdfFragment.setArguments(args);
                        ft.replace(R.id.placeholder_fragment, pdfFragment);
                        break;
                }

                ft.commit();
                return false;
            }
        };
    }


}
