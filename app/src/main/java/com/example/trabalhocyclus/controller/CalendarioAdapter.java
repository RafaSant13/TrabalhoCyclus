package com.example.trabalhocyclus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.R;

import java.util.ArrayList;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioViewHolder> {

    public final ArrayList<String> diasMes;
    private final OnItemListener onItemListener;

    public CalendarioAdapter(ArrayList<String> diasMes, OnItemListener onItemListener) {
        this.diasMes = diasMes;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celula_calendario, parent, false);
        ViewGroup.LayoutParams layout = view.getLayoutParams();
        layout.height = (int) (parent.getHeight()*0.166666666);
        return new CalendarioViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarioViewHolder holder, int position) {
        holder.tvDia.setText(diasMes.get(position));
    }

    @Override
    public int getItemCount() {
        return diasMes.size();
    }
}
