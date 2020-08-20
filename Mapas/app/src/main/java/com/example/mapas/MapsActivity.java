package com.example.mapas;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewAnimator;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFuseLocationProviderClient;
    private Button btnMyPos, btnMArker, btnIrArg;
    private static final int reqCode = 4321;
    private LatLng cordoba = new LatLng(-31.419867, -64.1903374);
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        btnIrArg = findViewById(R.id.btnArg);
        btnMArker = findViewById(R.id.btnMarcador);
        btnMyPos = findViewById(R.id.btnMyPos);
        btnMyPos.setEnabled(false);

        InitializePermisions();
    }

    public void InitializePermisions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
            }, reqCode);
        } else {
            btnMyPos.setEnabled(true);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == reqCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                InitializePermisions();
            }
        }
    }

    public void onMyPos(View view)
    {
        if(mFuseLocationProviderClient != null)
        {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            mFuseLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>()
            {
                @Override
                public void onSuccess(Location location)
                {
                    if(location != null)
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 17/*mMap.getMaxZoomLevel()*/));
                    }
                }
            });
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //inicializar los eventos de localizacion
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setOnMapClickListener(this);

        mFuseLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-31.42, -64.20);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in casa?"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(new MarkerOptions().position(cordoba).title("Marcador en cordoba?"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cordoba));
        //float maxZoom = mMap.getMaxZoomLevel();

        LocationManager manager = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> proveedores = manager.getAllProviders();
        for (String proveedor : proveedores) {
            Log.i("proveedores", "onMapReady: " + proveedor);
        }
        Criteria crt = new Criteria();
        crt.setCostAllowed(false);
        crt.setAltitudeRequired(false);
        crt.setAccuracy(Criteria.ACCURACY_FINE);
        String proveedor = manager.getBestProvider(crt, false);
        Location localizacion = manager.getLastKnownLocation(proveedor);
        if (localizacion != null) {
            Log.i("localizacion", "onMapReady: " + localizacion.toString());
        } else {
            Log.i("localizacion", "onMapReady: not found");
        }
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
        mMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title("Marcador en " + latLng.latitude + " - " + latLng.longitude)
                        .snippet("Mas información sobe este marcador.")
                        .alpha(0.6f)
                        .anchor(0.5f, 0.5f)
                        .draggable(true)
                        .infoWindowAnchor(0.5f, 0.5f)
        );
    }

    public void onIrArg(View view)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                cordoba,
                16
        ));
    }
}