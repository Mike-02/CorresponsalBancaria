package com.example.corresponsalbancaria.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.corresponsalbancaria.Adapter;
import com.example.corresponsalbancaria.Funciones;
import com.example.corresponsalbancaria.POJOs.Historial;
import com.example.corresponsalbancaria.R;

import java.util.ArrayList;

public class RegistroHistorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_historial);
        getSupportActionBar().hide();
        cargarData();
    }


    void cargarData() {
        Funciones funciones = new Funciones(this);
        ArrayList<Historial> historials = funciones.getHistorial();
        RecyclerView lista = findViewById(R.id.lista);
        Adapter adapter = new Adapter(historials);
        lista.setAdapter(adapter);
        lista.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarData();
    }


}