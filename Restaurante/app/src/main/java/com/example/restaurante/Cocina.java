package com.example.restaurante;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class Cocina extends ListActivity {

    public AlmacenPedidos m_almacenPedidos;
    public ArrayList<Pedido> m_localPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        refreshLocalOrders();

        //inicializo la lista con los pedidos obtenidos
        PedidoAdapter pedidoAdapter = new PedidoAdapter(this, m_localPedidos);
        setListAdapter(pedidoAdapter);
        updateList();
    }

    protected void borrarDb(View view){
        m_almacenPedidos.eliminarTodosLosPedidos();
    }

    private void refreshLocalOrders() {
        m_localPedidos = (ArrayList<Pedido>) m_almacenPedidos.listaPedidos();
    }

    private void updateList(){
        for (int i = 0; i < m_localPedidos.size(); ++i) {
            if (m_localPedidos.get(i).getEstadoPedido() >= 3) {
                int numeroPedido = m_localPedidos.get(i).getNumeroPedido();
                m_localPedidos.remove(i);
                m_almacenPedidos.eliminarPedidos(numeroPedido);
                PedidoAdapter pedidoAdapter = new PedidoAdapter(this, m_localPedidos);
                setListAdapter(pedidoAdapter);
            }
        }
    }

    public void refreshOrders(View view){
        refreshLocalOrders();
        updateList();
    }

    void SendOrderReadyNotification(Pedido itemPedido){

        final int ID_RECEIVER = 5678;
        final int ID_NOTIFICACION_ORDEN_LISTA = 1234;
        final String CHANNEL_ID = "NOTIFICATION_CHANNEL_R";
        String pedido_listo = "Pedido Nº"+ itemPedido.getNumeroPedido() + " LISTO /n" + itemPedido.getMozo() + " debe retirar";
        String content_title = "Restaurant App";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notBuilder;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Restaurant notificaciones",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Restaurant Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(0xff00ff00);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[] {1000, 500});

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes);

            nm.createNotificationChannel(notificationChannel);

            notBuilder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(content_title)
                    .setContentText(pedido_listo)
                    .setSmallIcon(android.R.drawable.star_on);
        } else {
            notBuilder = new Notification.Builder(this);
            notBuilder.setContentTitle(content_title)
                    .setContentText(pedido_listo)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }

        Notification notification = notBuilder.build();
        nm.notify(ID_NOTIFICACION_ORDEN_LISTA, notification);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        for (Pedido itemPedido : m_localPedidos) {
            if(((Pedido)item).getuniqueID().equals(itemPedido.getuniqueID())){
                int estado_actual = itemPedido.getEstadoPedido();
                int nuevo_estado = estado_actual + 1;
                //update DB and local array
                itemPedido.setEstadoItem(nuevo_estado);
                m_almacenPedidos.updateOrderState(itemPedido.getNumeroPedido() ,nuevo_estado);

                // update view
                PedidoAdapter pedidoAdapter = new PedidoAdapter(this, m_localPedidos);
                setListAdapter(pedidoAdapter);

                Toast.makeText(this, "Pedido presionado: " + item.toString(), Toast.LENGTH_SHORT).show();

                //Send notification
                if(nuevo_estado == 3){
                    SendOrderReadyNotification(itemPedido);
                }
            }
        }
        super.onListItemClick(l, v, position, id);
    }

}