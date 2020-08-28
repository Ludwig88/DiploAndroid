package com.example.intentservice;

import android.text.style.RelativeSizeSpan;

import androidx.annotation.NonNull;

public class Resultado {
    private Double numero;
    private Double resultado;
    private String nombre;

    public Resultado(Double numero, Double resultado, String nombre)
    {
        this.numero = numero;
        this.resultado = resultado;
        this.nombre = nombre;
    }

    public Double getNumero()
    {
        return this.numero;
    }

    public Double getResultado()
    {
        return this.resultado;
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    @NonNull
    @Override
    public String toString() {
        return "El cuadrado de: " + this.numero + " es " + this.resultado + "\n";
    }
}
