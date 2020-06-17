package com.example.menuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case(R.id.settings):
                break;

            case(R.id.about):
                showAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void onPuntuacionesClick(View view){
        Intent intent = new Intent(this, Puntuaciones.class);
        startActivity(intent);
    }
}
