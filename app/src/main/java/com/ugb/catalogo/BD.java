package com.ugb.catalogo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {
    public static final String dbname = "db_catalogo";
    public static final int v = 1;
    static final String sqlDb = "CREATE TABLE catalogo(idCatalogo integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text,stock text, precio text)";

    public BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlDb);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor consultar_catalogo() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM catalogo ORDER BY codigo";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public String administrar_catalogo(String id, String cod, String des, String mar, String pres, String prec, String accion, String eliminar, String s) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (accion.equals("nuevo")) {
                db.execSQL("INSERT INTO catalogo(codigo,descripcion,marca,presentacion,precio) VALUES('" + cod + "','" + des + "','" + mar + "','" + pres + "','" + prec + "')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE catalogo SET codigo='" + cod + "', descripcion='" + des + "', marca='" + mar + "', presentacion= '" + pres + "', precio= '" + prec + "' WHERE idCatalogo='" + id + "'");
            } else if (accion.equals("modificar")) {
                db.execSQL("DELETE FROM catalogo WHERE idCatalogo='" + id + "'");
            }
            return "ok";
        } catch (Exception e) {
            return "Error:" + e.getMessage();
        }
    }



}