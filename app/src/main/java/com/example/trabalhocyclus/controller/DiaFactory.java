package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Dia;

import java.time.LocalDate;

public interface DiaFactory {

    public Dia criaDia(LocalDate data);
}
