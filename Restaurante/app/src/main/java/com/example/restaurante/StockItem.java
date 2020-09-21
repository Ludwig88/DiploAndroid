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
    private NEW_ORDER_STATE m_EState = NEW_ORDER_STATE.NON_SELECTED;

    public StockItem(String item, float precio, int state){
        m_sItem = item;
        m_fPrecio = precio;
        m_iState = state;
        //m_EestadoPedido = estadoPedido;
    }

    public String getItemName(){
        return m_sItem;
    }

    public float getPrecio(){ return m_fPrecio; }

    public int getEstadoPedido(){ return m_EestadoPedido; }

    public void setEstadoPedido(int estado){ m_EestadoPedido = estado; }

    @NonNull
    public String toString(){
        return "Item " + m_sItem + " = " + m_fPrecio + "\n";
    }

}
