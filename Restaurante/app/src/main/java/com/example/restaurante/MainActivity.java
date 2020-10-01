package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    public AlmacenStock m_almacenStock;
    public AlmacenPedidos m_almacenPedidos;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        m_almacenStock = new AlmacenStock(this,1);

        ImageView logoImage = findViewById(R.id.imageView);
        logoImage.setImageResource(R.raw.pngegg);
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