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
        MensController mc =  new MensController(new MensDao(calendarioActivity), id);
        try {
            List<Menstruacao> menstruacoes = mc.findAllById();
            for (Menstruacao m : menstruacoes){
                LocalDate fim = m.getFim();
                if (m.getFim()==null){
                    fim = LocalDate.now();
                }
                if (isWithinRange(l, m.getInicio(), fim)){
                    if (l.isEqual(LocalDate.now())){
                        holder.tvDia.setBackgroundColor(paleta.get(0));
                    }
                    else {
                        holder.tvDia.setBackgroundColor(paleta.get(1));
                    }
                    break;
                }
                if (l.isEqual(LocalDate.now())){
                    holder.tvDia.setBackgroundColor(paleta.get(2));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Dia carregaDia(LocalDate data, int id, CalendarioActivity calendarioActivity) throws SQLException {
        MensController mc =  new MensController(new MensDao(calendarioActivity), id);
        Dia dia = null;
        try {
            List<Menstruacao> menstruacoes = mc.findAllById();
            for (Menstruacao m : menstruacoes){
                LocalDate fim = m.getFim();
                if (m.getFim()==null){
                    fim = LocalDate.now();
                }
                if (isWithinRange(data, m.getInicio(), fim)){
                    dia = new DiaMens(data);
                    break;
                } else {
                    dia = new DiaComum(data);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            return dia;
        }
    }

    boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
}
