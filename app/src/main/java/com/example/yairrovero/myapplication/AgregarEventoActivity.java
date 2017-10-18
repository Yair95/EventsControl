package com.example.yairrovero.myapplication;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AgregarEventoActivity extends AppCompatActivity {

    private CalendarView CVAddEvent;
    private Button btnCont;
    private String dateUsuario;
    private String dateSql;
    private Date fecha;
    private ListView LVDiasEvent;
    private ArrayList<String> dias = new ArrayList<String>();
    private ArrayAdapter<String> adaptador1;
    private String[] listDiasFInal;
    public static final String constantDias = "Dias";

    private List<Calendar> dates = new ArrayList<Calendar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);

        CVAddEvent = (CalendarView) findViewById(R.id.CVAddEvento);
        LVDiasEvent = (ListView) findViewById(R.id.LVDiasAddEvento);
        dias = new ArrayList<String>();
        dates = new ArrayList<Calendar>();
        adaptador1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dias);
        LVDiasEvent.setAdapter(adaptador1);
        btnCont = (Button) findViewById(R.id.btnAddEventCalendar);

        //Date [] dates = new Date[2];

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VerificarFechas())
                {
                    Intent i = new Intent(AgregarEventoActivity.this, AgregarEvento2Activity.class);
                    i.putExtra("diasss", (Serializable) dates);
                    startActivity(i);
                }
            }
        });

        CVAddEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat paraUsuario = new SimpleDateFormat("dd-MM-yyyy");

                dateUsuario = dayOfMonth + "/" + (month+1) + "/" + year;

                Calendar fechaParaSql =  new GregorianCalendar();
                fechaParaSql.set(year,month,dayOfMonth);

                dias.add(dateUsuario);
                dates.add(fechaParaSql);

                //con esto obtengo del calendar list una fcha en formato sql ;)
                /*Date aver = (dates.get(0).getTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String inActiveDate = null;
                inActiveDate = format1.format(aver);
                System.out.println(inActiveDate );*/

                ActualizarLista();
            }
        });

        LVDiasEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("Clcik", "Clieck en el elementto " + position + " De mi lista");
                borrarItems(position);
            }
        });
    }



    public void borrarItems(int position)
    {
        dias.remove(position);
        dates.remove(position);
        ActualizarLista();
    }

    public void ActualizarLista()
    {
        adaptador1.notifyDataSetChanged();
    }


    public long difDiasEntre2fechas(int Y1,int M1,int D1,int Y2,int M2,int D2){
        java.util.GregorianCalendar date1=new java.util.GregorianCalendar(Y1,M1,D1);
        java.util.GregorianCalendar date2=new java.util.GregorianCalendar(Y2,M2,D2);
        long difms=date2.getTimeInMillis() - date1.getTimeInMillis();
        long difd=difms / (1000 * 60 * 60 * 24);
        return difd;
    }

    public boolean VerificarFechas()
    {
        int cantidadDias = dias.size();
        //int cantidadDias = LVDiasEvent.getAdapter().getCount();
        if(cantidadDias==0)
        {
            Toast toast = Toast.makeText(this, "Debe de seleccionar al menos un día",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else
        {
            if (cantidadDias == 1)
                return true;
            else
            {
                Collections.sort(dias);
                Collections.sort(dates);
                for(int i=1; i<cantidadDias+1; i++)
                {
                    if(i>=cantidadDias)
                        break;

                    int year1       = dates.get(i).get(Calendar.YEAR);
                    int month1      = dates.get(i).get(Calendar.MONTH); // Jan = 0, dec = 11
                    int dayOfMonth1 = dates.get(i).get(Calendar.DAY_OF_MONTH);
                    int year2       = dates.get(i-1).get(Calendar.YEAR);
                    int month2      = dates.get(i-1).get(Calendar.MONTH); // Jan = 0, dec = 11
                    int dayOfMonth2 = dates.get(i-1).get(Calendar.DAY_OF_MONTH);

                    if(difDiasEntre2fechas(year2,month2+1,dayOfMonth2,year1,month1+1,dayOfMonth1)>1)
                    {
                        Toast msn = Toast.makeText(this,"Deben ser días consecutivos",Toast.LENGTH_SHORT);
                        msn.show();
                        return false;
                    }
                }
                ActualizarLista();
                return true;
            }
        }
    }
}