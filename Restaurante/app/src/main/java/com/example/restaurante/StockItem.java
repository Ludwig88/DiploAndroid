package com.example.restaurante;

import androidx.annotation.NonNull;

public class StockItem {
    private String m_sItem;
    private float m_fPrecio;
    private int m_EestadoPedido;

    public StockItem(String item, float precio){
        m_sItem = item;
        m_fPrecio = precio;
        //m_EestadoPedido = estadoPedido;
    }

    public String getItem(){
        return m_sItem;
    }

    public float getPrecio(){
        return m_fPrecio;
    }

    //public int getEstadoPedido(){
    //    return m_EestadoPedido;
    //}

    @NonNull
    public String toString(){
        return "Item " + m_sItem + " = " + m_fPrecio + "\n";
    }

}
