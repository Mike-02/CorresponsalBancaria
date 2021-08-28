package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class RegistroCorresponsalActivity extends AppCompatActivity {

    TextInputEditText coName;
    TextInputEditText coDocuments;
    TextInputEditText coMail;
    TextInputEditText coPassword;
    TextInputEditText coConfirPassword;
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
        setContentView(R.layout.activity_registro_corresponsal);
        getSupportActionBar().hide();
        coName = findViewById(R.id.coName);
        coDocuments = findViewById(R.id.coDocuments);
        coMail = findViewById(R.id.coMail);
        coPassword = findViewById(R.id.coPassword);
        coConfirPassword = findViewById(R.id.coConfirPassword);

    }

    public  void registroCorresponsal(View view) {
        Funciones funciones = new Funciones(getApplicationContext());
        Corresponsal corresponsal = new Corresponsal();
        String name = coName.getText().toString();
        String documents = coDocuments.getText().toString();
        String mail = coMail.getText().toString();
        String password = coPassword.getText().toString();
        corresponsal.setName(name);
        corresponsal.setDocuments(documents);
        corresponsal.setMail(mail);
        corresponsal.setPassword(password);
        corresponsal.setBalance(1000000);

        if (coName.getText().toString().isEmpty()) {
            coName.setError("El  campo no debe quedar vacio");
        } else if (coDocuments.getText().toString().isEmpty()) {
            coDocuments.setError("El  campo no debe quedar vacio");
        } else if (funciones.corresponsalRepetido(corresponsal)) {
            coName.setText("");
            coDocuments.setText("");
            coMail.setText("");
            coPassword.setText("");
            coConfirPassword.setText("");
            Toast.makeText(this, "El corresponsal ya existe", Toast.LENGTH_SHORT).show();
        } else if (coMail.getText().toString().isEmpty()) {
            coMail.setError("El  campo no debe quedar vacio");
        } else if (! PatternsCompat.EMAIL_ADDRESS.matcher(mail).matches()) {
            this.coMail.setError("Ingrese un correo valido");
        } else if (funciones.correoRepetido(corresponsal)) {
            coName.setText("");
            coDocuments.setText("");
            coMail.setText("");
            coPassword.setText("");
            coConfirPassword.setText("");
            Toast.makeText(this, "El correo ya esta registrado", Toast.LENGTH_SHORT).show();
        } else if (coPassword.getText().toString().isEmpty()) {
            coPassword.setError("El  campo no debe quedar vacio");
        } else if(!PASSWORD_REGEX.matcher(password).matches()) {
            this.coPassword.setError("Ingrese una contraseña valida");
        } else if (coConfirPassword.getText().toString().isEmpty()) {
            coConfirPassword.setError("El  campo no debe quedar vacio");
        }else if (coPassword.getText().toString().equals(coConfirPassword.getText().toString())) {

                if(funciones.nuevoCorresponsal(corresponsal)){
                    funciones.open();
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    funciones.close();
                } else{
                    Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show();
                }

        }else {
            Toast.makeText(this, "Las contraseñas no considen", Toast.LENGTH_SHORT).show();
        }
    }
}