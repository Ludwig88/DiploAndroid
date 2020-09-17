package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Mozo extends AppCompatActivity {

    public AlmacenPedidos m_almacenPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mozo);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        m_almacenPedidos.almacenarPedidos(1,"carlo",3,"coca", 123.9f,1);
    }
}