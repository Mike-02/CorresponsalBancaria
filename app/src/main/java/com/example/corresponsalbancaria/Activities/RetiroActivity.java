package com.example.corresponsalbancaria.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import static com.example.corresponsalbancaria.Constantes.RETIRO;
import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Cliente;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.POJOs.Historial;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;


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

    public void procesoRetiro(View view) {
        Funciones funciones = new Funciones(getApplicationContext());
        Cliente cliente = new Cliente();
        Corresponsal corresponsal;
        String identificacion = String.valueOf(retIdentification.getText());
        String pin = String.valueOf(retPin.getText());

        if (identificacion.equals("")) {
            retIdentification.setError("El  campo no debe quedar vacio");
        } else {
            cliente.setCedula(identificacion);
            if (pin.equals("")) {
                retPin.setError("El  campo no debe quedar vacio");
            } else {
                cliente.setPin(pin);
                if (!funciones.validarUsuario(cliente)) {
                    retIdentification.setText("");
                    retPin.setText("");
                    retConfirmPin.setText("");
                    retMounted.setText("");
                    Toast.makeText(this, "El Usuario No Existe", Toast.LENGTH_SHORT).show();
                } else {
                    String comfirmPin = String.valueOf(retConfirmPin.getText());
                    funciones.open();
                    Toast.makeText(this, "Si Existe el Usuario", Toast.LENGTH_SHORT).show();
                    if (comfirmPin.equals("")) {
                        retConfirmPin.setError("El  campo no debe quedar vacio");
                    } else {

                        if (!pin.equals(comfirmPin)) {
                            Toast.makeText(this, "Los pin no considen", Toast.LENGTH_SHORT).show();

                        } else {
                            String mounted = String.valueOf(retMounted.getText());
                            if (mounted.equals("")) {
                                retMounted.setError("El  campo no debe quedar vacio");
                            } else {


                                cliente.setSaldo(Integer.parseInt(mounted));

                                if (!funciones.validarMonto(cliente)) {
                                    Toast.makeText(this, "Saldo Insuficiente", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(this, "exito", Toast.LENGTH_SHORT).show();

                                    if (!funciones.saldo(cliente)) {
                                        Toast.makeText(this, "Retiro Fallido", Toast.LENGTH_SHORT).show();

                                    } else {

                                        SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
                                        String mail = preferences.getString("correspondent_mail", "");
                                        corresponsal = funciones.getCorresponsal(mail);

                                        int newsaldo = corresponsal.getBalance() + Integer.parseInt(mounted) + 2000;
                                        corresponsal.setBalance(newsaldo);
                                        funciones.newSaldoCorres(corresponsal);
                                        Historial historial = new Historial();
                                        historial.setCedula(identificacion);
                                        historial.setBalance(mounted);
                                        historial.setTransaccion(RETIRO);
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



