package com.example.trabalhocyclus.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "CYCLUSteste";
    private static final int DATABASE_VER = 5;
    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE usuario (" +
                    "id INT NOT NULL UNIQUE PRIMARY KEY, " +
                    "login VARCHAR(100) NOT NULL, " +
                    "senha VARCHAR(20) NOT NULL, " +
                    "nome VARCHAR(50) NOT NULL);";
    private static final String CREATE_TABLE_MENS =
            "CREATE TABLE menstruacao (" +
                    "data_inicio VARCHAR(10) NOT NULL PRIMARY KEY, " +
                    "data_fim VARCHAR(10), " +
                    "dias INT NOT NULL, "+
                    "idUsuario INT NOT NULL, FOREIGN KEY (idUsuario) REFERENCES usuario(id)); ";

    public GenericDao(Context context){
        super(context, DATABASE, null, DATABASE_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_MENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS usuario");
            db.execSQL("DROP TABLE IF EXISTS menstruacao");
            onCreate(db);
        }
    }
}
