package com.example.intenciones;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ID = 6565;

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
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE); // "android.media.action.IMAGE_CAPTURE"
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                loadImage(data);
            }
        }
    }

    /*
    private void loadImage(Intent data) {
        //Obtener la foto que viene desde el intent de la camara...
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imagen = (Bitmap)extras.get("data");
            //ImageView imgView = findViewById(R.id.imgView);
            //imgView.setImageBitmap(imagen);
            Intent showintent = new Intent(this, ShowImage.class);
            showintent.putExtra("data", imagen);
            startActivity(showintent);
        } else {
            Toast.makeText(this, "No tiene imagen...", Toast.LENGTH_LONG).show();
        }
    }
*/

    private void loadImage(Intent data) {
        //Obtener la foto que viene desde el intent de la camara...
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imagen = (Bitmap)extras.get("data");
            //ImageView imgView = findViewById(R.id.imgView);
            //imgView.setImageBitmap(imagen);
            Intent showintent = new Intent(this, ShowImage.class);
            showintent.putExtra("data", imagen);
            startActivity(showintent);
        } else {
            Toast.makeText(this, "No tiene imagen...", Toast.LENGTH_LONG).show();
        }
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

    public void onViewPhotosClick(View view)
    {
        /*
        Intent intento = new Intent("android.media.action.IMAGE_CAPTURE");
        getExternalStorage();
        startActivity(intento);
        */
        Intent intent = new Intent(this, GalleryPhotosActivity.class);
        startActivity(intent);
    }
/*
    public void getExternalStorage() {
        String DcimFullPath = Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        File storage = new File(DcimFullPath);
        //String path = storage.getAbsolutePath();
        //path += File.separator + "Camera" + File.separator;
        storage.getPath();
        File[] archivos = storage.listFiles();
        if (archivos != null) {
            for (File file : archivos) {
                String nombre = file.getAbsolutePath();
            }
        }
    }

    public void onViewPhotosClick(View view) {
        Intent intent = new Intent(this, GalleryPhotosActivity.class);
        startActivity(intent);
    }
*/
}
