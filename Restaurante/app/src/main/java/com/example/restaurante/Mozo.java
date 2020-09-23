package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Mozo extends ListActivity {

    public AlmacenPedidos m_almacenPedidos;
    public AlmacenStock m_almacenStock;
    public ArrayList<StockItem> m_localStockItems;
    private StockItemAdapter stockItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mozo);
        m_almacenPedidos = new AlmacenPedidos(this,1);

        //En el caso que sea necesario lleno los items de la base de datos de Stock, y los guardo localmente
        m_localStockItems = new ArrayList<StockItem>();
        m_localStockItems.clear();
        FillStockList();

        //inicializo la lista con valores
        stockItemAdapter = new StockItemAdapter(this, m_localStockItems);
        setListAdapter(stockItemAdapter);
    }

    private void FillStockList()
    {
        m_almacenStock = new AlmacenStock(this, 1);
        m_almacenStock.populateDB();
        ArrayList<StockItem> stockItems;
        stockItems = (ArrayList<StockItem>) m_almacenStock.listaStockItems();
        if (!stockItems.isEmpty()) {
            int numberOfItems = 0;
            for (StockItem itemFromStock : stockItems) {
                m_localStockItems.add(itemFromStock);
                numberOfItems++;
            }
        } else {
            Toast.makeText(this, "No hay nada en stock...!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        Toast.makeText(this, "Item presionado: " + item.toString(), Toast.LENGTH_SHORT).show();
        for (StockItem itemFromStock : m_localStockItems) {
            if(((StockItem)item).getItemName().equals(itemFromStock.getItemName())){
                int cantidad_actual = itemFromStock.getCantidad();
                itemFromStock.setCantidadItem(++cantidad_actual);
                stockItemAdapter.notifyDataSetChanged();
            }
        }
        super.onListItemClick(l, v, position, id);
    }

    public void endOrder(View view){
        //Loop por todos los items que tengan estado seleccionado
        //almacenar el monto q sumen y msotrarlo en el cuadro de texto del precio

        //TODO: borrar! prueba
        m_almacenPedidos.almacenarPedidos(1,"carlo",3,"coca", 123.9f,1,3);
    }
}