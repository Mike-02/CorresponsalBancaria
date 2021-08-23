package com.example.corresponsalbancaria.POJOs;

import android.content.ContentValues;

import com.example.corresponsalbancaria.Constantes;

public class Cliente {
    private String name;
    private String cedula;
    private String pin;
    private int saldo;

    public Cliente(){

    }

    public Cliente( String name, String cedula, String pin, int saldo){

        this.setName(name);
        this.setCedula(cedula);
        this.setPin(pin);
        this.setSaldo(saldo);

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }



    public ContentValues valorCliente(){
        ContentValues values = new ContentValues();
        values.put(Constantes.CLIENT_NAME, name);
        values.put(Constantes.CLIENT_CEDULA, cedula);
        values.put(Constantes.CLIENT_PIN, pin);
        values.put(Constantes.CLIENT_BALANCE, saldo);
        return values;
    }

    public ContentValues client(){
        ContentValues values = new ContentValues();
        values.put(Constantes.CLIENT_BALANCE, saldo);
        return values;
    }



}