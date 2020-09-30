package com.example.restaurante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class StockItemAdapter extends BaseAdapter {
    private Context m_context;
    private ArrayList<StockItem> m_stockItems;
    private LayoutInflater m_layoutInflater;

    public StockItemAdapter(Context context, ArrayList<StockItem> stockItems) {
        super();
        m_context = context;
        m_stockItems = stockItems;
        m_layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return m_stockItems.size();
    }

    @Override
    public Object getItem(int i) { return m_stockItems.get(i); }

    @Override
    public long getItemId(int i) {
        return (long) 0.0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = m_layoutInflater.inflate(R.layout.elemento_stock, null);
        TextView nombreItem = view.findViewById(R.id.ItemName);
        EditText cantidadItem = view.findViewById(R.id.textQuantity);
        TextView precioItem = view.findViewById(R.id.textPrice);

        nombreItem.setText(((StockItem)getItem(i)).getItemName());
        int cantidad_int = ((StockItem)getItem(i)).getCantidad();
        String cantidad_string = String.valueOf(cantidad_int);
        cantidadItem.setText(cantidad_string);

        float precio = (float) ((StockItem)getItem(i)).getPrecio();
        precioItem.setText(String.valueOf(precio));

        int colorItem = cantidad_int > 0 ? R.color.colorSelected : R.color.colorUnselected;
        view.setBackgroundColor(ContextCompat.getColor(m_context,colorItem));

        return view;
    }
}
