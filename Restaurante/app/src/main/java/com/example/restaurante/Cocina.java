package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cocina extends ListActivity {

    public AlmacenPedidos m_almacenPedidos;
    public ArrayList<Pedido> m_localPedidos;
    private PedidoAdapter m_pedidoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        refreshLocalOrders();

        //inicializo la lista con los pedidos obtenidos
        m_pedidoAdapter = new PedidoAdapter(this, m_localPedidos);
        setListAdapter(m_pedidoAdapter);
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
            if(m_localPedidos.get(i).getEstadoPedido() > 3){
                m_almacenPedidos.eliminarPedidos(m_localPedidos.get(i).getNumeroPedido());
                m_localPedidos.remove(i);
                m_pedidoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        Toast.makeText(this, "Pedido presionado: " + item.toString(), Toast.LENGTH_SHORT).show();
        for (Pedido itemFromStock : m_localPedidos) {
            if(((Pedido)item).getuniqueID().equals(itemFromStock.getuniqueID())){
                int estado_actual = itemFromStock.getEstadoPedido();
                int nuevo_estado = estado_actual + 1;
                itemFromStock.setEstadoItem(nuevo_estado);
                //update DB also
                m_almacenPedidos.updateOrderState(itemFromStock.getNumeroPedido() ,nuevo_estado);
                m_pedidoAdapter.notifyDataSetChanged();
            }
        }
        super.onListItemClick(l, v, position, id);
    }

}