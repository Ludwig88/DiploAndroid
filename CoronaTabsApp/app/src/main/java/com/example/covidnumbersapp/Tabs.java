package com.example.covidnumbersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

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

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Log.i("tabs", "Pestania pulsada: " + s);
            }
        });


    }
}
