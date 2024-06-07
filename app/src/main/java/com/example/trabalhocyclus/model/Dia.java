package com.example.trabalhocyclus.model;

import java.time.LocalDate;

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

    @Override
    public String toString() {
        return data.getDayOfMonth()+"/"+data.getMonthValue()+"/"+data.getYear();
    }
}
