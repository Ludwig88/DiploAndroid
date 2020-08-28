package com.example.intentservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainActivity extends AppCompatActivity {

    private EditText entrada;
    public static TextView salida;
    public AlmacenarResultados mALmacen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //entrada = findViewById(R.)
        //entrada = findViewById(R.)

    }

    public void calcularOperacion(View view)
    {

    }

    public class ReceptorOperacion extends BroadcastReceiver{
        public static final String ACTION_RECEPTOR = "com.example.intentservice.RESPUESTA_OPERACION";

        @Override
        public void onReceive(Context context, Intent intent) {
            Double res = intent.getDoubleExtra("resultado", 0);
            Double num = intent.getDoubleExtra("numero", 0);
            salida.append("EL cuadrado de " + num + " es " + res);
            //almacena generico
            mALmacen.GuardarResultados(num, res, "El cuadrado de");
        }
    }

    public void verResultados(View view)
    {
        ArrayList<Resultado> resultados = (ArrayList<Resultado>) mALmacen.ListaResultados();
        salida.setText("Resultados de operaciones anteriores    \n");
        if(!resultados.isEmpty())
        {
            for (Resultado itemResultado : resultados)
            {
                salida.append(
                        itemResultado.getNombre()
                );
            }
        }
    }
}