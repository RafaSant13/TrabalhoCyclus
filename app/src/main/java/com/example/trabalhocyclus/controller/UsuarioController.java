package com.example.trabalhocyclus.controller;

import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController implements IController<Usuario> {

    private final UsuarioDao uDao;

    public UsuarioController(UsuarioDao uDao) {
        this.uDao = uDao;
    }

    @Override
    public void insert(Usuario u) throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        uDao.insert(u);
        uDao.close();
    }

    @Override
    public void update(Usuario u) throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        uDao.update(u);
        uDao.close();
    }

    @Override
    public void delete(Usuario u) throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        uDao.delete(u);
        uDao.close();
    }

    @Override
    public Usuario findOne(Usuario u) throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        return uDao.findOne(u);
    }

    public Usuario findOneByLogin(Usuario u) throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        return uDao.findOneByLogin(u);
    }

    public List<Usuario> findAll() throws SQLException {
        if (uDao.open()==null){
            uDao.open();
        }
        return uDao.findAll();
    }
}
