package com.example.covidnumbersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onWelcomeGoClick(View view) {
        Intent intentNavigation = new Intent(this, Tabs.class);
        EditText date = findViewById(R.id.editTextDate);
        String date_text = date.getText().toString();
        intentNavigation.putExtra(Intent.EXTRA_TEXT, date_text);
        startActivity(intentNavigation);
    }
}