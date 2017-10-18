package com.example.yairrovero.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class AgregarEvento3Activity extends AppCompatActivity{

    private Spinner spinCli;
    private EditText etNom, etApP, etApM, etTel1, etTel2, etPrecioTotal, etNumPersonas, etCostoPerAdic, etTipoCliente, etDescripcion;
    private Button btnCont;

    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    String horaInicioO,horaFinN;
    private List<Calendar> dates = new ArrayList<Calendar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento3);

        List<Calendar> diasss = (List<Calendar>) getIntent().getSerializableExtra("diasss");
        dates = diasss;
        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        String horaIni = (String)extras.get("HoraIni");
        String horaFin = (String) extras.get("HoraFin");
        horaInicioO=horaIni;
        horaFinN=horaFin;



        spinCli = (Spinner) findViewById(R.id.spinnerClientesAddEvent3);
        etNom = (EditText) findViewById(R.id.ETNombreAddEvent3);
        etApP = (EditText) findViewById(R.id.ETApPAddEvent3);
        etApM = (EditText) findViewById(R.id.ETApMAddEvent3);
        etTel1 = (EditText) findViewById(R.id.ETTel1AddEvent3);
        etTel2 = (EditText) findViewById(R.id.EtTel2AddEvent3);
        etPrecioTotal = (EditText) findViewById(R.id.ETPrecioTotalAddEvent3);
        etNumPersonas = (EditText) findViewById(R.id.ETNumPersonasAddEvent3);
        etCostoPerAdic = (EditText) findViewById(R.id.ETCostoPersonaAdicAddEvent3);
        etTipoCliente = (EditText) findViewById(R.id.EtTipoClienteAddEvent3);
        etDescripcion = (EditText) findViewById(R.id.ETDescrpcionAddEvent3);
        btnCont = (Button) findViewById(R.id.btnContinuarAddEvent3);

        enviarRecibirDatos(Constants.SelectClientes_URL);

        spinCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                if(spinCli.getSelectedItemPosition() != 0)
                {
                    enviarRecibirDatos2();
                }
                else
                {
                    etNom.setText("");
                    etNom.setEnabled(true);

                    etApP.setText("");
                    etApP.setEnabled(true);

                    etApM.setText("");
                    etApM.setEnabled(true);

                    etTel1.setText("");
                    etTel1.setEnabled(true);

                    etTel2.setText("");
                    etTel2.setEnabled(true);

                    etTipoCliente.setText("");
                    etTipoCliente.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos())
                {
                    boolean nuevoCliente=false;
                    if(spinCli.getSelectedItemPosition() == 0)
                        nuevoCliente=true;
                    Intent i = new Intent(AgregarEvento3Activity.this, AgregarEvento4Activity.class);
                    i.putExtra("diasss", (Serializable) dates);
                    i.putExtra("HoraIni",horaInicioO);
                    i.putExtra("HoraFin",horaFinN);

                    i.putExtra("Nombre",etNom.getText().toString());
                    i.putExtra("ApP",etApP.getText().toString());
                    i.putExtra("ApM",etApM.getText().toString());
                    i.putExtra("Tel1",etTel1.getText().toString());
                    i.putExtra("Tel2",etTel2.getText().toString());
                    i.putExtra("PrecioTotal",etPrecioTotal.getText().toString());
                    i.putExtra("cantidadPersonas",etNumPersonas.getText().toString());
                    i.putExtra("costoPersAdic",etCostoPerAdic.getText().toString());
                    i.putExtra("tipoCliente",etTipoCliente.getText().toString());
                    i.putExtra("Descripcion",etDescripcion.getText().toString());
                    i.putExtra("ClienteNuevo?",nuevoCliente);

                    startActivity(i);
                }
            }
        });
    }


    public void llenarCombBox(JSONArray ja)
    {
        ArrayList<String> lista = new ArrayList<>();

        for(int i=0; i<ja.length(); i+=2)
        {
            if(i==0)
                lista.add("Seleccione al cliente en caso de que exista");
            try{
                lista.add(ja.getString(i)+" "+ja.getString(i+1));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adapador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        spinCli.setAdapter(adapador);
    }

    public void enviarRecibirDatos(String URL)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());

                            llenarCombBox(ja);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("error"," "+error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    public String obtenerIdCliente() {
        String text = spinCli.getSelectedItem().toString();
        String espacio = " ";
        String idCliente = "";
        //String linea = "elemento1 elemento2 elemento3";
        int j = 0;
        //String linea = "elemento1 elemento2 elemento3";
        String [] campos = text.split("\\s+");
        while(j<campos.length){
            System.out.println(campos[j]);
            j++;
        }
        String aver = campos[0];
        return aver;
    }

    private void enviarRecibirDatos2()
    {
        String idCliente = obtenerIdCliente();
        String URL = Constants.CLIENTE_SELECT_WHERE_URL + idCliente;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        JSONArray json = response.optJSONArray("cliente");
                        JSONObject jsonObject = null;

                        String nombre, apPat, apMat, tel1, tel2, tipo;

                        try {
                            jsonObject=json.getJSONObject(0);

                            nombre=jsonObject.optString("nombre");
                            etNom.setText(nombre);
                            etNom.setEnabled(false);

                            apPat=jsonObject.optString("apellidoPaterno");
                            etApP.setText(apPat);
                            etApP.setEnabled(false);

                            apMat=jsonObject.optString("apellidoMaterno");
                            etApM.setText(apMat);
                            etApM.setEnabled(false);

                            tel1=jsonObject.optString("telefono1");
                            etTel1.setText(tel1);
                            etTel1.setEnabled(false);

                            tel2=jsonObject.optString("telefono2");
                            etTel2.setText(tel2);
                            etTel2.setEnabled(false);

                            tipo=jsonObject.optString("tipo");
                            etTipoCliente.setText(tipo);
                            etTipoCliente.setEnabled(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgregarEvento3Activity.this,"No se pudo realizar la consulta " + error.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        queue.add(getRequest);


    }

    public boolean validarCampos()
    {
        if(etNom.getText().toString().isEmpty() || etTel1.getText().toString().isEmpty()
                || etPrecioTotal.getText().toString().isEmpty() || etNumPersonas.getText().toString().isEmpty()
                || etCostoPerAdic.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,"Faltan campos obligatorios por llenar",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        else
        {
            if(!etTipoCliente.getText().toString().isEmpty())
            {
                if(etTipoCliente.getText().toString().equals("1") || etTipoCliente.getText().toString().equals("2"))
                    return true;
                else
                {
                    Toast toast = Toast.makeText(this,"Tipo de cliente incorrecto, solo se acepta 1 y 2",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }
            return true;
        }
    }
}