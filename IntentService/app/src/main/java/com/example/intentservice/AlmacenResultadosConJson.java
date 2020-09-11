package com.example.intentservice;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlmacenResultadosConJson implements AlmacenarResultados {

    private Context m_context;
    private Gson gson = new Gson();
    private Type type = new TypeToken<Resultado>(){}.getType();
    private Type typeList = new TypeToken<ArrayList<Resultado>>(){}.getType();
    //Not working on device
    private final String archivo; //= m_context.getExternalFilesDir(null).getAbsolutePath() + File.separator + "resultadosJson.txt";
    //private static final String archivo = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "resultadosJson.txt";

    public AlmacenResultadosConJson(Context context)
    {
        this.m_context = context;
        archivo = m_context.getExternalFilesDir(null).getAbsolutePath() + File.separator + "resultadosJson.txt";
    }

    @Override
    public ArrayList<?> ListaResultados() {
        //TODO: aca va leerJson()
        String Json = leerJson();
        ArrayList<Resultado> resultados = gson.fromJson(Json, typeList);
        return resultados;
    }

    @Override
    public void GuardarResultados(double num, double resultado, String nombre) {
        Resultado result = new Resultado(num, resultado, nombre);
        String json = gson.toJson(result,type );
        guardarJson(json);
    }

    public String leerJson(){
        //Implementar la lectura del archivo de texto completo y devolverlo...
        StringBuilder json = new StringBuilder();
        try {
            FileInputStream f = new FileInputStream(archivo);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
            String linea;
            String sep = "";
            json.append("[");
            do {
                linea = entrada.readLine();
                if (linea != null) {
                    json.append(sep + linea);
                    sep = ",";
                }
            } while (linea != null);
            json.append("]");
            f.close();
        } catch (Exception e) {
            Toast.makeText(m_context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return json.toString();
    }

    public void guardarJson(String json){
        //Implementar la escritura del json string en un archivo de texto...
        try {
            FileOutputStream f = new FileOutputStream(archivo, true);
            json += "\n";
            f.write(json.getBytes());
            f.close();
        } catch (Exception e) {
            Toast.makeText(m_context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void EliminarResultados(double num, double resultado) {
        //TODO: implementar
    }
}
