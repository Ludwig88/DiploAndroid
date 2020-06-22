package com.example.covidnumbersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class Tabs extends AppCompatActivity {

    public enum Fields {
        CONTAGIADOS,
        RECUPERADOS,
        FALLECIDOS,
        EMPTY
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        //Tab creation
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab_1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Cordoba");
        TextView present = findViewById(R.id.textViewPresent1); //stats_presentation
        String title_present = getResources().getString(R.string.stats_presentation);
        //TODO: make it a variable and send it from the other activity
        String value_fecha = getResources().getString(R.string.date_description);
        present.setText( title_present + value_fecha);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab_2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Argentina");
        TextView present2 = findViewById(R.id.textViewPresent2); //stats_presentation
        present2.setText( title_present + value_fecha);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab_3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Mundo");
        TextView present3 = findViewById(R.id.textViewPresent3); //stats_presentation
        present3.setText( title_present + value_fecha);
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

    private String GetValueByTab(Fields Field, String Tab){
        switch (Tab){
            case "Tab_1":
                switch (Field){
                    case CONTAGIADOS:
                        return "459";
                    case RECUPERADOS:
                        return "93";
                    case FALLECIDOS:
                        return "31";
                }
                break;
            case "Tab_2":
                switch (Field){
                    case CONTAGIADOS:
                        return "13933";
                    case RECUPERADOS:
                        return "4349";
                    case FALLECIDOS:
                        return "500";
                }
                break;
            case "Tab_3":
                switch (Field){
                    case CONTAGIADOS:
                        return "5707193";
                    case RECUPERADOS:
                        return "2361312";
                    case FALLECIDOS:
                        return "355956";
                }
                break;
        }
        return "";
    }

    public void UpdateValues(String s){
        TextView et_contagiados = null,et_recuperados= null,et_fallecidos= null;

        //TODO: make ids with concatenation, Field + tab_number
        //                              recuperados2
        switch (s){
            case "Tab_1":
                et_contagiados = findViewById(R.id.contagiados1);
                et_recuperados = findViewById(R.id.recuperados1);
                et_fallecidos = findViewById(R.id.muertos1);
                break;
            case "Tab_2":
                et_contagiados = findViewById(R.id.contagiados2);
                et_recuperados = findViewById(R.id.recuperados2);
                et_fallecidos = findViewById(R.id.muertos2);
                break;
            case "Tab_3":
                et_contagiados = findViewById(R.id.contagiados3);
                et_recuperados = findViewById(R.id.recuperados3);
                et_fallecidos = findViewById(R.id.muertos3);
                break;
            default:
                break;
        }

        et_contagiados.setText(GetValueByTab(Fields.CONTAGIADOS, s));
        et_recuperados.setText(GetValueByTab(Fields.RECUPERADOS, s));
        et_fallecidos.setText(GetValueByTab(Fields.FALLECIDOS, s));

    }

}
