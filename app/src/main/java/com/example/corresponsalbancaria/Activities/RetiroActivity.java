package com.example.corresponsalbancaria.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;



public class RetiroActivity extends AppCompatActivity {


    TextInputEditText retIdentification;
    TextInputEditText retPin;
    TextInputEditText retConfirmPin;
    TextInputEditText retMounted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);
        getSupportActionBar().hide();
        retIdentification = findViewById(R.id.retIdentification);
        retPin = findViewById(R.id.retPin);
        retConfirmPin = findViewById(R.id.retConfirmPin);
        retMounted = findViewById(R.id.retMounted);
    }

    public void procesoRetiro(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        Cliente cliente = new Cliente();
        Corresponsal corresponsal;
        String identificacion = retIdentification.getText().toString();
        String pin = retPin.getText().toString();
        cliente.setCedula(identificacion);
        cliente.setPin(pin);
        if (retIdentification.getText().toString().isEmpty()){
            retIdentification.setError("El  campo no debe quedar vacio");
        } else if (retPin.getText().toString().isEmpty()){
            retPin.setError("El  campo no debe quedar vacio");
        } else if (funciones.validarUsuario(cliente)) {

             if (retConfirmPin.getText().toString().isEmpty()){
                retConfirmPin.setError("El  campo no debe quedar vacio");
             }else {

             }

            funciones.open();
            Toast.makeText(this, "Si Existe el Usuario", Toast.LENGTH_SHORT).show();

            if (retPin.getText().toString().equals(retConfirmPin.getText().toString())){
                if (retMounted.getText().toString().isEmpty()){
                    retMounted.setError("El  campo no debe quedar vacio");
                } else {

                    int mounted = Integer.parseInt(retMounted.getText().toString());
                         cliente.setSaldo(mounted);
                    if (funciones.validarMonto(cliente)) {

                        Toast.makeText(this, "exito", Toast.LENGTH_SHORT).show();

                        if (funciones.saldo(cliente)) {

                            SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
                            String mail = preferences.getString("correspondent_mail", "");
                            corresponsal = funciones.getCorresponsal(mail);

                            int newsaldo = corresponsal.getBalance() + mounted + 2000;
                            corresponsal.setBalance(newsaldo);
                            funciones.newSaldoCorres(corresponsal);

                            Toast.makeText(this, "Retiro exitoso", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            funciones.close();

                        } else {
                            Toast.makeText(this, "Retiro Fallido", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Saldo Insuficiente", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Los pin no considen", Toast.LENGTH_SHORT).show();
            }

        } else {
            retIdentification.setText("");
            retPin.setText("");
            retConfirmPin.setText("");
            retMounted.setText("");
            Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();
        }

    }

}



