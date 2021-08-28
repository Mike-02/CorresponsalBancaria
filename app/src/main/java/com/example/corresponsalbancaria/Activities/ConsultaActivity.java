package com.example.corresponsalbancaria.Activities;

import static com.example.corresponsalbancaria.Constantes.CONSULTA;
import static com.example.corresponsalbancaria.Constantes.RETIRO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.example.corresponsalbancaria.SaldoDisponible;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String cedula = String.valueOf(consIdentification.getText());
        String pin = String.valueOf(consPin.getText());
        String confir = String.valueOf(consConfirmPin.getText());


        int newsaldo;
        if (cedula.equals("")){
            consIdentification.setError("El  campo no debe quedar vacio");
        } else {
            cliente.setCedula(cedula);
            if (pin.equals("")){
                consPin.setError("El  campo no debe quedar vacio");
            } else {
                cliente.setPin(pin);
                if (!funciones.validarUsu(cliente)) {
                    consIdentification.setText("");
                    consPin.setText("");
                    consConfirmPin.setText("");
                    Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();

                } else {
                    funciones.open();
                    Toast.makeText(this,"Si Existe el Usuario", Toast.LENGTH_SHORT).show();

                    if (confir.equals("")){
                        consConfirmPin.setError("El  campo no debe quedar vacio");
                    } else {

                        if (!pin.equals(confir)){

                            Toast.makeText(this, "Los pin no considen", Toast.LENGTH_SHORT).show();

                        } else {

                            if (!funciones.nuevoSaldoConsul(cliente)){

                                Toast.makeText(this,"Consulta Fallida", Toast.LENGTH_SHORT).show();
                            } else {
                                saldo(cedula);

                                SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
                                String mail = preferences.getString("correspondent_mail","");
                                corresponsal = funciones.getCorresponsal(mail);

                                newsaldo = corresponsal.getBalance() + 1000 ;
                                corresponsal.setBalance(newsaldo);
                                funciones.newSaldoCorres(corresponsal);
                                Historial historial = new Historial();
                                historial.setCedula(cedula);
                                historial.setBalance(String.valueOf(cliente.getSaldo()));
                                historial.setTransaccion(CONSULTA);
                                historial.setFecha(fecha() + " \n " + hora());
                                funciones.registroHistorial(historial);

                                Toast.makeText(this,"Consulta Exitosa", Toast.LENGTH_SHORT).show();
                                funciones.close();
                            }
                        }
                    }
                }
            }
        }
    }

    public void saldo(String cedula){
        Intent intent = new Intent(this, SaldoDisponible.class);
        intent.putExtra("cedula", cedula);
        startActivity(intent);
        finish();
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