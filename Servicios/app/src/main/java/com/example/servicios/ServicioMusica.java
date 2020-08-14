package com.example.servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;

import java.io.IOException;

public class ServicioMusica extends Service {
    private MediaPlayer reproductor;
    private NotificationManager notMan;
    private final static int ID_NOTIFICAION_MUSICA = 1324;
    private final static int ID_RECEIVER = 9876;
    private final static String CHANNEL_ID = "NOTIFICATION_CHANNEL_1";


    public ServicioMusica()
    {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        reproductor = MediaPlayer.create(this, R.raw.audio);
        notMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Servicio Arrancando", Toast.LENGTH_LONG).show();
        reproductor.start();

        Notification.Builder notbuilder;
        NotificationChannel notificationChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "mis notifica's", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Descripcion de la notificaion");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(0xff00ff00);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[] {1000, 2000, 2000, 2000});
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),audioAttributes);
            //Asignar el canal al manejador de notificaciones
            notMan.createNotificationChannel(notificationChannel);

            notbuilder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Servicio de musica")
                    .setContentText("se ñldksañldksañldkas")
                    .setSubText("musica")
                    .setSmallIcon(android.R.drawable.presence_audio_online);

            Notification notification = notbuilder.build();
            notMan.notify(ID_NOTIFICAION_MUSICA, notification);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        reproductor.stop();
        Toast.makeText(this, "Servicio Destroied", Toast.LENGTH_LONG).show();
        notMan.cancel(ID_NOTIFICAION_MUSICA);
    }
}
