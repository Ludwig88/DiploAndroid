package com.example.restaurante;

import androidx.annotation.NonNull;

enum Estado {
    EN_ESPERA, //ES necesario?
    PREPARANDO,
    LISTO,
    ENTREGADO,
    DESCONOCIDO
};

public class Pedido {
    private Integer m_duniqueID; //Ver diferencia con DOuble
    private Integer m_iPedidoID; //Ver diferencia con INTEGER
    private String m_sMozo;
    private int m_iMesaNum;
    private String m_sItem;
    private float m_fPrecio;
    private int m_EestadoPedido;
    //private Estado m_EestadoPedido;

    public Pedido(Integer uniqID, Integer pedID, String mozo, int mesaNum, String item, float precio, int estadoPedido){
        m_duniqueID = uniqID;
        m_iPedidoID = pedID;
        m_sMozo = mozo;
        m_iMesaNum = mesaNum;
        m_sItem = item;
        m_fPrecio = precio;
        m_EestadoPedido = estadoPedido;
    }

    public Integer getuniqueID(){
        return m_duniqueID;
    }

    public String getMozo(){
        return m_sMozo;
    }

    public Integer getPedidoID(){
        return m_iPedidoID;
    }

    public int getesaNum(){
        return m_iMesaNum;
    }

    public String getItem(){
        return m_sItem;
    }

    public float getPrecio(){
        return m_fPrecio;
    }

    public int getEstadoPedido(){
        return m_EestadoPedido;
    }

    public Estado getEstadoPedidoEnum(){
        Estado actual;
        switch (m_EestadoPedido){
            case 0:
                actual = Estado.EN_ESPERA;
                break;
            case 1:
                actual = Estado.PREPARANDO;
                break;
            case 2:
                actual = Estado.LISTO;
                break;
            case 3:
                actual = Estado.ENTREGADO;
                break;
            default:
                actual = Estado.DESCONOCIDO;
                break;
        }
        return actual;
    }

    @NonNull
    public String toString(){
        return "Pedido[" + m_duniqueID + "|" + m_iPedidoID + "] - Mozo " + m_sMozo + "- Mesa " + m_iMesaNum + " Estado " + m_EestadoPedido + "\n";
    }

}
