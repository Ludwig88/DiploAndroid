package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pools;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener
{

    String acciones[] = { "ACTION_DOWN",
            "ACTION_UP",
            "ACTION_MOVE",
            "ACTION_CANCEL",
            "ACTION_OUTSIDE",
            "ACTION_POINTER_DOWN",
            "ACTION_POINTER_UP"
    };
    TextView entrada;
    TextView salida;
    private boolean isMultitouch;
    private float accelerometer_val = 0.0f;
    private float proximity_val = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = findViewById(R.id.textViewEntrada);
        salida = findViewById(R.id.textViewScroll);

        entrada.setOnTouchListener(this);

        isMultitouch = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);

        SensorManager sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensor_manager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensorItem : listaSensores)
        {
            salida.append(sensorItem.getName() + "\n");
        }

        listaSensores = sensor_manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(!listaSensores.isEmpty())
        {
            Sensor acelerometro = listaSensores.get(0);
            sensor_manager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_UI);

        }

        listaSensores = sensor_manager.getSensorList(Sensor.TYPE_PROXIMITY);
        if(!listaSensores.isEmpty())
        {
            Sensor proximity = listaSensores.get(0);
            sensor_manager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_UI);

        }

    }

    //eventos de los sensores
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        synchronized (this)
        {
            switch (event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER :
                    if(event.values[0] != accelerometer_val)
                    {
                        accelerometer_val = event.values[0];
                        for(int i=0; i <3; i++)
                        {
                            salida.append("Aceleroetro- pos: " + i + " - val: " + event.values[i] + "\n");
                        }
                    }
                    break;

                case Sensor.TYPE_PROXIMITY :
                    if(event.values[0] != proximity_val)
                    {
                        proximity_val = event.values[0];
                        salida.append("Proximity - val: " + event.values[0] + "\n");
                    }
                break;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    //eventos del touch screen
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int accion = event.getAction();
        int codigoAccion = accion & MotionEvent.ACTION_MASK;
        salida.append(acciones[codigoAccion]);

        if(isMultitouch)
        {
            for(int i = 0; i< event.getPointerCount(); i++)
            {
                salida.append( "\n\tPuntero: " + event.getPointerId(i) +
                        "\tX: " + event.getX(i) +
                        "\tY: " + event.getY(i) );
            }

        }
        else
        {
            salida.append( "\tPuntero unico:  \tX: " + event.getX() +
                    "\tY: " + event.getY() );
        }

        salida.append("\n");

        //dimensiones del motion event
        float downTime = event.getDownTime(); //tiempo q el usr preisono por priemra vez
        long eventTime = event.getEventTime();
        float eventPressure = event.getPressure(); //nivel de presion estimado [0,1]
        float eventSize = event.getSize();
        float eventOrientation =event.getOrientation();

        salida.append("\n downTime: " + downTime +
                " - eventTime: " + eventTime +
                " - eventPressure: " + eventPressure +
                " - eventSize: " + eventSize +
                " - eventOrientation: " + eventOrientation +"\n" );

        return true;
    }
}