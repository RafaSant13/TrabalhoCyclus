package com.example.trabalhocyclus.persistance;

import java.sql.SQLException;

public interface IMensDao {

    public MensDao open() throws SQLException;
    public void close();
}
