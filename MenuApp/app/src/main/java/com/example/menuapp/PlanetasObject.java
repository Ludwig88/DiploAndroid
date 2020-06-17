package com.example.menuapp;

import java.util.ArrayList;

public class PlanetasObject {
    private final String Titulo;
    private final String Subtitulo;
    private final Integer Resource;

    public PlanetasObject(String titulo, String subtitulo, Integer resource) {
        Titulo = titulo;
        Subtitulo = subtitulo;
        Resource = resource;
    }

    public String getTitulo()
    {
        return this.Titulo;
    }

    public String getSubtitulo()
    {
        return this.Subtitulo;
    }

    public Integer getResource()
    {
        return this.Resource;
    }

    public static ArrayList<PlanetasObject> getList() {
        ArrayList<PlanetasObject> planetas = new ArrayList<>();
        planetas.add(
                new PlanetasObject("mercurio", "el primer planeta", R.drawable.mercurio)
        );
        planetas.add(
                new PlanetasObject("venus", "el planeta numero 2", R.drawable.venus)
        );
        planetas.add(
                new PlanetasObject("tierra", "el tercer planeta", R.drawable.tierra)
        );
        planetas.add(
                new PlanetasObject("marte", "el numero 4", R.drawable.marte)
        );
        planetas.add(
                new PlanetasObject("jupiter", "el planeta 5", R.drawable.jupiter)
        );
        planetas.add(
                new PlanetasObject("saturno", "el planeta numero 6", R.drawable.saturno)
        );
        planetas.add(
                new PlanetasObject("urano", "el planeta 7", R.drawable.urano)
        );
        planetas.add(
                new PlanetasObject("neptuno", "el planeta numero 8", R.drawable.neptuno)
        );
        return planetas;
    }

}
