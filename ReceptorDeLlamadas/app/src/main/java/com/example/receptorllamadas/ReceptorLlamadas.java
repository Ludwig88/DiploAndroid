package com.example.receptorllamadas;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import java.nio.BufferUnderflowException;

public class ReceptorLlamadas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String estado = "", numero = "";
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            estado = extras.getString(TelephonyManager.EXTRA_STATE);
            if(estado.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                numero = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String info = estado + " - " + numero;
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(context);
                builder.setContentTitle("Llamada entrante");
                builder.setSmallIcon(android.R.drawable.sym_call_incoming);
                builder.setContentInfo(info);
            }
        }

    }
}
