package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;


import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mail;
    TextInputEditText password;
    private static final Pattern PASSWORD_REGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.\\S+$)"  +
                    ".{4,10}"     +
                    "$"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
    }

    public void inicioSecion(View view){
        Funciones funciones = new Funciones(getApplicationContext());
        String mail = this.mail.getText().toString();
        String password = this.password.getText().toString();
        Corresponsal corresponsal = new Corresponsal();
        corresponsal.setMail(mail);
        corresponsal.setPassword(password);
        if (this.mail.getText().toString().isEmpty()){
            this.mail.setError("El  campo no debe quedar vacio");
        } else if (! PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches()) {
            this.mail.setError("Ingrese un correo valido");

        } else if (this.password.getText().toString().isEmpty()){
            this.password.setError("El  campo no debe quedar vacio");

        } else if(!PASSWORD_REGEX.matcher(password).matches()) {
            this.password.setError("Ingrese una contraseña valida");

        } else if (funciones.validarUsuario(corresponsal)){
            funciones.open();
            pantallaPrincipal(mail);
            funciones.shardG(mail);
            Toast toast = Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT);
            toast.show();
            funciones.close();
        } else {
            Toast toast = Toast.makeText(this, "Datos Incorrectos", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void pantallaPrincipal(String mail){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("mail", mail);
        startActivity(intent);
        finish();
    }


    public void registro(View view){
        Intent intent = new Intent(this, RegistroCorresponsalActivity.class);
        startActivity(intent);
    }

}