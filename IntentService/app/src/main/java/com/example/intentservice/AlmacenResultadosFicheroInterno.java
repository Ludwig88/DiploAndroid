package com.example.intentservice;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AlmacenResultadosFicheroInterno implements AlmacenarResultados {

    private Context context;
    private static final String archivo_nombre = "resultados.text";

    public AlmacenResultadosFicheroInterno(Context context)
    {
        this.context = context;
    }

    @Override
    public ArrayList<?> ListaResultados() {
        ArrayList<Resultado> result = new ArrayList<>();
        try{
            FileInputStream file = context.openFileInput(archivo_nombre);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(file));
            int n = 0;
            String linea;
            do {
                linea = entrada.readLine(); // hasta salto de linea
                if(linea != null)
                {
                    double num = Double.parseDouble(linea.substring( 15, linea.indexOf("es")).trim());
                    double res = Double.parseDouble(linea.substring(linea.indexOf(" es ") + 4).trim());
                    result.add(new Resultado(num, res, "El cuadrado de"));
                }
            }
            while (linea != null);
            file.close();
        }
        catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return result;
    }

    @Override
    public void GuardarResultados(double num, double resultado, String nombre) {
        try{
            FileOutputStream file = context.openFileOutput(archivo_nombre, Context.MODE_APPEND);
            String texto = nombre  + " " + num + " es " + resultado + "\n";
            file.write(texto.getBytes());
            file.close();
        }
        catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void EliminarResultados(double num, double resultado) {

    }
}
