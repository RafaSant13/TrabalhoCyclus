package com.example.trabalhocyclus.controller;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.R;

public class CalendarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView tvDia;
    private final OnItemListener onItemListener;
    @SuppressLint("ResourceAsColor")
    public CalendarioViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);
        tvDia = itemView.findViewById(R.id.tvDia);
        if (tvDia.getText().toString().equals("17")){
            tvDia.setBackgroundColor(R.color.salmon);
        }
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), (String) tvDia.getText());
    }
}
