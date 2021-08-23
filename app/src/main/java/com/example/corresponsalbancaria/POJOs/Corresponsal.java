package com.example.corresponsalbancaria.POJOs;

import android.content.ContentValues;

import com.example.corresponsalbancaria.Constantes;


public class Corresponsal {
    private int id;
    private String name;
    private String documents;
    private String mail;
    private String password;
    private int balance;

    public Corresponsal(){
    }

    public Corresponsal(int id, String name, String documents, String mail, String password, int balance){
        this.setId(id);
        this.setName(name);
        this.setDocuments(documents);
        this.setMail(mail);
        this.setPassword(password);
        this.setBalance(balance);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public ContentValues valorCorresponsal(){
        ContentValues values = new ContentValues();
        values.put(Constantes.CORRESPONDENT_NAME,name);
        values.put(Constantes.CORRESPONDENT_DOCUMENTS,documents);
        values.put(Constantes.CORRESPONDENT_MAIL,mail);
        values.put(Constantes.CORRESPONDENT_PASSWORD,password);
        values.put(Constantes.CORRESPONDENT_BALANCE, balance);

        return values;
    }
    public ContentValues comision(){
        ContentValues values = new ContentValues();
        values.put(Constantes.CORRESPONDENT_BALANCE, balance );
        return values;
    }


}