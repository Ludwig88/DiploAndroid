package com.example.covidnumbersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class Tabs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tab_1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Cordoba");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab_2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Argentina");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab_3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Mundo");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        UpdateValues("Tab_1");

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                UpdateValues(s);
            }
        });
    }

    public void UpdateValues(String s){

        int number_contagiados=0, number_recuperados=0, number_muertos=0;
        TextView et_contagiados = null,et_recuperados= null,et_fallecidos= null;

        switch (s){
            case "Tab_1":
                number_contagiados=459;
                number_recuperados=93;
                number_muertos=31;
                et_contagiados = findViewById(R.id.textView3);
                et_recuperados = findViewById(R.id.textView4);
                et_fallecidos = findViewById(R.id.textView5);
                break;
            case "Tab_2":
                number_contagiados=13933;
                number_recuperados=4349;
                number_muertos=500;
                et_contagiados = findViewById(R.id.textView8);
                et_recuperados = findViewById(R.id.textView9);
                et_fallecidos = findViewById(R.id.textView10);
                break;
            case "Tab_3":
                number_contagiados=5707193;
                number_recuperados=2361312;
                number_muertos=355956;
                et_contagiados = findViewById(R.id.textView13);
                et_recuperados = findViewById(R.id.textView14);
                et_fallecidos = findViewById(R.id.textView15);
                break;
            default:
                break;
        }

        String ed_texto = et_contagiados.getText().toString();
        if (!ed_texto.matches(".*\\d.*"))
        {
            ed_texto = ed_texto + number_contagiados;
            et_contagiados.setText(ed_texto);
        }

        String texto_rec = et_recuperados.getText().toString();
        if (!texto_rec.matches(".*\\d.*"))
        {
            texto_rec = texto_rec + number_recuperados;
            et_recuperados.setText(texto_rec);
        }

        String texto_fall = et_fallecidos.getText().toString();
        if (!texto_fall.matches(".*\\d.*"))
        {
            texto_fall = texto_fall + number_muertos;
            et_fallecidos.setText(texto_fall);
        }
    }
}
