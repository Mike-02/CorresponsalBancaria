package com.example.corresponsalbancaria;

public class Constantes {

    public static final String TABLE_CORRESPONDENT     = "correspondent";
    public static final String CORRESPONDENT_ID        = "correspondent_id";
    public static final String CORRESPONDENT_NAME      = "correspondent_name";
    public static final String CORRESPONDENT_DOCUMENTS = "correspondent_documents";
    public static final String CORRESPONDENT_MAIL      = "correspondent_mail";
    public static final String CORRESPONDENT_PASSWORD  = "correspondent_password";
    public static final String CORRESPONDENT_BALANCE   = "correspondent_balance";

    public static final String TABLE_CORRESPONDENT_DATA =
            " CREATE TABLE " + TABLE_CORRESPONDENT + "(" +
            CORRESPONDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CORRESPONDENT_NAME + " TEXT, " +
            CORRESPONDENT_DOCUMENTS + " TEXT, " +
            CORRESPONDENT_MAIL + " TEXT," +
            CORRESPONDENT_PASSWORD + " TEXT," +
            CORRESPONDENT_BALANCE + " TEXT); ";

    public static final String DELETE_DATA =
            " DROP TABLE " + TABLE_CORRESPONDENT;

    //
    public static final String TABLE_CLIENT   = "client";
    public static final String CLIENT_NAME    = "client_name";
    public static final String CLIENT_CEDULA  = "client_cedula";
    public static final String CLIENT_PIN     = "client_pin";
    public static final String CLIENT_BALANCE = "client_saldo";

    public static final String TABLE_CLIENT_DATA =
            " CREATE TABLE " + TABLE_CLIENT + "(" +
                    CLIENT_NAME + " TEXT, " +
                    CLIENT_CEDULA + " TEXT, " +
                    CLIENT_PIN + " TEXT," +
                    CLIENT_BALANCE + " TEXT); ";

    public static final String DELETE_DATA_CLIENT =
            " DROP TABLE " + TABLE_CORRESPONDENT;

    //
    public static final String TABLE_REGISTRO   = "registro";
    public static final String REGISTRO_CEDULA    = "registro_cedula";
    public static final String REGISTRO_BALANCE = "registro_balance";
    public static final String REGISTRO_TRANSACCION = "registro_transaccion";
    public static final String REGISTRO_FECHA = "registro_fecha";

    public static final String TABLE_REGISTRO_DATA =
            " CREATE TABLE " + TABLE_REGISTRO + "(" +
                    REGISTRO_CEDULA + " TEXT, " +
                    REGISTRO_BALANCE + " TEXT, " +
                    REGISTRO_TRANSACCION + " TEXT, " +
                    REGISTRO_FECHA + " TEXT); ";

    public static final String DELETE_DATA_REGISTRO =
            " DROP TABLE " + TABLE_REGISTRO;
}
