package com.example.restapi;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class F1 {
    private Integer posicion;
    private Integer puntos;
    private  String nombre;

    public F1(Integer position, Integer puntos, String Name)
    {
        posicion = position;
        puntos = puntos;
        nombre = Name;
    }

    public Integer getPosicion()
    {
        return posicion;
    }

    public Integer getPuntos()
    {
        return puntos;
    }

    public String getNombre()
    {
        return nombre;
    }
    public static ArrayList<F1> fromJson(String jsonString)
    {
        ArrayList<F1> flist = new ArrayList<>();
        try{
            JSONObject f1Object = new JSONObject(jsonString);
            JSONObject MRdata = f1Object.getJSONObject("MRData");
            JSONObject StandingsTable = f1Object.getJSONObject("StandingsTable");
            JSONObject StandingsLists = (JSONObject) StandingsTable.getJSONArray("StandingsLists").get(0);
            JSONArray DriverStandings = StandingsLists.getJSONArray("DriverStandings");
            for (int i=0; i<DriverStandings.length(); i++)
            {
                JSONObject DriverItem = (JSONObject) DriverStandings.get(i);
                Integer position = DriverItem.getInt("position");
                //Integer position = DriverStandings.getInt()
                //JSONObject Driver = DriverStandings.getInt("Driver");
                F1 uno = new F1(position,0,"Carlo");
                flist.add(uno);
            }
        }
        catch (JSONException exception) {
            exception.printStackTrace();
        }
        return flist;
    }
}
