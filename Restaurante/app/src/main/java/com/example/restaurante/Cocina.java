package com.example.restaurante;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ListView;
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
    }

    protected void borrarDb(View view){
        m_almacenPedidos.eliminarTodosLosPedidos();
    }

    private void refreshLocalOrders() {
        m_localPedidos = (ArrayList<Pedido>) m_almacenPedidos.listaPedidos();
    }

    public void refreshOrders(View view){
        refreshLocalOrders();
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

    void SendOrderReadyNotification(Pedido itemPedido){
        String pedido_listo = "Pedido Nº"+ itemPedido.getNumeroPedido() + " LISTO /n" + itemPedido.getMozo() + " debe retirar";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Orden Lista");
        builder.setSmallIcon(android.R.drawable.star_on);
        builder.setContentInfo(pedido_listo);
        //TODO: sent it!
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
                SendOrderReadyNotification(itemPedido);
            }
        }
        super.onListItemClick(l, v, position, id);
    }

}