package com.example.intentservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlmacenResultadosConJson implements AlmacenarResultados {

    private Context context;
    private Gson gson = new Gson();
    private Type type = new TypeToken<Resultado>(){}.getType();
    private Type typeList = new TypeToken<ArrayList<Resultado>>(){}.getType();

    public AlmacenResultadosConJson(Context context)
    {
        this.context = context;
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
        //TODO: aca va guardarJson()
        guardarJson();
    }

    public String leerJson(){
        return "";
    }

    public void guardarJson(String json){

    }

    @Override
    public void EliminarResultados(double num, double resultado) {

    }
}
