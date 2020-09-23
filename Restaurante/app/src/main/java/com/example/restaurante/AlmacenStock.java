package com.example.restaurante;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlmacenStock extends SQLiteOpenHelper  {

    static final String TABLE_NAME = "stock_i";

    public AlmacenStock(@Nullable Context context, int version) {
        super(context, "StockItemsRestaurant", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + " " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "item TEXT," +
                        "estado INTEGER, " +
                        "cantidad INTEGER, " +
                        "precio DOUBLE)"
        );
    }

    public void populateDB()
    {
        if(!dbHasItems())
        {
            //TODO: use X qtty. random values and items from a file
            almacenarItemEnStock("coca-cola",125.9f     ,0,0);
            almacenarItemEnStock("Pepsi",120.7f         ,0,0);
            almacenarItemEnStock("Fanta",115.6f         ,0,0);
            almacenarItemEnStock("Fernet",300.1f        ,0,0);
            almacenarItemEnStock("Agua Mineral",100.2f  ,0,0);
            almacenarItemEnStock("Pancho",225.6f        ,0,0);
            almacenarItemEnStock("Vino Tinto",325.0f    ,0,0);
            almacenarItemEnStock("Choripan",200.0f      ,0,0);
            almacenarItemEnStock("Lomito",350.5f        ,0,0);
            almacenarItemEnStock("Fideos",300.8f        ,0,0);
            almacenarItemEnStock("Lasagna",350.9f       ,0,0);
            almacenarItemEnStock("Postre",150.1f        ,0,0);
            almacenarItemEnStock("Café",99.9f           ,0,0);
            almacenarItemEnStock("Asado",1560.1f        ,0,0);
            almacenarItemEnStock("Ensalada",230.0f      ,0,0);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN fecha DATETIME");
            }
        }
    }

    public boolean dbHasItems()
    {
        Integer quantityOfElements;
        try (SQLiteDatabase db = getReadableDatabase()) {
            quantityOfElements = 0;
            try (Cursor cursor = db.rawQuery("SELECT COUNT(item) FROM " + TABLE_NAME + "", null)) {
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

    public ArrayList<StockItem> listaStockItems() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<StockItem> stockItems;
        stockItems = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT DISTINCT item, precio, estado, cantidad FROM " + TABLE_NAME + "", null)) {
            while (cursor.moveToNext()) {
                String item = cursor.getString(0);
                float precio = cursor.getFloat(2);
                int estado = cursor.getInt(1);
                int cantidad = cursor.getInt(3);
                stockItems.add(new StockItem( item, precio, estado, cantidad));
            }
            cursor.close();
        }catch (Exception e){
            Log.e("DB_STOCK","error: " + e.getMessage());
        }
        db.close();
        return stockItems;
    }

    public void actualizarCantidadItem(String item, int cantidad) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("UPDATE stock SET cantidad = " + cantidad + " WHERE item = " + item + " )");
            db.close();
        }
        catch (Exception e){
            Log.e("DB_STOCK","error: " + e.getMessage());
        }
    }

    public void actualizarEstadoItem(String item, int estado) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_NAME + " SET estado = " + estado + " WHERE item = " + item + " )");
            db.close();
        }
        catch (Exception e){
            Log.e("DB_STOCK","error: " + e.getMessage());
        }
    }

    public void almacenarItemEnStock(String item, float precio, int estado, int cantidad) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String query = "INSERT INTO " + TABLE_NAME + " VALUES (null, " + "'" + item + "', " + precio + " , " + estado + " , " + cantidad + " )";
            db.execSQL(query);
            db.close();
        }catch (Exception ex){
            Log.e("DB_STOCK","error: " + ex.getMessage());
        }
    }

    public void eliminarPedidos(String item) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE item = " + item + ")");
            db.close();
        }
        catch (Exception e){
            Log.e("DB_STOCK","error: " + e.getMessage());
        }
    }
}
