package com.example.covidnumbersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button buttonGo = findViewById(R.id.button_welcomeGo);
        //buttonGo.add
    }

    public void onWelcomeGoClick(View view) {
<<<<<<< HEAD
        Intent intetNavigation = new Intent(this, tabActivity.class);
        startActivity(intetNavigation);
=======
        Intent intentNavigation = new Intent(this, Tabs.class);
        startActivity(intentNavigation);
>>>>>>> acd46a472b57e0ae9b734acb2dfbd0b8362b0795
    }
}
