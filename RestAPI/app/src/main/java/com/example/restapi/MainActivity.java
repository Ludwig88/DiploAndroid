package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.test.espresso.remote.EspressoRemoteMessage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import org.junit.runner.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity
{
    String url = "http://ergast.com/api/f1/2020/driverStandings.json";
    String urlImage = "https://commons.wikimedia.org/wiki/File:Cats_eye.jpg#/media/File:Cats_eye.jpg";
    //"https://www.publicdomainpictures.net/pictures/30000/velka/halloween-1351523573K4k.jpg";
    String urlDatos = "https://jsonplaceholder.typicode.com/posts";

    RequestQueue requestQueue;
    public static ProgressDialog pd;
    ImageView imageView;
    TextView resultText;
    NetworkImageView imageViewVOlley;
    ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{ Manifest.permission.INTERNET}, 1234);
        }

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Cargando datos...");

        //Inicilzar la cola de peticiones con voller
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, new BitmapLRUCache());

        //imageView = findViewById(R.id)
        resultText = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

    }

    public void onQuery(View view)
    {
        pd.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<F1> f1 = F1.fromJson(response);
                        for (F1 item : f1){
                            resultText.append("Posicion: " + item.getPosicion() + " - Puntos: " + item.getPuntos() + " - Piloto: " + item.getNombre() + "\n");
                        }
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("error", Objects.requireNonNull(error.getMessage()));
                        pd.dismiss();
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void senData(View view){
        pd.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                urlDatos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                        } catch (JSONException ex) {
                            resultText.append("Error: " + ex.getMessage());
                        }
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultText.append("Error!: "+error.getMessage());
                        pd.dismiss();
                    }
                }
        );



        requestQueue.add(stringRequest);
    }

    public void onLoadImage(View view){
        //cargarConImageRequest();
        cargarImageConCache();
    }

    public void cargarImageConCache() {
        imageLoader.get(urlImage, ImageLoader.getImageListener(
                imageViewVOlley,
                android.R.drawable.ic_menu_camera, //imagen previa antes de cargar la imagen, por las dudas
                android.R.drawable.ic_dialog_alert //en el caso q haya error
        ));

        imageViewVOlley.setImageUrl(urlImage, imageLoader);
    }

    public void cargarConImageRequest()
    {
        pd.show();
        ImageRequest imageRequest = new ImageRequest(
                urlImage,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                        pd.dismiss();
                    }
                },
                0,
                0,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultText.append("Error en la imagenMan!" + error.getMessage());
                        pd.dismiss();
                    }
                }
        );
        requestQueue.add(imageRequest);
    }
}