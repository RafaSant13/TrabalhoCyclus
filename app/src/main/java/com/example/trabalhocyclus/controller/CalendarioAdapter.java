package com.example.trabalhocyclus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.CalendarioActivity;
import com.example.trabalhocyclus.R;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioViewHolder> {

    public final ArrayList<LocalDate> diasMes;
    private final OnItemListener onItemListener;
    private final List<Integer> paleta;
    private int id;
    private CalendarioActivity calendarioActivity;

    public CalendarioAdapter(ArrayList<LocalDate> diasMes, OnItemListener onItemListener, List<Integer> paleta, int id, CalendarioActivity calendarioActivity) {
        this.diasMes = diasMes;
        this.onItemListener = onItemListener;
        this.paleta = paleta;
        this.id = id;
        this.calendarioActivity = calendarioActivity;
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
        if (diasMes.get(position).isEqual(LocalDate.MIN)) {
            holder.tvDia.setText("");
        } else {
            holder.tvDia.setText(String.valueOf(diasMes.get(position).getDayOfMonth()));
        }
        CalendarioController cc = new CalendarioController();
        try {
            cc.checaMens(holder, diasMes.get(position), paleta, id, calendarioActivity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return diasMes.size();
    }
}
