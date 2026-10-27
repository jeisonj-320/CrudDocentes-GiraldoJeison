package com.ucsur.cruddocentes.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "docentes.db";
    private static final int DB_VERSION = 1;
    public static final String TABLA = "docente";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA + " (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, paterno TEXT NOT NULL, materno TEXT," +
                "sexo TEXT, sueldo REAL, hijos INTEGER)");
        // Datos de prueba iniciales
        db.execSQL("INSERT INTO docente (nombre,paterno,materno,sexo,sueldo,hijos) " +
                "VALUES ('Gloria','Mora','Lescano','Femenino',3000,0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }
}