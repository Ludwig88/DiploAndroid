package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class Cocina extends AppCompatActivity {

    public AlmacenPedidos m_almacenPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        ArrayList<Pedido> resultados = (ArrayList<Pedido>) m_almacenPedidos.listaPedidos();
        String salida;
        salida = "Resultados de operaciones anteriores... \n";
        if (!resultados.isEmpty()) {
            for (Pedido itemResultado : resultados) {
                salida += itemResultado.toString(); //getNombre() + " " + itemResultado.getNumero() + " " + itemResultado.getResultado() + " \n";
            }
        } else {
            salida += "No hay resultados...";
        }
    }
}