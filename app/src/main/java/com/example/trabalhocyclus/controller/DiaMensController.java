package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Dia;
import com.example.trabalhocyclus.model.DiaMens;

import java.time.LocalDate;

public class DiaMensController implements DiaFactory{

    @Override
    public Dia criaDia(LocalDate data) {
        Dia dia = new DiaMens(data);
        return dia;
    }
}
