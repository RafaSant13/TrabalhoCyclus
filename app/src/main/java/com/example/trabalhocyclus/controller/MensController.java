package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Menstruacao;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.MensDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class MensController implements IController<Menstruacao> {

    private final MensDao mDao;
    private int id;

    public MensController(MensDao mDao, int id) throws SQLException {
        this.mDao = mDao;
        this.id = id;
    }

    @Override
    public void insert(Menstruacao menstruacao) throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        mDao.insert(menstruacao);
        mDao.close();
    }

    @Override
    public void update(Menstruacao menstruacao) throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        mDao.update(menstruacao);
        mDao.close();
    }

    @Override
    public void delete(Menstruacao menstruacao) throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        mDao.delete(menstruacao);
        mDao.close();
    }

    @Override
    public Menstruacao findOne(Menstruacao menstruacao) throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        return mDao.findOne(menstruacao);
    }

    public List<Menstruacao> findAllById() throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        List<Menstruacao> menstruacoes = mDao.findAllById(id);
        return menstruacoes;
    }

    public int calcDia(List<Menstruacao> menstruacoes) {
        int dias = 0;
        for (Menstruacao m : menstruacoes){
            dias = dias + m.getDiasDesdeUltimo();
        }
        dias = dias/(menstruacoes.size()-1);
        return dias;
    }

    public void atualizaDias() throws SQLException {
        List<Menstruacao> menstruacoes = findAllById();
        int tamanho = menstruacoes.size();
        Menstruacao m;
        for (int i = 0; i<tamanho;i++){
            m = menstruacoes.get(i);
            if (i==0){
                m.setDiasDesdeUltimo(0);
            } else {
                Menstruacao ultima = menstruacoes.get(i-1);
                m.setDiasDesdeUltimo(diasDesde(ultima.getInicio(), m.getInicio()));
            }
            update(m);
        }
    }


    public int getDiasDesde(Menstruacao m) throws SQLException {
        List<Menstruacao> menstruacoes = findAllById();
        int tamanho = menstruacoes.size();
        int dias = 0;
        if (tamanho == 0){
            return dias;
        } else {
            Menstruacao ultima = menstruacoes.get(tamanho-1);
            dias = diasDesde(ultima.getInicio(), m.getInicio());
            while (ultima.getInicio().isBefore(m.getInicio())||ultima.getInicio().isEqual(m.getInicio())){
                dias++;
                ultima.setInicio(ultima.getInicio().plusDays(1));
            }
            return dias;
        }
    }

    public int diasDesde(LocalDate ultima, LocalDate proxima){
        int count = 0;
        while (ultima.isBefore(proxima)||ultima.isEqual(proxima)){
            count++;
            ultima= ultima.plusDays(1);
        }
        return count;
    }

}
