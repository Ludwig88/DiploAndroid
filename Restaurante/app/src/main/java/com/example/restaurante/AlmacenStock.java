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

    public void populateDB()
    {
        if(!dbHasItems())
        {
            //TODO: use X qtty. random values and items from a file
            almacenarPedidos("coca-cola",125.9f,0);
            almacenarPedidos("Pepsi",120.7f,0);
            almacenarPedidos("Fanta",115.6f,0);
            almacenarPedidos("Fernet",300.1f,0);
            almacenarPedidos("Agua Mineral",100.2f,0);
            almacenarPedidos("Pancho",225.6f,0);
            almacenarPedidos("Vino Tinto",325.0f,0);
            almacenarPedidos("Choripan",200.0f,0);
            almacenarPedidos("Lomito",350.5f,0);
            almacenarPedidos("Fideos",300.8f,0);
            almacenarPedidos("Lasagna",350.9f,0);
            almacenarPedidos("Postre",150.1f,0);
            almacenarPedidos("Café",99.9f,0);
            almacenarPedidos("Asado",1560.1f,0);
            almacenarPedidos("Ensalada",230.0f,0);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE stock ADD COLUMN fecha DATETIME");
            }
        }
    }

    public boolean dbHasItems()
    {
        Integer quantityOfElements;
        try (SQLiteDatabase db = getReadableDatabase()) {
            quantityOfElements = 0;
            try (Cursor cursor = db.rawQuery("SELECT COUNT(item) FROM stock", null)) {
                while (cursor.moveToNext()) {
                    quantityOfElements = cursor.getInt(0);
                }
                cursor.close();
            } catch (Exception e) {
                Log.e("DB_STOCK", "error: " + e.getMessage());
            }
            db.close();
        }
        return quantityOfElements > 1;
    }

    public ArrayList<?> listaStockItems() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<StockItem> stockItems;
        stockItems = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT DISTINCT item, precio FROM stock", null)) {
            while (cursor.moveToNext()) {
                stockItems.add(new StockItem( cursor.getString(0), cursor.getFloat(1), 0));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DB_STOCK","error: " + e.getMessage());
        }
        db.close();
        return stockItems;
    }

    public void actualizarEstadoItem(String item, int estado) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE stock SET estado = " + estado + " WHERE item = " + item + " )");
        db.close();
    }

    public void almacenarPedidos(String item, float precio, int estado) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO stock VALUES (null, " + "'"+ item + "', " + precio + " , " + estado + " )");
        db.close();
    }

    public void eliminarPedidos(String item) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM stock WHERE item = " + item + ")");
        db.close();
    }
}
