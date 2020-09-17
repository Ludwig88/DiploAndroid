package com.example.restaurante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlmacenPedidos extends SQLiteOpenHelper  {
    public AlmacenPedidos(@Nullable Context context, int version) {
        super(context, "AlmacenRestaurant", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE pedidos " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "pedidoNum INTEGER," +
                        "mozo TEXT," +
                        "mesaNum INTEGER," +
                        "item TEXT," +
                        "precio DOUBLE," + //CHEQUEAR!
                        "estado INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE pedidos ADD COLUMN fecha DATETIME");
            }
        }
    }

    public ArrayList<?> listaPedidos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Pedido> pedidos;
        pedidos = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT _id, pedidoNum, mozo, mesaNum, item, precio, estado FROM pedidos ORDER BY pedidoNum DESC", null)) {
            while (cursor.moveToNext()) {
                pedidos.add(new Pedido(cursor.getInt(0), cursor.getInt(1),
                        cursor.getString(2), cursor.getInt(3),
                        cursor.getString(4), cursor.getFloat(5), cursor.getInt(6)));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("COCINA","error: " + e.getMessage());
        }
        db.close();
        return pedidos;
    }

    public void almacenarPedidos(Integer pedID, String mozo, int mesaNum, String item, float precio, int estadoPedido) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO pedidos VALUES (null, " + pedID + ",'" + mozo + "'," + mesaNum +
                ", '"+ item + "', " + precio + ", " + estadoPedido + " )");
        db.close();
    }

    public void eliminarPedidos(Integer pedID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM pedidos WHERE pedidoNum = " + pedID + ")");
        db.close();
    }
}
