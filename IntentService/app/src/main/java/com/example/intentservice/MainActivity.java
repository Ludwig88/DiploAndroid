package com.example.intentservice;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
        entrada = findViewById(R.id.entrada);
        salida = findViewById(R.id.salida);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE }, 1234 );
        }

        //registrando el broadcast
        IntentFilter filtro = new IntentFilter(ReceptorOperacion.ACTION_RECEPTOR);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        ReceptorOperacion receptorOperacion = new ReceptorOperacion();
        registerReceiver(receptorOperacion, filtro);

        /*Defino el almacen con el tipo que yo haya implementado*/
        //TODO: resultados pref is not working!
        //mALmacen = new AlmacenResultadoPref(this);
        //mALmacen = new AlmacenResultadosSQLLite(this, 1);
        //mALmacen = new AlmacenResultadosFicheroInterno(this);
        mALmacen = new AlmacenResultadosFicheroExterno(this);
    }

    public void calcularOperacion(View view)
    {
        double n = Double.parseDouble(entrada.getText().toString());
        //salida
        Intent intent = new Intent(this, IntentServiceOperacion.class);
        intent.putExtra("numero", n);
        startService(intent);
    }

    public class ReceptorOperacion extends BroadcastReceiver{
        public static final String ACTION_RECEPTOR = "com.example.intentservice.RESPUESTA_OPERACION";

        @Override
        public void onReceive(Context context, Intent intent) {
            Double res = intent.getDoubleExtra("resultado", 0);
            Double num = intent.getDoubleExtra("numero", 0);
            salida.append("EL cuadrado de " + num + " es " + res + "\n");
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
                        itemResultado.toString() //getNombre()
                );
            }
        }
        else
        {
            salida.append("No hay resultados");
        }
    }
}