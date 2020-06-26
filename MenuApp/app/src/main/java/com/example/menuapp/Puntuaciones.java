package com.example.menuapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Puntuaciones extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.puntuaciones_arr)));
        //setListAdapter(new PlanetasAdapter(this, PlanetasObject.getList()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        /*Toast.makeText(this, "Item presionado: " + position, Toast.LENGTH_SHORT).show();*/
        Object item = getListAdapter().getItem(position);
        Toast.makeText(this, "Item press: " + item.toString(), Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
    }
}
