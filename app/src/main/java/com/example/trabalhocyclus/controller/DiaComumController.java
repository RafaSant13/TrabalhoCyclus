package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Dia;
import com.example.trabalhocyclus.model.DiaComum;

import java.time.LocalDate;

public class DiaComumController implements DiaFactory{
    @Override
    public Dia criaDia(LocalDate data) {
        Dia dia = new DiaComum(data);
        return dia;
    }
}
