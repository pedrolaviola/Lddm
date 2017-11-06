/*
*Otto Bittencourt
* LDDM
* 2/2017
 */
package com.example.otto.trabalhopratico1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.provider.CalendarContract.Events;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Calendar mCurrentDate;
    private EditText dataInicio;
    private EditText dataFim;
    private EditText horaInicio;
    private EditText horaFim;
    private Button add,listaConvi;
    private EditText nome;
    private EditText local;
    private EditText descricao,nomeOrga,emailOrga,telOrga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataInicio = (EditText)findViewById(R.id.txtDateEntered);
        dataFim = (EditText)findViewById(R.id.txtEndDate);
        horaInicio = (EditText)findViewById(R.id.horaInicio);
        horaFim = (EditText)findViewById(R.id.horaFinne);
        add = (Button)findViewById(R.id.botaoAdd);
        nome = (EditText)findViewById(R.id.nomeEvento);
        local = (EditText)findViewById(R.id.localEvento);
        descricao = (EditText)findViewById(R.id.descEvento);
        nomeOrga = (EditText)findViewById(R.id.nomeOrga);
        emailOrga = (EditText)findViewById(R.id.emailOrga);
        telOrga = (EditText)findViewById(R.id.numeroOrga);
        listaConvi = (Button)findViewById(R.id.botaoConvi);


        dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentDate = Calendar.getInstance();
                int year = mCurrentDate.get(Calendar.YEAR);
                int month = mCurrentDate.get(Calendar.MONTH);
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        dataInicio.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);

                        mCurrentDate.set(selectedYear, selectedMonth, selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        dataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentDate = Calendar.getInstance();
                int year = mCurrentDate.get(Calendar.YEAR);
                int month = mCurrentDate.get(Calendar.MONTH);
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        dataFim.setText(selectedDay+"/"+selectedMonth+"/"+selectedYear);

                        mCurrentDate.set(selectedYear,selectedMonth,selectedDay);

                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        horaInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaInicio.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        horaFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        horaFim.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Campo Vazio!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);

                if (nome.getText().length() == 0){
                    nome.setError("Ponha um nome!");
                    toast.show();
                }
                else if(local.getText().length() == 0){
                    local.setError("Ponha um local!");
                    toast.show();
                }
                else if(descricao.getText().length() == 0){
                    descricao.setError("Ponha um descrição!");
                    toast.show();
                }
                else if(dataInicio.getText().length() == 0){
                    dataInicio.setError("Informe uma data de inicio!");
                    toast.show();
                }
                else if(horaInicio.getText().length() == 0){
                    horaInicio.setError("Informe um hora de inicio!");
                    toast.show();
                }
                else if(dataFim.getText().length() == 0){
                    dataFim.setError("Informe uma data de fim!");
                    toast.show();
                }
                else if(horaFim.getText().length() == 0){
                    horaFim.setError("Informe um hora de fim!");
                    toast.show();
                }
                else {
                    String name = nome.getText().toString();
                    String place = local.getText().toString();
                    String description = descricao.getText().toString();
                    set(name,place,description);
                }
            }
        });

        listaConvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Campo Vazio!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);


                //*************************************mudar isso dps de acabar o app****************************************

                if (nome.getText().length() == 0){
                    nome.setError("Ponha um nome!");
                    toast.show();
                }
                else if(local.getText().length() == 0){
                    local.setError("Ponha um local!");
                    toast.show();
                }
                else if(descricao.getText().length() == 0){
                    descricao.setError("Ponha um descrição!");
                    toast.show();
                }
                else if(dataInicio.getText().length() == 0){
                    dataInicio.setError("Informe uma data de inicio!");
                    toast.show();
                }
                else if(horaInicio.getText().length() == 0){
                    horaInicio.setError("Informe um hora de inicio!");
                    toast.show();
                }
                else if(dataFim.getText().length() == 0){
                    dataFim.setError("Informe uma data de fim!");
                    toast.show();
                }
                else if(horaFim.getText().length() == 0){
                    horaFim.setError("Informe um hora de fim!");
                    toast.show();
                }

                //************************************************************************************************

                else if (nomeOrga.getText().length() == 0){
                    nomeOrga.setError("Ponha um nome!");
                    toast.show();
                }
                else if(emailOrga.getText().length() == 0){
                    emailOrga.setError("Informe um E-mail!");
                    toast.show();
                }
                else if(telOrga.getText().length() == 0){
                    telOrga.setError("Informe um telefone!");
                    toast.show();
                }
                else {
                    Intent mudarPg = new Intent(getApplicationContext(),listaConvidados.class);
                    mudarPg.putExtra("nomeOrga",nomeOrga.getText().toString());
                    mudarPg.putExtra("emailOrga",emailOrga.getText().toString());
                    mudarPg.putExtra("telOrga",telOrga.getText().toString());
                    mudarPg.putExtra("nomeEvento",nome.getText().toString());
                    mudarPg.putExtra("horaInicio",horaInicio.getText().toString());
                    mudarPg.putExtra("horaFim",horaFim.getText().toString());
                    mudarPg.putExtra("dataInicio",dataInicio.getText().toString());
                    mudarPg.putExtra("dataFim",dataFim.getText().toString());
                    startActivity(mudarPg);
                }
            }
        });
    }

    public void set(String nome,String local,String desc){
        String[] startDate = dataInicio.getText().toString().split("/");
        String[] startTimeHour = horaInicio.getText().toString().split(":");
        String[] endDate = dataFim.getText().toString().split("/");
        String[] endTimeHour = horaFim.getText().toString().split(":");

        int startYear = Integer.parseInt(startDate[2]);
        int startMonth = Integer.parseInt(startDate[1]);
        int startDay = Integer.parseInt(startDate[0]);
        int startHour = Integer.parseInt(startTimeHour[0]);
        int startMin = Integer.parseInt(startTimeHour[1]);

        int endYear = Integer.parseInt(endDate[2]);
        int endMonth = Integer.parseInt(endDate[1]);
        int endDay = Integer.parseInt(endDate[0]);
        int endHour = Integer.parseInt(endTimeHour[0]);
        int endMin = Integer.parseInt(endTimeHour[1]);


        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(Events.CONTENT_URI);
        calIntent.putExtra(Events.TITLE, nome);
        calIntent.putExtra(Events.EVENT_LOCATION, local);
        calIntent.putExtra(Events.DESCRIPTION, desc);
        Calendar startTime = Calendar.getInstance();

        startTime.set(startYear,startMonth,startDay,startHour,startMin);

        Calendar endTime = Calendar.getInstance();

        endTime.set(endYear,endMonth,endDay,endHour,endMin);

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                endTime.getTimeInMillis());
        startActivity(calIntent);

    }
}
