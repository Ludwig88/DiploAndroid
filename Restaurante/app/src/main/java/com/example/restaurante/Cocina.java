package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
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
        m_localPedidos = (ArrayList<Pedido>) m_almacenPedidos.listaPedidos();

        //inicializo la lista con los pedidos obtenidos
        m_pedidoAdapter = new PedidoAdapter(this, m_localPedidos);
        setListAdapter(m_pedidoAdapter);
    }

    protected void borrarDb(View view){
        m_almacenPedidos.eliminarPedidos(1);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        Toast.makeText(this, "Pedido presionado: " + item.toString(), Toast.LENGTH_SHORT).show();
        for (Pedido itemFromStock : m_localPedidos) {
            if(((Pedido)item).getuniqueID().equals(itemFromStock.getuniqueID())){
                int estado_actual = itemFromStock.getEstadoPedido();
                itemFromStock.setEstadoItem(++estado_actual);
                m_pedidoAdapter.notifyDataSetChanged();
            }
        }
        super.onListItemClick(l, v, position, id);
    }

}