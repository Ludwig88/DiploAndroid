package com.example.restaurante;

import androidx.annotation.NonNull;

enum NEW_ORDER_STATE{
    SELECTED,
    NON_SELECTED
}

public class StockItem {
    private String m_sItem;
    private float m_fPrecio;
    private int m_EestadoPedido;
    private int m_iState;
    private int m_iCantidad;
    private NEW_ORDER_STATE m_EState = NEW_ORDER_STATE.NON_SELECTED;

    public StockItem(String item, float precio, int state, int cantidad){
        m_sItem = item;
        m_fPrecio = precio;
        m_iState = state;
        m_iCantidad = cantidad;
        //m_EestadoPedido = estadoPedido;
    }

    public String getItemName(){
        return m_sItem;
    }

    public float getPrecio(){ return m_fPrecio; }

    public int getCantidad(){ return m_iCantidad; }

    public int getEstadoPedido(){ return m_EestadoPedido; }

    public void setEstadoPedido(int estado){ m_EestadoPedido = estado; }

    public void setCantidadItem(int cant){ m_iCantidad = cant; }

    @NonNull
    public String toString(){
        return "Item " + m_sItem + " | precio = " + m_fPrecio + " | qtty: " + m_iCantidad +"\n";
    }

}
