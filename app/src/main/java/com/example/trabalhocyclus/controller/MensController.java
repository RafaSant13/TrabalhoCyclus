package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Menstruacao;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.MensDao;

import java.sql.SQLException;
import java.util.List;

public class MensController implements IController<Menstruacao> {

    private final MensDao mDao;

    public MensController(MensDao mDao) {
        this.mDao = mDao;
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

    public List<Menstruacao> findAllById(int id) throws SQLException {
        if (mDao.open()==null){
            mDao.open();
        }
        return mDao.findAllById(id);
    }

    public int calcDia(List<Menstruacao> menstruacoes) {
        int dias = 0;
        for (Menstruacao m : menstruacoes){
            dias = dias + m.getDiasDesdeUltimo();
        }
        dias = dias/(menstruacoes.size()-1);
        return dias;
    }
}
