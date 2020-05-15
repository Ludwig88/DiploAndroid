package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAnotherClick(View view){
        //Toast.makeText(this, "holamundoooo!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SecondActivity.class);
        //es como un Dic con EXTRA_TEXT como key
        intent.putExtra(Intent.EXTRA_TEXT, "Cualquier cosa le mando");
        startActivity(intent);
    }

    public void onClick(View view){
        //Toast.makeText(this, "holamundoooo!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SecondActivity.class);
        //es como un Dic con EXTRA_TEXT como key
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.TextWelcome));
        startActivity(intent);
    }

    //Si no pongo argumento view no me va a aparecer como opcion a lahora de conectar con algun asset
    //del activity xml
    public void onAltoClick(View view){
        Toast.makeText(this, "Otro holamundoooo!", Toast.LENGTH_LONG).show();
    }

}
