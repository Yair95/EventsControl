package com.example.yairrovero.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * Created by Yair Rovero on 18/09/2017.
 */

public class CalendarActivity extends AppCompatActivity{

    private static final String TAG = "CalendarActivity";

    private CalendarView mCalendarView;
    private Button btnInsDate;
    private Button btnAddEvent;

    private String date="", status="Apalabrado";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        btnInsDate = (Button) findViewById(R.id.btnInsFecha);
        btnInsDate.setVisibility(View.GONE);
        btnAddEvent = (Button) findViewById(R.id.btnAddEventCalendar);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month+1) + "/" + year;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " + date);
                ConsultarFecha();
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAddEvento = new Intent(CalendarActivity.this, AgregarEventoActivity.class);
                startActivity(ventanaAddEvento);
            }
        });

    }

        public void ConsultarFecha()
    {
        //Select estatus from viaje where fecha = date
        //if no encuentra resultado
            //Print disponible
        //else
            //print status
        if(status!="")
            btnInsDate.setVisibility(View.VISIBLE);
        else
            btnInsDate.setVisibility(View.GONE);
        status="";
        Toast.makeText(CalendarActivity.this, "Seleccionaste "+date, Toast.LENGTH_SHORT).show();
    }
}
