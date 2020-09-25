package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Mozo extends ListActivity {

    public AlmacenPedidos m_almacenPedidos;
    public AlmacenStock m_almacenStock;
    public ArrayList<StockItem> m_localStockItems;
    private StockItemAdapter stockItemAdapter;
    public float m_fPrecioTotal = 0.0f;
    private int m_iUltimoPedidoId;

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
                UpdateTotalCost(itemFromStock.getPrecio());
                stockItemAdapter.notifyDataSetChanged();
            }
        }
        super.onListItemClick(l, v, position, id);
    }

    public void UpdateTotalCost(float nuevoItemPrecio){
        m_fPrecioTotal = m_fPrecioTotal + nuevoItemPrecio;
        TextView precioTot = findViewById(R.id.TotCost);
        String s_prectioTot = precioTot.getText().toString();
        precioTot.setText(s_prectioTot.replace("Costo Total:","Costo Total: " + String.valueOf(m_fPrecioTotal)));
    }

    private String getNameMozoActual(){
        TextView NombreMozo = findViewById(R.id.editTextMozoVal);
        return NombreMozo.toString();
    }

    private String getNumMesaActual(){
        TextView NumMesaActual = findViewById(R.id.editTextMesaVal);
        return NumMesaActual.toString();
    }

    private void UpdateUltimoPedido(){
        int ultimoPedido = m_almacenPedidos.getUltimoPedido();
        m_iUltimoPedidoId = ultimoPedido + 1;
    }

    public void crearOrden(View view){
        //Loop por todos los items que tengan cantidad distinta a 0
        for (StockItem itemFromStock : m_localStockItems) {
            UpdateUltimoPedido();
            if(itemFromStock.getCantidad() != 0){

                String m_mozoName = getNameMozoActual();
                String m_mesaNum = getNumMesaActual();
                float itemPrecio = itemFromStock.getPrecio();
                String itemName = itemFromStock.getItemName();
                int estadoPedido = itemFromStock.getEstadoPedido(); //lo cambio directamente?
                int itemCant = itemFromStock.getCantidad();
                m_almacenPedidos.almacenarPedidos(m_iUltimoPedidoId,m_mozoName,m_mesaNum,itemName, itemPrecio,estadoPedido,itemCant);
            }
        }
        Toast.makeText(this, "Orden " + m_iUltimoPedidoId + " Enviada ", Toast.LENGTH_LONG).show();
    }
}