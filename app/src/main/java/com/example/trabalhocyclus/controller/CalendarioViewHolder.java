package com.example.trabalhocyclus.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.R;

public class CalendarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView tvDia;
    private final OnItemListener onItemListener;


    public CalendarioViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        tvDia = itemView.findViewById(R.id.tvDia);
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), (String) tvDia.getText());
    }
}
