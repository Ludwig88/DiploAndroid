package com.example.restaurante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PedidoAdapter extends BaseAdapter {
    private Context m_context;
    private ArrayList<Pedido> m_pedidos;
    private LayoutInflater m_layoutInflater;

    public PedidoAdapter(Context context, ArrayList<Pedido> pedidos) {
        super();
        m_context = context;
        m_pedidos = pedidos;
        m_layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return m_pedidos.size();
    }

    @Override
    public Object getItem(int i) {
        return m_pedidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return m_pedidos.get(i).getPedidoID(); //FIXME: es esto correcto?
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = m_layoutInflater.inflate(R.layout.elemento_pedido, null);
        TextView mesaNum = view.findViewById(R.id.ItemNumberMesa);
        TextView mozoName = view.findViewById(R.id.itemNameMozo);
        TextView itemsText = view.findViewById(R.id.itemsText);
        String numeroMesa = String.valueOf(((Pedido)getItem(i)).getesaNum());
        mesaNum.setText("Mesa: " + numeroMesa);
        String nombreMozo = ((Pedido)getItem(i)).getMozo();
        mozoName.setText("Mozo: " +nombreMozo);
        String pedidoItem = (((Pedido) getItem(i)).getItem());
        itemsText.setText("ITEM: " + pedidoItem);
        //TODO: acording to order state must change background color
        //R.layout.elemento_stock. getColor(R.color.colorSelected)
        return view;
    }
}
