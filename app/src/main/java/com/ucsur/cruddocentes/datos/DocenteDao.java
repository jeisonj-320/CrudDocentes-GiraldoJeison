package com.ucsur.cruddocentes.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ucsur.cruddocentes.modelo.Docente;

import java.util.ArrayList;
import java.util.List;

public class DocenteDao {
    private final DbHelper helper;

    public DocenteDao(Context context) { helper = new DbHelper(context); }

    private ContentValues toValues(Docente d) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", d.getNombre());
        cv.put("paterno", d.getPaterno());
        cv.put("materno", d.getMaterno());
        cv.put("sexo", d.getSexo());
        cv.put("sueldo", d.getSueldo());
        cv.put("hijos", d.getHijos());
        return cv;
    }

    private Docente desdeCursor(Cursor c) {
        return new Docente(
                c.getInt(c.getColumnIndexOrThrow("codigo")),
                c.getString(c.getColumnIndexOrThrow("nombre")),
                c.getString(c.getColumnIndexOrThrow("paterno")),
                c.getString(c.getColumnIndexOrThrow("materno")),
                c.getString(c.getColumnIndexOrThrow("sexo")),
                c.getDouble(c.getColumnIndexOrThrow("sueldo")),
                c.getInt(c.getColumnIndexOrThrow("hijos")));
    }

    // CREATE: devuelve el código generado o -1 si falla
    public long insertar(Docente d) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(DbHelper.TABLA, null, toValues(d));
        db.close();
        return id;
    }

    // READ: todos los docentes, o filtrados por apellido paterno
    public List<Docente> listar(String paterno) {
        List<Docente> lista = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c;
        if (paterno == null || paterno.isEmpty()) {
            c = db.rawQuery("SELECT * FROM docente ORDER BY paterno", null);
        } else {
            c = db.rawQuery("SELECT * FROM docente WHERE paterno LIKE ?",
                    new String[]{ paterno + "%" });
        }
        while (c.moveToNext()) {
            lista.add(desdeCursor(c));
        }
        c.close();
        db.close();
        return lista;
    }

    // READ: un solo docente por código (null si no existe)
    public Docente buscar(int codigo) {
        Docente d = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM docente WHERE codigo = ?",
                new String[]{ String.valueOf(codigo) });
        if (c.moveToFirst()) {
            d = desdeCursor(c);
        }
        c.close();
        db.close();
        return d;
    }

    // UPDATE: devuelve las filas afectadas
    public int actualizar(Docente d) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int n = db.update(DbHelper.TABLA, toValues(d), "codigo = ?",
                new String[]{ String.valueOf(d.getCodigo()) });
        db.close();
        return n;
    }

    // DELETE: devuelve las filas eliminadas
    public int eliminar(int codigo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int n = db.delete(DbHelper.TABLA, "codigo = ?",
                new String[]{ String.valueOf(codigo) });
        db.close();
        return n;
    }
}