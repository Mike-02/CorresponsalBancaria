package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegistroClienteActivity extends AppCompatActivity {

    TextInputEditText usuName;
    TextInputEditText usuIdentification;
    TextInputEditText usuPin;
    TextInputEditText usuConfirmPin;
    TextInputEditText usuBalanceClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);
        getSupportActionBar().hide();
        usuName = findViewById(R.id.usuName);
        usuIdentification = findViewById(R.id.usuCard);
        usuPin = findViewById(R.id.usuPin);
        usuBalanceClient = findViewById(R.id.usuBalance);
        usuConfirmPin = findViewById(R.id.usuConfirmPin);
    }

    public  void registroCliente(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        Cliente cliente = new Cliente();
        String name = usuName.getText().toString();
        String card = usuIdentification.getText().toString();
        String pin = usuPin.getText().toString();
        cliente.setName(name);
        cliente.setCedula(card);
        cliente.setPin(pin);
        if (usuName.getText().toString().isEmpty()){
            usuName.setError("El  campo no debe quedar vacio");
        } else if (usuIdentification.getText().toString().isEmpty()){
            usuIdentification.setError("El  campo no debe quedar vacio");
        } else if (funciones.usuarioRepetido(cliente)){
            usuName.setText("");
            usuIdentification.setText("");
            usuPin.setText("");
            usuBalanceClient.setText("");
            usuConfirmPin.setText("");
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
        } else if (usuPin.getText().toString().isEmpty()){
            usuPin.setError("El  campo no debe quedar vacio");
        } else if (usuConfirmPin.getText().toString().isEmpty()){
            usuConfirmPin.setError("El  campo no debe quedar vacio");
        }else if (usuBalanceClient.getText().toString().isEmpty()){
            usuBalanceClient.setError("El  campo no debe quedar vacio");
        } else if (usuPin.getText().toString().equals(usuConfirmPin.getText().toString())){

            int balance = Integer.parseInt(usuBalanceClient.getText().toString());
            cliente.setSaldo(balance);
            if(funciones.nuevoCliente(cliente)){
                funciones.open();
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                onBackPressed();
                funciones.close();
            } else {
                Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show();
            }

        } else{
            Toast.makeText(this, "Los pin no considen", Toast.LENGTH_SHORT).show();
        }

    }


}