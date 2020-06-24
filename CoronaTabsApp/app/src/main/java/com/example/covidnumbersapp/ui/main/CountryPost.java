package com.example.covidnumbersapp.ui.main;

public class CountryPost {
    private int Country;
    private int id;
    private String title;
    private String body;

    public int getCountry() {
        return Country;
    }

    public void setCountry(int country) {
        this.Country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;

    }
}