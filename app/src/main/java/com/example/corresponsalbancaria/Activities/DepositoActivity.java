package com.example.corresponsalbancaria.Activities;

import static com.example.corresponsalbancaria.Constantes.DEPOSITO;
import static com.example.corresponsalbancaria.Constantes.RETIRO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.POJOs.Historial;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Corresponsal corresponsal = new Corresponsal();
        int newsaldo;
        String cedula = String.valueOf(depcedula.getText());
        String card = String.valueOf(depCard.getText());
        String balance = String.valueOf(depBalance.getText());
        SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
        String mail = preferences.getString("correspondent_mail","");
        corresponsal = funciones.getCorresponsal(mail);


        if (cedula.equals("")){
            depcedula.setError("El  campo no debe quedar vacio");
        } else {
            cliente.setCedula(cedula);
            if (!funciones.validarUsuDeposito(cliente)) {

                depcedula.setText("");
                depCard.setText("");
                depBalance.setText("");
                Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();

            } else {

                funciones.open();
                Toast.makeText(this, "Si Existe el Usuario", Toast.LENGTH_SHORT).show();

                if (card.equals("")){
                    depCard.setError("El  campo no debe quedar vacio");
                } else {

                    if (balance.equals("")){
                        depBalance.setError("El  campo no debe quedar vacio");

                    } else {

                        cliente.setSaldo(Integer.parseInt(balance));
                        if (corresponsal.getBalance() < cliente.getSaldo()){
                            Toast.makeText(this, "Saldo Insuficiente", Toast.LENGTH_SHORT).show();

                        } else {
                                funciones.saldoDe(cliente);
                            if (!funciones.saldoDeposito(cliente)){
                                Toast.makeText(this, "Retiro Fallido", Toast.LENGTH_SHORT).show();

                            } else {

                                newsaldo = corresponsal.getBalance() - Integer.parseInt(balance) + 1000;
                                corresponsal.setBalance(newsaldo);
                                funciones.newSaldoCorres(corresponsal);
                                Historial historial = new Historial();
                                historial.setCedula(cedula);
                                historial.setBalance(balance);
                                historial.setTransaccion(DEPOSITO);
                                historial.setFecha(fecha() + " \n " + hora());
                                funciones.registroHistorial(historial);

                                Toast.makeText(this, "Retiro exitoso", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                funciones.close();
                            }
                        }
                    }
                }
            }
        }
    }

    private  String fecha(){
        Date date = new Date();
        String formatoFecha = "yyyy-MM-dd";

        String fecha = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
            fecha = dateFormat.format(date);
        } catch (Exception e){
            Log.e("ERROR", e.toString());

        }
        return fecha;
    }

    private  String hora(){
        Date date = new Date();
        String formatoHora = "HH:mm:ss";

        String hora = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatoHora);
            hora = dateFormat.format(date);
        } catch (Exception e){
            Log.e("ERROR", e.toString());

        }
        return hora;
    }

}