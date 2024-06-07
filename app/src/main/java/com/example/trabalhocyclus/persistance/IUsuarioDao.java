package com.example.trabalhocyclus.persistance;

import java.sql.SQLException;

public interface IUsuarioDao {
    public UsuarioDao open() throws SQLException;
    public void close();
}
