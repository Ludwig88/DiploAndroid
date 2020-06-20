package com.example.graficos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animacion;

    //TODO: remember things
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //custom view con la clase EjemploView
        /*setContentView(new EjemploView((this)));

        //una vista con transiciones
        ImageView image = new ImageView(this);
        //setContentView(image);
        TransitionDrawable transicion = (TransitionDrawable) getDrawable( R.drawable.transicion);
        image.setImageDrawable(transicion);
        transicion.reverseTransition(2);
        transicion.startTransition(2000);

        animacion = (AnimationDrawable) getDrawable(R.drawable.animacion);
        ImageView image = new ImageView(this);
        setContentView(image);
        image.setBackgroundColor(Color.CYAN);
        image.setImageDrawable(animacion);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animacion.start();
            }
        });
        */
    }

    //solo acceso dentro de MainActivity
 /*   public class EjemploView extends View {

        private final Context context;

        public EjemploView(Context context) {
            super(context);
            this.context = context;
        }

        //cada vez q se dibuje en la pantalla
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //cambiar de color de fondo
            //setBackgroundColor(Color.GRAY);
            setBackground(ContextCompat.getDrawable(context, R.drawable.gradient));

            //Dibujar aca lo q quiera
            Paint pincel = new Paint();
            pincel.setColor(Color.BLUE);
            pincel.setStrokeWidth(10);
            //relleno, solo trazo, etc
            pincel.setStyle(Paint.Style.STROKE);

            //Dibuja un circulo
            canvas.drawCircle(300,300, 250, pincel);

            //Dibujar aca lo q quiera
            Paint pincel2 = new Paint();
            pincel2.setColor(Color.BLACK);
            pincel2.setStrokeWidth(8);
            //relleno, solo trazo, etc
            pincel2.setStyle(Paint.Style.FILL_AND_STROKE);

            //Dibuja un circulo
            canvas.drawCircle(200,200, 250, pincel2);

            //Trazo unna serie de lineas det por params
            Path trazo = new Path();
            //CounterClockWise
            trazo.addCircle(300, 1000, 200, Path.Direction.CCW);
            canvas.drawPath(trazo, pincel2);
            pincel2.setTextSize(80);
            pincel2.setTypeface(Typeface.SANS_SERIF);
            canvas.drawTextOnPath("Desarrollo de apps Android", trazo, 10, 40, pincel2);

            Path trazo2 = new Path();
            trazo2.moveTo(700,100);
            //Dibujo de una curva
            trazo2.cubicTo(750,150, 800, 170, 890, 300);
            trazo2.lineTo(1000, 1300);

            canvas.drawPath(trazo2, pincel);
            canvas.drawTextOnPath("texto cualquiera en trazo multiple", trazo2, 100, -70, pincel2);

            Drawable mi_imagen = ContextCompat.getDrawable(context, R.drawable.estrella);
            mi_imagen.setBounds(700, 900, 900, 1900);
            mi_imagen.draw(canvas);

            //Drawable mi_imagen_tierr = ContextCompat.getDrawable(context, R.drawable.misil1);
            //mi_imagen_tierr.setBounds(700, 900, 900, 1100);
            //mi_imagen_tierr.draw(canvas);

            Drawable mi_imagen_yt = ContextCompat.getDrawable(context, R.drawable.ic_youtube_icon_f7801);
            mi_imagen_yt.setBounds(700, 900, 900, 1100);
            mi_imagen_yt.draw(canvas);
        }
    }*/
}