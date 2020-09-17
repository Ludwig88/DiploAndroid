package com.example.restaurante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlmacenStock extends SQLiteOpenHelper  {
    public AlmacenStock(@Nullable Context context, int version) {
        super(context, "StockRestaurant", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE stock " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "item TEXT," +
                        "precio DOUBLE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE stock ADD COLUMN fecha DATETIME");
            }
        }
    }

    public ArrayList<?> listaPedidos() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<StockItem> stockItems;
        stockItems = new ArrayList<>();
        //TODO: Agregar DIstinct!
        try (Cursor cursor = db.rawQuery("SELECT _id, item, precio FROM stock", null)) {
            while (cursor.moveToNext()) {
                stockItems.add(new StockItem( cursor.getString(1), cursor.getFloat(2)));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("COCINA","error: " + e.getMessage());
        }
        db.close();
        return stockItems;
    }

    public void almacenarPedidos(String item, float precio) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO stock VALUES (null, " + "'"+ item + "', " + precio + " )");
        db.close();
    }

    public void eliminarPedidos(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM stock WHERE item = " + item + ")");
        db.close();
    }
}
