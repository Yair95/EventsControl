package com.example.yairrovero.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AgregarEvento2Activity extends AppCompatActivity {

    private TimePicker pikTim;
    private Button btnHoraIni, btnHoraFin, btnContinuar;
    private TextView TeViHoraIni, TeViHoraFin;
    private String horaInicio, horaFin, minutoInicio, MinutoFin;
    private List<Calendar> dates = new ArrayList<Calendar>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento2);

        List<Calendar> diasss = (List<Calendar>) getIntent().getSerializableExtra("diasss");
        dates = diasss;


        pikTim = (TimePicker) findViewById(R.id.timePickerHoraAddEvent2);
        btnHoraIni = (Button) findViewById(R.id.btnHoraInicioAddEvent2);
        btnHoraFin = (Button) findViewById(R.id.btnHoraFinAddEvent2);
        btnContinuar = (Button) findViewById(R.id.btnContinuarAddEvento2);
        TeViHoraIni = (TextView) findViewById(R.id.TVHoraInicioAddEvents);
        TeViHoraFin = (TextView) findViewById(R.id.TVHoraFinAddEvento);

        btnHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaInicio = (Integer.toString(pikTim.getCurrentHour())) + ":" + (Integer.toString(pikTim.getCurrentMinute()));
                TeViHoraIni.setText(horaInicio);
            }
        });

        btnHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaFin = (Integer.toString(pikTim.getCurrentHour())) + ":" + (Integer.toString(pikTim.getCurrentMinute()));
                TeViHoraFin.setText(horaFin);
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarHoras())
                {
                    Intent i = new Intent(AgregarEvento2Activity.this, AgregarEvento3Activity.class);
                    i.putExtra("diasss", (Serializable) dates);
                    i.putExtra("HoraIni",horaInicio);
                    i.putExtra("HoraFin",horaFin);
                    startActivity(i);
                }
            }
        });
    }


    public boolean validarHoras()
    {
        if(TeViHoraIni.getText().toString() == "")
        {
            Toast toast1 = Toast.makeText(this, "Debe de seleccionar la Hora de Inicio", Toast.LENGTH_SHORT);
            toast1.show();
            return false;
        }
        if(TeViHoraFin.getText().toString() == "")
        {
            Toast toast2 = Toast.makeText(this, "Debe de seleccionar la Hora de Fin", Toast.LENGTH_SHORT);
            toast2.show();
            return false;
        }
        return true;
    }
}