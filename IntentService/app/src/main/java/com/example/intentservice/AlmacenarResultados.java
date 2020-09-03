package com.example.intentservice;

import java.util.ArrayList;

public interface AlmacenarResultados {
    public ArrayList<?> ListaResultados();
    public void GuardarResultados(double num, double resultado, String nombre);
    public void EliminarResultados(double num, double resultado);
}
