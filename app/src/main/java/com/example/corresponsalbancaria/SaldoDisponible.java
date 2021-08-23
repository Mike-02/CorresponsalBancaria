package com.example.corresponsalbancaria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.corresponsalbancaria.POJOs.Corresponsal;

public class SaldoDisponible extends AppCompatActivity {

    TextView consSaldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saldo_disponible);
        getSupportActionBar().hide();
        consSaldo = findViewById(R.id.consSaldo);

        String newCedula;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newCedula = null;
            } else {
                newCedula = extras.getString("cedula");
            }
        } else {
            newCedula = (String) savedInstanceState.getSerializable("cedula");
        }


        Funciones funciones = new Funciones(this);
        Corresponsal corresponsal = funciones.getSaldo(newCedula);

        consSaldo.setText(String.valueOf(corresponsal.getBalance()));
    }
}