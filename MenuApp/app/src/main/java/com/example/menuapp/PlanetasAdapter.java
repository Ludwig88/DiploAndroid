package com.example.menuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanetasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PlanetasObject> planetas;
    private LayoutInflater layoutInflater;

    public PlanetasAdapter(Context context, ArrayList<PlanetasObject> planetas) {
        super();
        this.context = context;
        this.planetas = planetas;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.planetas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.planetas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.planetas.get(i).getResource();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.elemento_planeta, null);
        TextView nombrePlaneta = view.findViewById(R.id.nombrePlaneta);
        TextView subtitulo = view.findViewById(R.id.subtitulo);
        ImageView imagenPlaneta = view.findViewById(R.id.planeta);
        nombrePlaneta.setText(((PlanetasObject)getItem(i)).getTitulo());
        subtitulo.setText(((PlanetasObject)getItem(i)).getSubtitulo());
        imagenPlaneta.setImageResource((int)getItemId(i));
        return view;
    }
}
