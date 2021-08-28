package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Corresponsal;
import com.example.corresponsalbancaria.R;

public class MainActivity extends AppCompatActivity {

    TextView nameCorres;
    TextView balanceCorres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        nameCorres = findViewById(R.id.nameCorres);
        balanceCorres = findViewById(R.id.balanceCorres);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Funciones funciones = new Funciones(getApplicationContext());
        SharedPreferences preferences = getSharedPreferences("comision", Context.MODE_PRIVATE);
        String mail = preferences.getString("correspondent_mail","");
        funciones.saldoCorresponsal(mail);
        Corresponsal corresponsal = funciones.getCorresponsal(mail);

        nameCorres.setText("Corresponsal: "+ corresponsal.getName());
        balanceCorres.setText(String.valueOf("Balance: "+ corresponsal.getBalance()));

    }

    public void registroClient(View view){
        Intent intent = new Intent(this, RegistroClienteActivity.class);
        startActivity(intent);
    }

    public void retiro(View view){
        Intent intent = new Intent(this, RetiroActivity.class);
        startActivity(intent);
    }

    public void deposito(View view){
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);
    }

    public void consulta(View view){
        Intent intent = new Intent(this, ConsultaActivity.class);
        startActivity(intent);
    }

    public void cerrar(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void historial(View view){
        Intent intent = new Intent(this, RegistroHistorialActivity.class);
        startActivity(intent);
        finish();
    }


}