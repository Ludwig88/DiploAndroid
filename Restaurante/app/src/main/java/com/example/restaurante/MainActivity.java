package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public AlmacenPedidos m_almacenPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_almacenPedidos = new AlmacenPedidos(this,1);
    }

    public void NavigateToCocina(View view)
    {
        Intent intent = new Intent(this, Cocina.class);
        startActivity(intent);
    }

    public void NavigateToMozo(View view)
    {
        Intent intent = new Intent(this, Mozo.class);
        startActivity(intent);
    }
}