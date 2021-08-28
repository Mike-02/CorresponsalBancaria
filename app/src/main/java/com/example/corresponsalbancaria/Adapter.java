package com.example.corresponsalbancaria;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corresponsalbancaria.POJOs.Historial;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<Historial> historials;

    public Adapter(ArrayList<Historial> historials){
        this.historials = historials;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historial,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Historial historial = historials.get(position);
        holder.hisCedula.setText(historial.getCedula());
        holder.hisBalance.setText(historial.getBalance());
        holder.hisTransaccion.setText(historial.getTransaccion());
        holder.hisFecha.setText(historial.getFecha());

    }

    @Override
    public int getItemCount() {
        return historials.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView hisCedula;
        TextView hisBalance;
        TextView hisTransaccion;
        TextView hisFecha;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hisCedula = itemView.findViewById(R.id.hisCedula);
            hisBalance = itemView.findViewById(R.id.hisBalance);
            hisTransaccion = itemView.findViewById(R.id.hisTransaccion);
            hisFecha = itemView.findViewById(R.id.hisFecha);

        }
    }
}


