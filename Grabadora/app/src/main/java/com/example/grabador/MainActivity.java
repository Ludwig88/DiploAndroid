package com.example.grabador;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static final String archivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + "audio.3gp";
    private Button btnGrabar, btnStopRec, btnPlayRec;

    // Para el sound pool
    private SoundPool soundpool;
    private Spinner dropDownSpinner;
    private SeekBar seekBar;
    private float seekRatio = (float) 1.0;
    private Button btnPlay, btnStop;
    private int itemSelected, itemPlay, audioItem, damageItem, invaderItem, ohItem, playerItem, shootItem, uhItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrabar = findViewById(R.id.BtnGrabar);
        btnStopRec = findViewById(R.id.BtnStopRec);
        btnStopRec.setEnabled(true);
        btnPlayRec = findViewById(R.id.BtnPlayRec);
        btnPlayRec.setEnabled(true);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
            }, 1234);
        }

        btnGrabar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                grabar(view);
            }
        });

        btnStopRec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                detener(view);
            }
        });

        btnPlayRec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                reproducir(view);
            }
        });

        // Para funcionalidad Soundpool...
        btnPlay = findViewById(R.id.BtnPlay);
        btnStop = findViewById(R.id.BtnStop);
        dropDownSpinner = findViewById(R.id.spinner);
        seekBar = findViewById(R.id.seekBar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME) //le da mas prioridad
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundpool = new SoundPool.Builder()
                    .setMaxStreams(7)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else
        {
            soundpool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                seekRatio = (float) i / 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        // Se podrian agregar a un vector o lista en vez de cargar individualmente?
        audioItem       =   soundpool.load(this, R.raw.audio, 0);
        damageItem      =   soundpool.load(this, R.raw.damageshelter, 0);
        invaderItem     =   soundpool.load(this, R.raw.invaderexplode, 0);
        ohItem          =   soundpool.load(this, R.raw.oh, 0);
        playerItem      =   soundpool.load(this, R.raw.playerexplode, 0);
        shootItem       =   soundpool.load(this, R.raw.shoot, 0);
        uhItem          =   soundpool.load(this, R.raw.uh, 0);
        itemSelected    =   audioItem;

        String[] items = new String[] {"audio", "damageshelter", "invaderexplode", "oh", "playerexplode", "shoot", "uh"};
        dropDownSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items)); //TODO: ver adaptador
        dropDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        itemSelected = audioItem;
                        break;
                    }
                    case 1: {
                        itemSelected = damageItem;
                        break;
                    }
                    case 2: {
                        itemSelected = invaderItem;
                        break;
                    }
                    case 3: {
                        itemSelected = ohItem;
                        break;
                    }
                    case 4: {
                        itemSelected = playerItem;
                        break;
                    }
                    case 5: {
                        itemSelected = shootItem;
                        break;
                    }
                    case 6: {
                        itemSelected = uhItem;
                        break;
                    }
                    default: {
                        itemSelected = audioItem;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(view);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop(view);
            }
        });

    }

    public void reproducir(View view)
    {
        btnGrabar.setEnabled(false);
        btnStopRec.setEnabled(false);
        btnPlayRec.setEnabled(false);

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource(archivo);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.prepare();
            mediaPlayer.start();
            btnStopRec.setEnabled(false);
            btnGrabar.setEnabled(false);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Fallo la reproduccion: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            btnPlayRec.setEnabled(true);
            btnGrabar.setEnabled(true);
        }
    }

    public void detener(View view)
    {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        btnStopRec.setEnabled(false);
        btnGrabar.setEnabled(true);
        btnPlayRec.setEnabled(true);
    }

    public void grabar(View view)
    {
        btnGrabar.setEnabled(false);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOutputFile(archivo);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            mediaRecorder.prepare();
            btnStopRec.setEnabled(true);
            btnPlayRec.setEnabled(false);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Fallo la grabacion: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            btnGrabar.setEnabled(false);
        }

        mediaRecorder.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        btnGrabar.setEnabled(true);
        btnStopRec.setEnabled(false);
        btnPlayRec.setEnabled(true);
        mediaPlayer.release();
        //mediaPlayer = null;
    }

    // Para el soundpool...
    private void play(View view) {
        itemPlay = soundpool.play(itemSelected,
                1,
                1,
                0,
                0,
                seekRatio
        );
    }

    private void stop(View view) {
        soundpool.stop(itemPlay);
    }

}