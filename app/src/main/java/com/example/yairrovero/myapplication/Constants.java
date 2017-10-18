package com.example.yairrovero.myapplication;

/**
 * Created by Yair Rovero on 28/09/2017.
 */

public class Constants {

            ///URL'S
    public static final String DB_URL = "https://undisciplined-meals.000webhostapp.com/connect/";

    public static final String AGREGAR_CLIENTE_URL =        DB_URL + "agregarCliente.php";
    public static final String AGREGAR_DIASEVENTOS_URL =    DB_URL + "agregarDiasEventos.php";
    public static final String AGREGAR_COSTO_URL =          DB_URL + "agregarCosto.php";
    public static final String AGREGAR_ANTICIPO_URL =       DB_URL + "agregarAnticipo.php";
    public static final String REGISTER_URL =               DB_URL + "register.php";
    public static final String SelectClientes_URL =         DB_URL + "consultaClientes.php";
    public static final String CLIENTE_SELECT_WHERE_URL =   DB_URL + "consultaClienteWhere2.php?idCliente=";


            ///diasPrueba
    public static final String KEY_FECHA = "dia";
    public static final String KEY_NAME = "nombre";

            ///CLIENTE
    public static final String KEY_CLIENTE_ID = "idCliente";
    public static final String KEY_CLIENTE_NAME = "nombre";
    public static final String KEY_CLIENTE_AP_PA = "apellidoPaterno";
    public static final String KEY_CLIENTE_AP_MA = "apellidoMaterno";
    public static final String KEY_CLIENTE_TEL1 = "telefono1";
    public static final String KEY_CLIENTE_TEL2 = "telefono2";
    public static final String KEY_CLIENTE_TIPO = "tipo";
    public static final String KEY_CLIENTE_VALORACION = "valoracion";
    public static final String KEY_CLIENTE_VISITAS = "visitas";
    public static final String KEY_CLIENTE_CANCELACIOINES = "cancelaciones";
    public static final String KEY_CLIENTE_COMENTARIOS = "comentarios";

            ///DIASEVENTOS
    public static final String KEY_DIASEVENTOS_ID = "idDiasEventos";
    public static final String KEY_DIASEVENTOS_DIA = "dia";

            ///ANTICIPO
    public static final String KEY_ANTICIPO_ID = "idAnticipo";
    public static final String KEY_ANTICIPO_CANTIDAD = "cantidad";
    public static final String KEY_ANTICIPO_FECHAHORAANTICIPO = "fechaHoraAnticipo";
    public static final String KEY_ANTICIPO_ESTADO = "estado";

            ///COSTO
    public static final String KEY_COSTO_ID = "idCosto";
    public static final String KEY_COSTO_CANTIDADTOTAL = "cantidadTotal";
    public static final String KEY_COSTO_DESCRIPCION = "descripcion";
}
