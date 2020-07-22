package com.example.multimedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        View.OnClickListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener {

    private boolean readExternalStorage = false;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ImageButton bPlay, bPause, bStop, bLog;
    private TextView logTextView;
    private EditText editText;
    private ScrollView scrollView;
    private int savedPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1234);
        }
        else
        {
            readExternalStorage = true;
        }

        logTextView = findViewById(R.id.logText);
        logTextView.append("onCreate!...\n");

        surfaceView = findViewById(R.id.surfaceView);
        UpdateSurfaceView();

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        scrollView = findViewById(R.id.scrollView);
        editText = findViewById(R.id.editText);
        String videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath() +
                File.separator +
                "video.mp4";
        videoPath = "http://personales.gan.upv.es/~jtomas/video.3gp";
        editText.setText(videoPath);

        bPlay = findViewById(R.id.start);
        bPause = findViewById(R.id.pause);
        bStop = findViewById(R.id.stop);
        bLog = findViewById(R.id.log);

        bPlay.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bLog.setOnClickListener(this);
        bPause.setOnClickListener(this);

        //UpdateSurfaceView();

    }

    private void UpdateSurfaceView() {
        if(readExternalStorage)
        {
            surfaceView.setVisibility(View.VISIBLE);
        }
        else
        {
            surfaceView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log("OnStop...");
        if(mediaPlayer != null)
        {
            pauseVideo();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log("OnResume...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log("OnStart...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log("OnStart...");
        if(mediaPlayer != null)
        {
            playVideo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log("OnPause...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log("OnDestroy...");
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        if(mediaPlayer != null)
        {
            stopVideo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1234)
        {
            if(permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                readExternalStorage = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            }
            else
            {
                readExternalStorage = (grantResults[1] == PackageManager.PERMISSION_GRANTED);
            }
            UpdateSurfaceView();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log("Surface Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log("Surface changed");
        if(savedPos != 0)
        {
            playVideo();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log("Surface destroyed");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.play:
            {
                Log("play video ...");
                playVideo();
                break;
            }
            case R.id.log:
            {
                Log("log video ...");
                toggleLog();
                break;
            }
            case R.id.stop:
            {
                Log("stop video ...");
                stopVideo();
                break;
            }

            case R.id.pause:
            {
                Log("pause video ...");
                pauseVideo();
                break;
            }

            default:
                {
                // nada
                }
        }
    }

    private void pauseVideo()
    {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        else
        {
            Log("No hay video reproduciendose");
        }
    }



    private void toggleLog()
    {
        if(scrollView.getVisibility() == TextView.VISIBLE)
        {
            scrollView.setVisibility(TextView.GONE);
        }
        else
        {
            scrollView.setVisibility(TextView.VISIBLE);
        }
    }

    private void stopVideo()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        else
        {
            Log("No hay video cargado");
        }
    }

    private void playVideo()
    {
        if(mediaPlayer == null)
        {
            try {
                String path = editText.getText().toString();
                if(!path.isEmpty() )
                {
                    mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
                    mediaPlayer.setDisplay(surfaceHolder);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.setOnBufferingUpdateListener(this);
                    //mediaPlayer.prepare();
                    mediaPlayer.prepareAsync();
                }
                else
                {
                    Log("Por favor ingrese un video...");
                }

            }
            catch (Exception err)
            {
                Log("Error: " + err.getMessage());
            }
        }
        else
        {
            mediaPlayer.start();
        }
    }

    private void Log(String play)
    {
        logTextView.append(play + "\n");
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log("onPrepared...");
        int videoWIdth = mediaPlayer.getVideoWidth();
        int videoHeight = mediaPlayer.getVideoHeight();
        if(videoHeight!= 0 && videoWIdth != 0)
        {
            surfaceHolder.setFixedSize(videoWIdth, videoHeight);
            mediaPlayer.seekTo(savedPos);
            mediaPlayer.start();
        }
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Log("onCompletion...");
        savedPos = 0;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent)
    {
        Log("Buffering " + percent + "percent...");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        Log("OnsaveInstanceState");
        if(mediaPlayer != null)
        {
            int pos = mediaPlayer.getCurrentPosition();
            savedInstanceState.putString("ruta", editText.getText().toString());
            savedInstanceState.putInt("pos", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log("OnRestoreInstanceState");
        if(savedInstanceState != null)
        {
            editText.setText(savedInstanceState.getString("ruta"));
            savedPos = savedInstanceState.getInt("pos");
            Log("Posicion: " + savedPos);
        }
    }
}