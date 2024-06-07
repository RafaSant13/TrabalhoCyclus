package com.example.trabalhocyclus.model;

import java.time.LocalDate;

public class DiaMens extends Dia{
    public DiaMens(LocalDate data) {
        super(data);
    }

    @Override
    public String toString() {
        return getData().getDayOfMonth()+"/"+ getData().getMonthValue()+"/"+getData().getYear()+": Dia Menstruação";
    }
}
