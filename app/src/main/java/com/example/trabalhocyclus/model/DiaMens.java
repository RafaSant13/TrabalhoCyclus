package com.example.trabalhocyclus.model;

import androidx.annotation.NonNull;

import com.example.trabalhocyclus.controller.CalendarioViewHolder;

import java.time.LocalDate;
import java.util.List;

public class DiaMens extends Dia{
    public DiaMens(LocalDate data) {
        super(data);
    }

    @Override
    public void identificaDia(@NonNull CalendarioViewHolder holder, List<Integer> paleta) {
        if (getData().isEqual(LocalDate.now())){
            holder.tvDia.setBackgroundColor(paleta.get(0));
        }
        else {
            holder.tvDia.setBackgroundColor(paleta.get(1));
        }
    }

    @Override
    public String toString() {
        return getData().getDayOfMonth()+"/"+ getData().getMonthValue()+"/"+getData().getYear()+": Dia Menstruação";
    }
}
