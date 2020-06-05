package com.example.intenciones;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //si tengo permisos desde mi manifest
        if((checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            || (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            requestPermissions(new String[] {
                    Manifest.permission.INTERNET,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_ID);
        }
    }

    public void onBtnWebClick(View view){
        Intent intento = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.infobae.com/"));
        startActivity(intento);
    }

    public void onBtnPhoneClick(View view){
        EditText phoNumber = findViewById(R.id.edPhone);
        String phoneNumber = phoNumber.getText().toString();
        Intent intento = new Intent(
                //Intent.ACTION_DIAL, //Discar al colgar hay q volveer a la app
                Intent.ACTION_CALL, //Discar -  al colgar vuelve directamente
                Uri.parse("tel:" + phoneNumber));
        startActivity(intento);
    }

    public void onBtnMapClick(View view){
        Intent intento = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:-31.4201,-64,1888")); //ver porque no manda a esa coordenada
        startActivity(intento);
    }

    public void onBtnPhotoClick(View view){
        Intent intento = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intento);
    }

    public void onBtnEmailClick(View view){
        Uri uriEmail = Uri.parse("mailto:");
        Intent intento = new Intent(Intent.ACTION_SENDTO,uriEmail);
        intento.setType("text/text");
        intento.setData(uriEmail);
        intento.putExtra(Intent.EXTRA_SUBJECT, "asunto de email");
        intento.putExtra(Intent.EXTRA_EMAIL, new String[]{
                "pedro@picapiedra.com",
                "tuhermana@picapiedra.com"
        });
        intento.putExtra(Intent.EXTRA_TEXT, "cuerpo del email");
        startActivity(intento);
    }

    public void onViewPhotosClick(View view){
        Intent intento = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intento);
    }

}