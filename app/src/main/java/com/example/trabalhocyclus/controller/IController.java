package com.example.trabalhocyclus.controller;

import java.sql.SQLException;

public interface IController<T> {

    public void insert(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    public T findOne (T t) throws SQLException;
}
