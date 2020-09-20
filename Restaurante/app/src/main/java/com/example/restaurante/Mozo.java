package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Mozo extends ListActivity {

    public AlmacenPedidos m_almacenPedidos;
    public AlmacenStock m_almacenStock;
    public ArrayList<StockItem> m_localStockItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mozo);
        m_almacenPedidos = new AlmacenPedidos(this,1);
        //m_almacenPedidos.almacenarPedidos(1,"carlo",3,"coca", 123.9f,1); //TODO: borrar! prueba

        //en el caso que sea necesario lleno los items de la base de datos de Stock, y los guardo localmente
        m_localStockItems = new ArrayList<StockItem>();
        m_localStockItems.clear();
        FillStockList();

        //inicializo la lista con valores
        setListAdapter(new StockItemAdapter(this, m_localStockItems));
    }

    private void FillStockList()
    {
        m_almacenStock = new AlmacenStock(this, 1);
        m_almacenStock.populateDB();
        ArrayList<StockItem> stockItems = (ArrayList<StockItem>) m_almacenStock.listaStockItems();
        String salida = "Items en la lista de stock... \n"; //TODO: borrar! Variable de prueba.
        if (!stockItems.isEmpty()) {
            int numberOfItems = 0;
            for (StockItem itemFromStock : stockItems) {
                m_localStockItems.add(itemFromStock);
                numberOfItems++;
                salida += itemFromStock.toString();
            }
        } else {
            Toast.makeText(this, "No hay nada en stock...!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        Toast.makeText(this, "Item presionado: " + item.toString(), Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
    }
}