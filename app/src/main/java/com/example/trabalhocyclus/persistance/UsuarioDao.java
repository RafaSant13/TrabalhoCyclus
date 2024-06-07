package com.example.trabalhocyclus.persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabalhocyclus.model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements IUsuarioDao, ICRUDDao<Usuario> {

    private Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public UsuarioDao(Context context) {
        this.context = context;
    }

    @Override
    public UsuarioDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Usuario u) throws SQLException {
        ContentValues cv = getContentValues(u);
        db.insert("usuario",null, cv);
    }

    @Override
    public int update(Usuario u) throws SQLException {
        ContentValues cv = getContentValues(u);
        int ret = db.update("usuario", cv,
                "id = " + u.getId(), null);
        return ret;
    }

    @Override
    public void delete(Usuario u) throws SQLException {
        ContentValues cv = getContentValues(u);
        db.delete("usuario", "id = " + u.getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Usuario findOne(Usuario u) throws SQLException {
        Usuario u1;
        String sql = "SELECT id, login, senha, nome FROM usuario WHERE id = '"+u.getId()+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            u1 = new Usuario();
            u1.setId(cursor.getInt(cursor.getColumnIndex("id")));
            u1.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            u1.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            u1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        }
        else {
            throw new SQLException("Usuário não encontrado");
        }
        cursor.close();
        return u1;
    }

    @SuppressLint("Range")
    public Usuario findOneByLogin(Usuario u) throws SQLException {
        Usuario u1;
        String sql = "SELECT id, login, senha, nome FROM usuario WHERE login = '"+u.getLogin()+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            u1 = new Usuario();
            u1.setId(cursor.getInt(cursor.getColumnIndex("id")));
            u1.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            u1.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            u1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        }
        else {
            throw new SQLException("Usuário não encontrado");
        }
        cursor.close();
        return u1;
    }

    @SuppressLint("Range")
    public List<Usuario> findAll() throws SQLException{
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, login, senha, nome FROM usuario";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Usuario u = new Usuario();
            u.setId(cursor.getInt(cursor.getColumnIndex("id")));
            u.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            u.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            u.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usuarios.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        return usuarios;
    }

    private static ContentValues getContentValues (Usuario u){
        ContentValues cv = new ContentValues();
        cv.put("id", u.getId());
        cv.put("login", u.getLogin());
        cv.put("senha", u.getSenha());
        cv.put("nome", u.getNome());
        return cv;
    }




}
