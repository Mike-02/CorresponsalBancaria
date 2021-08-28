package com.example.corresponsalbancaria;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.corresponsalbancaria.DataBase.MyDataBase;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.POJOs.Historial;

import static com.example.corresponsalbancaria.Constantes.*;

import java.util.ArrayList;

@SuppressLint("Range")
public class Funciones {
    private Context context;
    private SQLiteDatabase db;
    private MyDataBase myDataBase;


    public Funciones(Context context){
        this.context = context;
        myDataBase = new MyDataBase(context);
        db = myDataBase.getReadableDatabase();
    }

    public void open(){
         db = myDataBase.getWritableDatabase();
    }

    public void close(){
        myDataBase.close();
    }

    //Registro Corresponsal
    public boolean nuevoCorresponsal(Corresponsal corresponsal){
        ContentValues values = corresponsal.valorCorresponsal();
        int ret = (int) db.insert(TABLE_CORRESPONDENT, null, values);
        if (ret > -1){
            return true;
        } else {
            return false;
        }

    }

    public boolean corresponsalRepetido(Corresponsal corresponsal) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT + " WHERE " + CORRESPONDENT_DOCUMENTS + " = '" + corresponsal.getDocuments() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    public boolean correoRepetido(Corresponsal corresponsal) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT + " WHERE " + CORRESPONDENT_MAIL + " = '" + corresponsal.getMail() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    //Login
    public boolean validarUsuario(Corresponsal corresponsal) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT + " WHERE " + CORRESPONDENT_MAIL + " = '" + corresponsal.getMail() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String usua = cursor.getString(cursor.getColumnIndex(CORRESPONDENT_MAIL));
                    String pass = cursor.getString(cursor.getColumnIndex(CORRESPONDENT_PASSWORD));
                    if (corresponsal.getMail().equals(usua) && (corresponsal.getPassword().equals(pass))) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    //Main
    public Corresponsal getCorresponsal(String mail) {
        Corresponsal corresponsal = new Corresponsal();
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT + " WHERE " + CORRESPONDENT_MAIL + " = '" + mail + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() !=0){
            while (cursor.moveToNext()){
                corresponsal.setName(cursor.getString(cursor.getColumnIndex(CORRESPONDENT_NAME)));
                corresponsal.setBalance(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CORRESPONDENT_BALANCE))));
                return corresponsal;
            }
        }
        return corresponsal;
    }

    //Registro Cliente
    public boolean nuevoCliente(Cliente cliente){
        ContentValues values = cliente.valorCliente();
        int ret = (int) db.insert(Constantes.TABLE_CLIENT,null,values);
        if (ret > -1){
            return true;
        } else {
            return false;
        }
    }

    public boolean usuarioRepetido(Cliente cliente) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA+ " = '" + cliente.getCedula() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                        return true;
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    //Retiro
    public boolean validarUsuario(Cliente cliente) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA+ " = '" + cliente.getCedula() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String cedu = cursor.getString(cursor.getColumnIndex(CLIENT_CEDULA));
                    String pin = cursor.getString(cursor.getColumnIndex(CLIENT_PIN));
                    if (cliente.getCedula().equals(cedu) && (cliente.getPin().equals(pin))) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    public boolean validarMonto(Cliente cliente) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA + " = '" + cliente.getCedula() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int bal = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CLIENT_BALANCE)));
                    if (cliente.getSaldo() < bal) {
                        int res = bal - cliente.getSaldo() - 2000;
                        cliente.setSaldo(res);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }


    public boolean saldo(Cliente cliente){
        ContentValues values = cliente.client();
        SQLiteDatabase db = this.myDataBase.getWritableDatabase();
        int ret = db.update(TABLE_CLIENT, values, CLIENT_CEDULA + " = " + cliente.getCedula(), null);
        if (ret !=0){
            return true;
        } else {
            return false;
        }

    }

    public boolean newSaldoCorres(Corresponsal corresponsal){
        ContentValues values = corresponsal.comision();
        SQLiteDatabase db = this.myDataBase.getWritableDatabase();
        int ret = db.update(TABLE_CORRESPONDENT, values, CORRESPONDENT_BALANCE, null);
        if (ret !=0){
            return true;
        } else {
            return false;
        }

    }

    //SaredPreferences
    public void shardG(String id){
        SharedPreferences preferences = context.getSharedPreferences("comision", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String mail = CORRESPONDENT_MAIL;
        editor.putString(mail,id);
        editor.commit();
    }

    public Corresponsal saldoCorresponsal (String mail) {
        Corresponsal corresponsal = new Corresponsal();
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT + " WHERE " + CORRESPONDENT_MAIL + " = '" + mail + "'";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    cursor.getString(cursor.getColumnIndex(CORRESPONDENT_ID));
                    cursor.getString(cursor.getColumnIndex(CORRESPONDENT_DOCUMENTS));
                    cursor.getString(cursor.getColumnIndex(CORRESPONDENT_NAME));
                    cursor.getString(cursor.getColumnIndex(CORRESPONDENT_MAIL));
                    cursor.getString(cursor.getColumnIndex(CORRESPONDENT_PASSWORD));
                }
            }
        } catch (Exception ex) {
            ex.toString();
        }

        return corresponsal;
    }

    //Depositos
    public boolean validarUsuDeposito(Cliente cliente) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA + " = '" + cliente.getCedula() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String cedula = cursor.getString(cursor.getColumnIndex(CLIENT_CEDULA));
                    if ((cliente.getCedula().equals(cedula))) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    public boolean validarMontoCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CORRESPONDENT+ " WHERE " + CORRESPONDENT_DOCUMENTS+ " = '" + corresponsal.getDocuments() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int bal = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CORRESPONDENT_BALANCE)));
                    if (corresponsal.getBalance() < bal) {
                        int res = bal + corresponsal.getBalance();
                        corresponsal.setBalance(res);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    public boolean saldoDeposito(Cliente cliente) {
        ContentValues values = cliente.client();
        SQLiteDatabase db = this.myDataBase.getWritableDatabase();
        int ret = db.update(TABLE_CLIENT, values, CLIENT_CEDULA + " = " + cliente.getCedula(), null);
        if (ret !=0) {
            return true;
        } else {
            return false;
        }
    }

    //Consulta
    public boolean validarUsu(Cliente cliente) {
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA+ " = '" + cliente.getCedula() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String cedu = cursor.getString(cursor.getColumnIndex(CLIENT_CEDULA));
                    String pin = cursor.getString(cursor.getColumnIndex(CLIENT_PIN));
                    int balan = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CLIENT_BALANCE)));
                    if (cliente.getCedula().equals(cedu) && (cliente.getPin().equals(pin))) {
                        int resul = balan - 1000;
                        cliente.setSaldo(resul);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

    public Corresponsal getSaldo(String cedula) {
        Corresponsal corresponsal = new Corresponsal();
        SQLiteDatabase db = this.myDataBase.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_CEDULA + " = '" + cedula + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() !=0){
            while (cursor.moveToNext()){
                corresponsal.setBalance(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CLIENT_BALANCE))));
                return corresponsal;
            }
        }
        return corresponsal;
    }
    public boolean nuevoSaldoConsul(Cliente cliente){
        ContentValues values = cliente.client();
        SQLiteDatabase db = this.myDataBase.getWritableDatabase();
        int ret = db.update(TABLE_CLIENT, values, CLIENT_CEDULA + " = " + cliente.getCedula(), null);
        if (ret !=0){
            return true;
        } else {
            return false;
        }

    }

    //historial
    public boolean registroHistorial(Historial historial){
        ContentValues values = historial.historial();
        int ret = (int) db.insert(TABLE_REGISTRO, null, values);
        if (ret > -1){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Historial> getHistorial() {
        ArrayList<Historial> historials = new ArrayList<>();
        SQLiteDatabase db = this.myDataBase.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_REGISTRO;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Historial historial = new Historial();
                historial.setCedula(cursor.getString(cursor.getColumnIndex(REGISTRO_CEDULA)));
                historial.setBalance(cursor.getString(cursor.getColumnIndex(REGISTRO_BALANCE)));
                historial.setTransaccion(cursor.getString(cursor.getColumnIndex(REGISTRO_TRANSACCION)));
                historial.setFecha(cursor.getString(cursor.getColumnIndex(REGISTRO_FECHA)));
                historials.add(historial);
            }
        }
        return historials;
    }


}