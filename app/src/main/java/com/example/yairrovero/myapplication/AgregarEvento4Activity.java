package com.example.yairrovero.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregarEvento4Activity extends AppCompatActivity {

    String horaIni, horaFin, nomb, apPaterno, apMaterno, telef1, telef2, precioTot, numPersonas, costPerAd, tipoCli, descipcionEvento;
    boolean clienteNuevo;

    private CalendarView cvFechaAnticipo;
    private TextView tvFecha;
    private EditText etCantidadAnticipo, etHoraAnticipo;
    private Button btnTerminar;

    private Date date = new Date();
    private List<Calendar> dates = new ArrayList<Calendar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento4);

        List<Calendar> diasss = (List<Calendar>) getIntent().getSerializableExtra("diasss");
        dates = diasss;
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        String horaIn = (String)extras.get("HoraIni");
        String horaFi = (String) extras.get("HoraFin");
        String nombre = (String) extras.get("Nombre");
        String apP = (String) extras.get("ApP");
        String apM = (String) extras.get("ApM");
        String tel1 = (String) extras.get("Tel1");
        String tel2 = (String) extras.get("Tel2");
        String preTo = (String) extras.get("PrecioTotal");
        String numPer = (String) extras.get("cantidadPersonas");
        String costoPersonaAdd = (String) extras.get("costoPersAdic");
        String tipCliente = (String) extras.get("tipoCliente");
        String descipcionEv = (String) extras.get("Descripcion");
        boolean nuevoCli = (boolean) extras.get("ClienteNuevo?");

        horaIni=horaIn;
        horaFin=horaFi;
        nomb = nombre;
        apPaterno=apP;
        apMaterno=apM;
        telef1=tel1;
        telef2=tel2;
        precioTot=preTo;
        numPersonas=numPer;
        costPerAd=costoPersonaAdd;
        tipoCli=tipCliente;
        descipcionEvento=descipcionEv;
        clienteNuevo=nuevoCli;


        cvFechaAnticipo = (CalendarView) findViewById(R.id.CVFechaAnticipoAddEvent4);
        tvFecha = (TextView) findViewById(R.id.TVFechaAnticipoAddEvent4);
        etCantidadAnticipo = (EditText) findViewById(R.id.ETCantidadAnticipoAddEvent4);
        etHoraAnticipo = (EditText) findViewById(R.id.etHoraAnticipo);
        btnTerminar = (Button) findViewById(R.id.btnFinalizarAddEvent4);

        cvFechaAnticipo.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
                //String currentdate= ss.format(date);
                //Date datee  = new Date(year,month,dayOfMonth);
                //date.set(year,month,dayOfMonth);
                Calendar cumpleCal = Calendar.getInstance();
                cumpleCal.set(year,month,dayOfMonth); //La hora no me interesa y recuerda que los meses van de 0 a 11
                if(validarFecha(cumpleCal))
                {
                    SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
                    String currentdate= ss.format(cumpleCal.getTime());
                    tvFecha.setText(currentdate);
                }
            }
        });

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valirCampos()){
                    if(clienteNuevo)
                        agregarCliente();
                    agregarDiasEventos0();
                    agregarAnticipo();
                    agregarCosto();
                }
            }
        });
    }

    public boolean valirCampos()
    {
        if(etCantidadAnticipo.getText().toString().isEmpty() || tvFecha.getText().toString().isEmpty() || tvFecha.getText().toString().equals("") || etHoraAnticipo.getText().toString().isEmpty())
        {
            Toast.makeText(AgregarEvento4Activity.this, "Faltan campos obligatorios, revise la fecha", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if(validarHora())
                return true;
            else
                return false;

        }

    }

    public boolean validarFecha(Calendar date)
    {
        if (dates.get(0).compareTo(date) > 0){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"El anticipo debe ser antes del evento no mames",Toast.LENGTH_SHORT).show();
            tvFecha.setText("");
            return false;
        }




        /*Date aver = (dates.get(0).getTime());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String inActiveDate = null;
        inActiveDate = format1.format(aver);
        //System.out.println(inActiveDate );


        Date inicioEvento =  new Date(dates.get(0).getTime().getYear(),dates.get(0).getTime().getMonth(),dates.get(0).getTime().getDay());
        */
        /*if(dates.get(0).getTime().before(date.getTime()))
        {
            Toast.makeText(this,"El anticipo debe ser antes del evento no mames",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;*/
    }

    public boolean validarHora()
    {
        int hour=0, minute=0;
        String sHour="", sMinute="";
        int contadorDePuntos=0;
        String dosPUntos = ":";
        String horaCompleta = etHoraAnticipo.getText().toString();//etHoraAnticipo.getText().toString();
        for(int i=0; i<etHoraAnticipo.length();i++)
        {
            if(horaCompleta.charAt(i)== dosPUntos.charAt(0))
            {
                contadorDePuntos++;
                i++;
            }

            if(contadorDePuntos==0)
                sHour+=horaCompleta.charAt(i);

            if(contadorDePuntos==1)
                sMinute+=horaCompleta.charAt(i);
        }

        hour = Integer.parseInt(sHour);
        if(!sMinute.equals(""))
            minute = Integer.parseInt(sMinute);

        if(sHour.length()>2 || sMinute.length()>2)
        {
            Toast.makeText(AgregarEvento4Activity.this,"Formato de hora incorrecto",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(hour<0 || hour>23 || minute<0 || minute>59)
        {
            Toast.makeText(AgregarEvento4Activity.this,"Formato de hora incorrecto",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    public void agregarAnticipo()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.AGREGAR_ANTICIPO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if (response.contains("Revisa la fecha")) {

                        //}
                        Toast.makeText(AgregarEvento4Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){// throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Constants.KEY_ANTICIPO_CANTIDAD,  etCantidadAnticipo.getText().toString());
                params.put(Constants.KEY_ANTICIPO_FECHAHORAANTICIPO,  tvFecha.getText().toString() + " " + etHoraAnticipo.getText().toString());

                return params;
                //return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("User-Agent","MyTestApp");

                return headers;
                //return super.getHeaders();
            }
        };

        MySingleton.getInstance(AgregarEvento4Activity.this).addToRequestQueue(stringRequest);

    }

    public void agregarDiasEventos0()
    {
        for(int i=0; i<dates.size(); i++)
            agregarDiasEventos(i);
    }

    public void agregarDiasEventos(final int posicion)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.AGREGAR_DIASEVENTOS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if (response.contains("Revisa la fecha")) {

                        //}
                        Toast.makeText(AgregarEvento4Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){// throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                //con esto obtengo del calendar list una fcha en formato sql ;)

                Date aver = (dates.get(posicion).getTime());
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String inActiveDate = null;
                inActiveDate = format1.format(aver);
                //System.out.println(inActiveDate );

                    params.put(Constants.KEY_DIASEVENTOS_DIA,  inActiveDate);


                return params;
                //return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("User-Agent","MyTestApp");

                return headers;
                //return super.getHeaders();
            }
        };

        MySingleton.getInstance(AgregarEvento4Activity.this).addToRequestQueue(stringRequest);

    }

    public void agregarCliente()
    {

                                            ///CLIENTE
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.AGREGAR_CLIENTE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if (response.contains("Revisa la fecha")) {

                        //}
                        Toast.makeText(AgregarEvento4Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){// throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //int tel1 = Integer.parseInt(telef1);
                long tel1 = Long.parseLong(telef1);

                long tel2;
                int tipo;

                if(telef2.equals(""))
                    tel2 = 0;
                else
                    tel2 = Long.parseLong(telef2);

                if(tipoCli.equals(""))
                    tipo=1;
                else
                    tipo = Integer.parseInt(tipoCli);

                if(apPaterno.equals(""))
                    apPaterno="NULL";
                if(apMaterno.equals(""))
                    apMaterno="NULL";

                    params.put(Constants.KEY_CLIENTE_NAME,  nomb);
                    params.put(Constants.KEY_CLIENTE_AP_PA, apPaterno);
                    params.put(Constants.KEY_CLIENTE_AP_MA, apMaterno);
                    params.put(Constants.KEY_CLIENTE_TEL1,  String.valueOf(tel1));
                    params.put(Constants.KEY_CLIENTE_TEL2,  String.valueOf(tel2));
                    params.put(Constants.KEY_CLIENTE_TIPO,  String.valueOf(tipo));

                    params.put(Constants.KEY_CLIENTE_VALORACION,  "NULL");
                    params.put(Constants.KEY_CLIENTE_VISITAS,  String.valueOf(0));
                    params.put(Constants.KEY_CLIENTE_CANCELACIOINES,  String.valueOf(0));
                    params.put(Constants.KEY_CLIENTE_COMENTARIOS,  "NULL");


                return params;
                //return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("User-Agent","MyTestApp");

                return headers;
                //return super.getHeaders();
            }
        };

        MySingleton.getInstance(AgregarEvento4Activity.this).addToRequestQueue(stringRequest);


        ///Localhost
        /*try {
            PreparedStatement pst=conexionBD().prepareStatement("insert into diasEventos values(?)");
            pst.setString(1,date);
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"Agregado",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }*/
    }

    public void agregarCosto()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.AGREGAR_COSTO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if (response.contains("Revisa la fecha")) {

                        //}
                        Toast.makeText(AgregarEvento4Activity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){// throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                Double costoTotal = Double.parseDouble(precioTot);
                if(descipcionEvento.equals(""))
                    descipcionEvento="NULL";

                params.put(Constants.KEY_COSTO_CANTIDADTOTAL, String.valueOf(costoTotal));
                params.put(Constants.KEY_COSTO_DESCRIPCION, descipcionEvento);

                return params;
                //return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("User-Agent","MyTestApp");

                return headers;
                //return super.getHeaders();
            }
        };

        MySingleton.getInstance(AgregarEvento4Activity.this).addToRequestQueue(stringRequest);

    }
    /*public Connection conexionBD()
    {
        Connection conexion = null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.71;databaseName=EventsControl;user='Yair Rovero';password=;");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }*/
}