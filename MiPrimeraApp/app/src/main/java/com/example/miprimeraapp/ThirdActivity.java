package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //Una forma usando el ID del elemento
        //EditText editTextALLA = findViewById(R.id.editText3); //antes necesitaba hacerle un cast (EditText)
        //Otra forma usando instanciando el elemento
        TextView editText = new EditText(this);
        Intent intent = getIntent();
        String mensaje = intent.getExtras().getString(intent.EXTRA_TEXT);
        //editTextALLA.setText(mensaje);
        //editTextALLA.setTextSize(10);
        editText.setText("mensaje");
        editText.setTextSize(32);
        editText.setPadding(10,300,10,1);
        setContentView(editText);
        //setContentView(editTextALLA);
    }
}
