package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

public class TransferenciasActivity extends AppCompatActivity {

    TextInputEditText traIdentification;
    TextInputEditText traPin;
    TextInputEditText traConfirmPin;
    TextInputEditText traId;
    TextInputEditText traMounted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencias);
        getSupportActionBar().hide();
        traIdentification = findViewById(R.id.traIdentification);
        traPin = findViewById(R.id.traPin);
        traConfirmPin = findViewById(R.id.traConfirmPin);
        traId = findViewById(R.id.traId);
        traMounted = findViewById(R.id.traMounted);
    }

    public  void transacciones(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        String identification = traIdentification.getText().toString();
        String pin = traPin.getText().toString();
        int mounted = Integer.parseInt(traMounted.getText().toString());
        Cliente cliente = new Cliente();
        cliente.setCedula(identification);
        cliente.setPin(pin);
        cliente.setSaldo(mounted);
        if (traIdentification.getText().toString().isEmpty()){
            traIdentification.setError("El  campo no debe quedar vacio");
        } else if (traPin.getText().toString().isEmpty()) {
            traPin.setError("El  campo no debe quedar vacio");
        } else if (traConfirmPin.getText().toString().isEmpty()) {
            traConfirmPin.setError("El  campo no debe quedar vacio");
        } else if (traId.getText().toString().isEmpty()) {
            traId.setError("El  campo no debe quedar vacio");
        } else if (traMounted.getText().toString().isEmpty()) {
            traMounted.setError("El  campo no debe quedar vacio");
        }

    }
}