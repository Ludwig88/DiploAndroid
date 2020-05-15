package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        EditText editTextALLA = findViewById(R.id.editText3); //antes necesitaba hacerle un cast (EditText)
        EditText editText = new EditText(this);
        Intent intent = getIntent();
        String mensaje = intent.getExtras().getString(intent.EXTRA_TEXT);
        editText.setText(mensaje);
        editText.setTextSize(32);
        setContentView(editTextALLA);
    }
}
