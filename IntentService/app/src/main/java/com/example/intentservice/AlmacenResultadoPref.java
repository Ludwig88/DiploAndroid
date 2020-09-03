package com.example.intentservice;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class AlmacenResultadoPref implements AlmacenarResultados {

    private Context context;

    public AlmacenResultadoPref(Context context){
        this.context = context;
    }

    @Override
    public ArrayList<Resultado> ListaResultados() {
        ArrayList<Resultado> result = new ArrayList<Resultado>();
        SharedPreferences preferences = context.getSharedPreferences("resultados", Context.MODE_PRIVATE);
        int i = -1;
        String vres = "";
        do {
            i++;
            vres = preferences.getString("resultado" + i, "");
            if(!vres.isEmpty())
            {
                try {
                    double num = Double.parseDouble(vres.substring( 15, vres.indexOf("es")).trim());
                    double res = Double.parseDouble(vres.substring(vres.indexOf(" es ") + 4).trim());
                    result.add(new Resultado(num, res, "El cuadrado de"));
                }catch (Exception err){
                    result.add(new Resultado(0.0,0.0,""));
                }
            }
        }while (!vres.isEmpty());

        return result;
    }

    @Override
    public void GuardarResultados(double num, double resultado, String nombre) {
        SharedPreferences preferences = context.getSharedPreferences("resultados", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int i = -1;
        String vres = "";
        //obtenoo la ultima posicion

        do {
            i++;
            vres = preferences.getString("resultado" + i, "");
        }while (!vres.isEmpty());

        //put resultado
        editor.putString("resultado " + i , "- El cuadrado de " + num + " es "+ resultado);
        editor.apply();
        editor.commit();
    }

    @Override
    public void EliminarResultados(double num, double resultado) {
        //fuera de scope
    }
}
