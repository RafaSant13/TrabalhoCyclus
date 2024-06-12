package com.example.trabalhocyclus.controller;

import androidx.annotation.NonNull;

import com.example.trabalhocyclus.CalendarioActivity;
import com.example.trabalhocyclus.model.Dia;
import com.example.trabalhocyclus.model.DiaComum;
import com.example.trabalhocyclus.model.DiaMens;
import com.example.trabalhocyclus.model.Menstruacao;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.MensDao;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CalendarioController {

    public CalendarioController() {
        super();
    }

    public void checaMens(@NonNull CalendarioViewHolder holder, LocalDate l, List<Integer> paleta, int id, CalendarioActivity calendarioActivity) throws SQLException {
        Dia d = carregaDia(l, id, calendarioActivity);
        d.identificaDia(holder, paleta);
    }

    public Dia carregaDia(LocalDate data, int id, CalendarioActivity calendarioActivity) throws SQLException {
        MensController mc =  new MensController(new MensDao(calendarioActivity), id);
        DiaFactory df;
        List<Menstruacao> menstruacoes = mc.findAllById();
        if (isInMenstruacoes(data, menstruacoes)){
            df = new DiaMensController();
        } else {
            df = new DiaComumController();
        }
        return df.criaDia(data);
    }

    boolean isInMenstruacoes(LocalDate data, List<Menstruacao> menstruacoes){
        for (Menstruacao m : menstruacoes){
            LocalDate fim = m.getFim();
            if (m.getFim()==null){
                fim = LocalDate.now();
            }
            if (isWithinRange(data, m.getInicio(), fim)){
                return true;
            }
        }
        return false;
    }

    boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
}
