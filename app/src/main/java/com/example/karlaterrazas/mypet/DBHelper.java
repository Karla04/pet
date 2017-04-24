package com.example.karlaterrazas.mypet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table usuarios(correo text,contrasena text)");
        db.execSQL("insert into usuarios values('1715110631@utec-tgo.edu.mx','toby')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table usuarios(nombre text, apellido_paterno text, apellido_materno text, correo text, contrasena text)");
        db.execSQL("insert into usuarios values('Karla','Terrazas','Lopez','1715110631@utec-tgo.edu.mx','toby')");
    }
}
