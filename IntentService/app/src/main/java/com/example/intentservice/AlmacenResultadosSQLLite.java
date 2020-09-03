package com.example.intentservice;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class AlmacenResultadosSQLLite extends SQLiteOpenHelper implements AlmacenarResultados {

    public AlmacenResultadosSQLLite(Context context, int version) {
        //Este super es el de la extensión de SQLiteHelper...
        //version = 1 ejecuta el onCreate
        //version misma version anterior no ejecuta nada
        //version distinta version anterior ejecuta el onUpgrade.
        super(context, "resultados", null, version);
    }

    @Override
    public ArrayList<?> ListaResultados() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT numero, resultado, nombre FROM resultados ORDER BY numero ASC", null);
        ArrayList<Resultado> resultados = new ArrayList<>();
        while (cursor.moveToNext()) {
            resultados.add(new Resultado(cursor.getDouble(0), cursor.getDouble(1), cursor.getString(2)));
        }
        cursor.close();
        db.close();
        return resultados;
    }

    @Override
    public void GuardarResultados(double num, double res, String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO resultados VALUES (null, " + num + "," + res + ",'" + name + "')");
        db.close();
    }

    @Override
    public void EliminarResultados(double num, double res) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM resultados WHERE numero = " + num + " AND resultado = " + res + ")");
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE resultados " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "numero DOUBLE," +
                        "resultado DOUBLE," +
                        "nombre TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //i = version anterior..
        //i1 = version actual...
        if (i != i1) {
            if (i1 == 2) {
                sqLiteDatabase.execSQL("ALTER TABLE resultados ADD COLUMN fecha DATETIME");
            }
        }
    }
}