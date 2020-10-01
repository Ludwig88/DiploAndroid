package com.example.restaurante;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
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
    private ListView listView;
    private double m_LastClickTime;

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
        listView = (ListView) findViewById(android.R.id.list);
        listView.setItemsCanFocus(true);
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
        long currentTime = System.currentTimeMillis();
        boolean isReset = false;
        if(currentTime - m_LastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
            isReset = true;
        }
        m_LastClickTime = currentTime;
        updateCantValue(position, isReset);
        super.onListItemClick(l, v, position, id);
    }

    private void updateCantValue(int position, boolean isReset){
        Object item = getListAdapter().getItem(position);
        for (StockItem itemFromStock : m_localStockItems) {
            if(((StockItem)item).getItemName().equals(itemFromStock.getItemName())){
                int cantidad_actual = itemFromStock.getCantidad();
                if(!isReset){
                    itemFromStock.setCantidadItem(isReset? 0 : ++cantidad_actual);
                    UpdateTotalCost(itemFromStock.getPrecio());
                    Toast.makeText(this, "doble click reinicia cuenta ", Toast.LENGTH_SHORT).show();
                }
                else{
                    UpdateTotalCost((-1) * cantidad_actual * itemFromStock.getPrecio());
                    itemFromStock.setCantidadItem(0);
                }
                stockItemAdapter.notifyDataSetChanged();
            }
        }
    }

    public void UpdateTotalCost(float nuevoItemPrecio){
        m_fPrecioTotal = m_fPrecioTotal + nuevoItemPrecio;
        TextView precioTot = findViewById(R.id.TotCost);
        String s_prectioTot = precioTot.getText().toString();
        String cost_title = s_prectioTot.substring(0,s_prectioTot.indexOf(":") + 1);
        precioTot.setText( cost_title + " " + String.valueOf(m_fPrecioTotal));
    }

    private String getNameMozoActual(){
        TextView NombreMozo = findViewById(R.id.editTextMozoVal);
        String nombre_mozo = NombreMozo.getText().toString();
        return nombre_mozo;
    }

    private String getNumMesaActual(){
        TextView NumMesaActual = findViewById(R.id.editTextMesaVal);
        String num_MesaActual = NumMesaActual.getText().toString();
        return num_MesaActual;
    }

    private void UpdateUltimoPedido(){
        int ultimoPedido = m_almacenPedidos.getLastOrderNumber();
        m_iUltimoPedidoId = ultimoPedido + 1;
    }

    public void crearOrden(View view){
        UpdateUltimoPedido();
        StringBuilder ListItem = new StringBuilder();
        String itemName;
        int itemCant;
        //obtengo los valores fijos en cada pedido
        String m_mozoName = getNameMozoActual();
        if(m_mozoName.isEmpty())
        {
            Toast.makeText(this, "ERROR: Ingresar Nombre Mozo! ", Toast.LENGTH_LONG).show();
            return;
        }
        String m_mesaNum = getNumMesaActual();
        if(m_mesaNum.isEmpty())
        {
            Toast.makeText(this, "ERROR: Ingresar Numero de mesa! ", Toast.LENGTH_LONG).show();
            return;
        }
        //Loop por todos los items que tengan cantidad distinta a 0
        for (StockItem itemFromStock : m_localStockItems) {
            if(itemFromStock.getCantidad() != 0){
                itemName = itemFromStock.getItemName();
                itemCant = itemFromStock.getCantidad();
                ListItem.append(itemName).append(" * ").append(itemCant).append(", ");
            }
        }
        UpdateTotalCost(0.0f);
        m_almacenPedidos.almacenarPedidos(m_iUltimoPedidoId,m_mozoName,m_mesaNum,ListItem.toString(),1);
        Toast.makeText(this, "Orden " + m_iUltimoPedidoId + " Enviada ", Toast.LENGTH_LONG).show();
    }
}