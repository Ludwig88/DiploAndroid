package com.example.restaurante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlmacenPedidos extends SQLiteOpenHelper  {

    private final String DB_TABLE_NAME = "listpedidos";

    public AlmacenPedidos(@Nullable Context context, int version) {
        super(context, "AlmacenRestaurant", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + DB_TABLE_NAME  +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "pedidoNum INTEGER," +
                        "mozo TEXT," +
                        "mesaNum INTEGER," +
                        "listitem TEXT," +
                        "estado INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE " + DB_TABLE_NAME  + " ADD COLUMN fecha DATETIME");
            }
        }
    }

    public int getLastOrderNumber(){
        SQLiteDatabase db = getReadableDatabase();
        int pedidoNum = 0;
        try (Cursor cursor = db.rawQuery("SELECT pedidoNum FROM " + DB_TABLE_NAME  + " ORDER BY pedidoNum DESC", null)) {
            while (cursor.moveToNext()) {
                int aux = cursor.getInt(0);
                if(aux > pedidoNum){
                    pedidoNum = aux;
                }
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
        db.close();
        return pedidoNum;
    }

    public ArrayList<?> listaPedidos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Pedido> pedidos;
        pedidos = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT _id, pedidoNum, mozo, mesaNum, listitem, estado FROM " + DB_TABLE_NAME  + " ORDER BY pedidoNum DESC", null)) {
            while (cursor.moveToNext()) {
                pedidos.add(new Pedido(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
        db.close();
        return pedidos;
    }

    public ArrayList<Integer> listaNumPedidos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> pedidosNUM;
        pedidosNUM = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT DISTINCT pedidoNum FROM " + DB_TABLE_NAME  + " ORDER BY pedidoNum DESC", null)) {
            while (cursor.moveToNext()) {
                pedidosNUM.add(cursor.getInt(0));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
        db.close();
        return pedidosNUM;
    }

    public void almacenarPedidos(Integer pedID, String mozo, String mesaNum, String ListItem, int estadoPedido) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("INSERT INTO " + DB_TABLE_NAME  + " VALUES (null, " + pedID + ",'" + mozo + "', '" + mesaNum +
                    "', '" + ListItem + "', "  + estadoPedido + " )");
            db.close();
        }
        catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
    }

    public void eliminarTodosLosPedidos() {
        ArrayList<Integer> pedidosNUM = listaNumPedidos();
        for(Integer numPed : pedidosNUM){
            eliminarPedidos(numPed);
        }
    }

    public void updateOrderState(Integer numPedido, int estado) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("UPDATE " + DB_TABLE_NAME  + " SET estado = " + estado + " WHERE pedidoNum = " + numPedido);
            db.close();
        }
        catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
    }

    public void eliminarPedidos(Integer numPedido) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("DELETE FROM " + DB_TABLE_NAME  + " WHERE pedidoNum = " + numPedido );
            db.close();
        }
        catch (Exception e){
            Log.e("DB_PEDIDOS","error: " + e.getMessage());
        }
    }
}
