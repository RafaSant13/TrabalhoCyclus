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

    public void checaMens(@NonNull CalendarioViewHolder holder, LocalDate l, int color, int id, CalendarioActivity calendarioActivity){
        MensController mc =  new MensController(new MensDao(calendarioActivity));
        try {
            List<Menstruacao> menstruacoes = mc.findAllById(id);
            for (Menstruacao m : menstruacoes){
                if (isWithinRange(l, m.getInicio(), m.getFim())){
                    holder.tvDia.setBackgroundColor(color);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dia carregaDia(LocalDate data, int id, CalendarioActivity calendarioActivity){
        MensController mc =  new MensController(new MensDao(calendarioActivity));
        Dia dia = null;
        try {
            List<Menstruacao> menstruacoes = mc.findAllById(id);
            for (Menstruacao m : menstruacoes){
                if (isWithinRange(data, m.getInicio(), m.getFim())){
                    dia = new DiaMens(data);
                    break;
                } else {
                    dia = new DiaComum(data);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return dia;
        }
    }

    boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
}
