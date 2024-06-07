package com.example.trabalhocyclus.model;

import java.time.LocalDate;

public class DiaComum extends Dia {

    public DiaComum(LocalDate data) {
        super(data);
    }

    @Override
    public String toString() {
        return getData().getDayOfMonth()+"/"+ getData().getMonthValue()+"/"+getData().getYear()+": Dia Comum";
    }
}
