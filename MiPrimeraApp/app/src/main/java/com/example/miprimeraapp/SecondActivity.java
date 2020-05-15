package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        EditText editText = findViewById(R.id.editTextSecond); //antes necesitaba hacerle un cast (EditText)
        Intent intent = getIntent();
        String mensaje = intent.getExtras().getString(intent.EXTRA_TEXT);
        editText.setText(mensaje);
    }
}
