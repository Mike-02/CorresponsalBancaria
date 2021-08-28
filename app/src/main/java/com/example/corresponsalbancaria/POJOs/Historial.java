package com.example.corresponsalbancaria.POJOs;

import android.content.ContentValues;

import com.example.corresponsalbancaria.Constantes;

public class Historial {
    private String cedula;
    private String balance;
    private String transaccion;
    private String fecha;

    public Historial(){

    }

    public Historial(String cedula, String balance , String transaccion, String fecha){
        this.setCedula(cedula);
        this.setBalance(balance);
        this.setTransaccion(transaccion);
        this.setFecha(fecha);

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ContentValues historial(){
        ContentValues values = new ContentValues();
        values.put(Constantes.REGISTRO_CEDULA,cedula);
        values.put(Constantes.REGISTRO_BALANCE,balance);
        values.put(Constantes.REGISTRO_TRANSACCION,transaccion);
        values.put(Constantes.REGISTRO_FECHA,fecha);

        return values;
    }
}


