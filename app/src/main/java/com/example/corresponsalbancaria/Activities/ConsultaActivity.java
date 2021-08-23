package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.R;
import com.example.corresponsalbancaria.SaldoDisponible;
import com.google.android.material.textfield.TextInputEditText;

public class ConsultaActivity extends AppCompatActivity {

    TextInputEditText consIdentification;
    TextInputEditText consPin;
    TextInputEditText consConfirmPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        getSupportActionBar().hide();
        consIdentification = findViewById(R.id.consIdentification);
        consPin = findViewById(R.id.consPin);
        consConfirmPin = findViewById(R.id.consConfirmPin);
    }

    public void validarUsu(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        Cliente cliente = new Cliente();
        Corresponsal corresponsal;
        String cedula = consIdentification.getText().toString();
        String pin = consPin.getText().toString();
        cliente.setCedula(cedula);
        cliente.setPin(pin);
        int newsaldo;
        if (consIdentification.getText().toString().isEmpty()){
            consIdentification.setError("El  campo no debe quedar vacio");
        } else if (consPin.getText().toString().isEmpty()){
            consPin.setError("El  campo no debe quedar vacio");
        } else
            if (funciones.validarUsu(cliente)) {

            funciones.open();
            Toast.makeText(this,"Si Existe el Usuario", Toast.LENGTH_SHORT).show();

                if (consConfirmPin.getText().toString().isEmpty()){
                    consConfirmPin.setError("El  campo no debe quedar vacio");
                } else if (consPin.getText().toString().equals(consConfirmPin.getText().toString())){

                 if (funciones.nuevoSaldoConsul(cliente)){
                    saldo(cedula);

                    SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
                    String mail = preferences.getString("correspondent_mail","");
                    corresponsal = funciones.getCorresponsal(mail);

                    newsaldo = corresponsal.getBalance() + 1000 ;
                    corresponsal.setBalance(newsaldo);
                    funciones.newSaldoCorres(corresponsal);

                    Toast.makeText(this,"Consulta Exitosa", Toast.LENGTH_SHORT).show();
                    funciones.close();
                } else {
                    Toast.makeText(this,"Consulta Fallida", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Los pin no considen", Toast.LENGTH_SHORT).show();
            }

        } else {
                consIdentification.setText("");
                consPin.setText("");
                consConfirmPin.setText("");
            Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void saldo(String cedula){
        Intent intent = new Intent(this, SaldoDisponible.class);
        intent.putExtra("cedula", cedula);
        startActivity(intent);
        finish();
    }

}