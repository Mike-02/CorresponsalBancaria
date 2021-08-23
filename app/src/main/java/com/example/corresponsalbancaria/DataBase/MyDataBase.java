package com.example.corresponsalbancaria.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.corresponsalbancaria.Constantes;
import com.example.corresponsalbancaria.POJOs.Cliente;

public class MyDataBase extends SQLiteOpenHelper {


    private Context context;
    public static final String DATABASE_NAME = "MyDataBase.db";
    private static final int DATABASE_VERSION = 1;

    public MyDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.TABLE_CORRESPONDENT_DATA);
        db.execSQL(Constantes.TABLE_CLIENT_DATA);
        db.execSQL(Constantes.TABLE_REGISTRO_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constantes.DELETE_DATA);
        db.execSQL(Constantes.DELETE_DATA_CLIENT);
        db.execSQL(Constantes.DELETE_DATA_REGISTRO);
        onCreate(db);
    }


}
