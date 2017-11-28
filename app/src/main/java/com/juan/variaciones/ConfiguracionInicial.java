package com.juan.variaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Juan on 23/10/2017.
 */


//Esta clase gestiona el requerimiento:
//REQ: Gestion de configuraciones multiples recibido el 23-10-17

public class ConfiguracionInicial {
    public String xxx = this.getClass().getSimpleName();
    public Context context;
    public static final String PREFS_NAME_1 = "licenciaDeUso";
    String prefName = "configuracion";

    //Directorio de trabajo por defecto de la aplicacion
    String pathCesaralMagicImageC = "/CesaralMagic/ImageC/";

    public ConfiguracionInicial(Context context){
            this.context = context;
            Log.d(xxx, "Nueva instancia de la clase  ConfiguracionInicial" );

    }

    public void setValor(String string){//Prueba OK
        //guarda en share preferences el directorio de trabajo actual:
        //que puede ser /CesaralMagic/ImageC/ (por defecto) o
        //alguno que cuelga de /CesaralMagic/ImageC/ introducido a traves de la UI
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME_1, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        if(string.equals("resetxx")){
            //reseteamos la clave, ahora puede entrar cualquiera
            editor.putString(prefName, null); // Storing string

        }else{
            //Guardamos la clave
            editor.putString(prefName, string+"/"); // Storing string

        }
        editor.commit(); // commit changes
    }

    public String getValor(){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME_1, 0); // 0 - for private mode
        String licencia = pref.getString(prefName, null); // getting String
        if(licencia!= null){
            Log.d(xxx, "getValor, la licencia es: " + licencia);
            return licencia;

        }else{
            Log.d(xxx, "getLicencia, la licencia NO existe: ");
            return null;

        }
    }


}
