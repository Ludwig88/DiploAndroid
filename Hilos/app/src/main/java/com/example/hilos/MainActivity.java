package com.example.hilos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText entrada;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = findViewById(R.id.editTextNumber);
        salida = findViewById(R.id.textResult);
    }

    public int factorial(int n) {
        int fac = 1;
        for (int i=1; i<=n; i++) {
            fac *= i;
            SystemClock.sleep(1000);
        }
        return fac;
    }

    public void calculateOperation(View view) {
        int n = Integer.parseInt(entrada.getText().toString());
        // Opcion sin thread
        // salida.append(n + " * = " + factorial(n) +"\n");
        //opcion con Thread - Thread
        //MiHilo nuevoHilo = new MiHilo(n);
        //nuevoHilo.start();
        //opcion con Thread - AsyncTask
        MiHomework mitarea = new MiHomework();
        mitarea.execute(n);
    }

    class MiHilo extends Thread {
        private int n, res;

        public  MiHilo(int num){
            n = num;
        }

        @Override
        public void run() {
            super.run();
            res = factorial(n);
            //cuando termine tengo q sincronizar el resultado con el hilo principal
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    salida.append( n + " * = " + res +"\n");
                }
            });
        }
    }

    //usa un solo asyncTask para todos, es decir no isntancias nuevos por cada click,
    // por ende primero termina uno y luego empieza el otro, por mas q el segundo se termine mas rapido
    class MiHomework extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog progreso;

        //metodo que inicia la ejecucion -  se ejecuta en Mainthread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inicializacion de la barra de progreso
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setMax(100);
            progreso.setProgress(0);
            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progreso.setMessage("Calculating... factorial<>lairotcaf");
            //agregamos control de proceso
            progreso.setCancelable(true);
            progreso.setButton(DialogInterface.BUTTON_NEGATIVE, "cancelar calculo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MiHomework.this.cancel(true); // true ; cancela la q corre tambein
                }
            });
            progreso.setCanceledOnTouchOutside(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MiHomework.this.cancel(true); // true ; cancela la q corre tambein
                }
            });
            progreso.show();
        }

        //este es el metodo que va a correr en segundo plano
        @Override
        protected Integer doInBackground(Integer... integers) {
            Integer n = integers[0];
            int fac = 1;
            for (int i=1; i<=n && !isCancelled(); i++) {
                fac *= i;
                SystemClock.sleep(1000);
                publishProgress(i * 100 / n);
            }
            return fac;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progreso.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            salida.append("---------CANCELED---------\n");
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            salida.append("available result: " + integer + "---------CANCELED---------\n");
        }

        //metodo q corre cuando finlaiza la ejecucion del hilo secundario pero en el hilo principal
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            salida.append(" * = " + integer +"\n");
            progreso.dismiss();
        }
    }

}