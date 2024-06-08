package com.example.trabalhocyclus.model;

import java.time.LocalDate;

public class Menstruacao {


    private LocalDate inicio;
    private LocalDate fim;
    private int diasDesdeUltimo;
    private Usuario usuario;

    public Menstruacao(){
        super();
    }
    public Menstruacao(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public int getDiasDesdeUltimo() {
        return diasDesdeUltimo;
    }

    public void setDiasDesdeUltimo(int diasDesdeUltimo) {
        this.diasDesdeUltimo = diasDesdeUltimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Menstruacao: Início: "+getInicio().toString()+" - Fim: "+getFim().toString()+"\n";
    }
}
