package com.example.trabalhocyclus.model;

import androidx.annotation.NonNull;

import com.example.trabalhocyclus.controller.CalendarioViewHolder;

import java.time.LocalDate;
import java.util.List;

public abstract class Dia {

    private LocalDate data;

    public Dia(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public abstract void identificaDia(@NonNull CalendarioViewHolder holder, List<Integer> paleta);

    @Override
    public String toString() {
        return data.getDayOfMonth()+"/"+data.getMonthValue()+"/"+data.getYear();
    }
}
