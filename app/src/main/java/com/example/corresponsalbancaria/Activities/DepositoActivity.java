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

public class DepositoActivity extends AppCompatActivity {

    TextInputEditText depcedula;
    TextInputEditText depCard;
    TextInputEditText depBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        getSupportActionBar().hide();
        depcedula = findViewById(R.id.depcedula);
        depCard = findViewById(R.id.depCard);
        depBalance = findViewById(R.id.depBalance);
    }

    public void procesoDeposito(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        Cliente cliente = new Cliente();
        Corresponsal corresponsal;
        int newsaldo;
        String cedula = depcedula.getText().toString();
        cliente.setCedula(cedula);

        if (depcedula.getText().toString().isEmpty()){
            depcedula.setError("El  campo no debe quedar vacio");
        } else if (funciones.validarUsuDeposito(cliente)) {

            funciones.open();
            Toast.makeText(this, "Si Existe el Usuario", Toast.LENGTH_SHORT).show();

            if (depCard.getText().toString().isEmpty()){
                depCard.setError("El  campo no debe quedar vacio");
            } else if (depBalance.getText().toString().isEmpty()){
                depBalance.setError("El  campo no debe quedar vacio");

            } else if (funciones.saldoDeposito(cliente)){

                int balance = Integer.parseInt(depBalance.getText().toString());
                cliente.setSaldo(balance);

                SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
                String mail = preferences.getString("correspondent_mail","");
                corresponsal = funciones.getCorresponsal(mail);

                newsaldo = corresponsal.getBalance() - balance + 1000;
                corresponsal.setBalance(newsaldo);
                funciones.newSaldoCorres(corresponsal);

                Toast.makeText(this, "Retiro exitoso", Toast.LENGTH_SHORT).show();
                onBackPressed();
                funciones.close();
            } else {
                Toast.makeText(this, "Retiro Fallido", Toast.LENGTH_SHORT).show();
            }

        } else {
            depcedula.setText("");
            depCard.setText("");
            depBalance.setText("");
            Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();
        }
    }

}