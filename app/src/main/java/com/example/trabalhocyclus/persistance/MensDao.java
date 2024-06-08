package com.example.trabalhocyclus.persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trabalhocyclus.model.Menstruacao;
import com.example.trabalhocyclus.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MensDao implements IMensDao, ICRUDDao<Menstruacao> {

    private Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public MensDao(Context context) {
        this.context = context;
    }

    @Override
    public MensDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Menstruacao m) throws SQLException {
        ContentValues cv = getContentValues(m);
        db.insert("menstruacao",null, cv);
    }

    @Override
    public int update(Menstruacao m) throws SQLException {
        ContentValues cv = getContentValues(m);
        int ret = db.update("menstruacao", cv,
                "data_inicio = '" + m.getInicio().toString() + "' AND idUsuario = "+m.getUsuario().getId(), null);
        return ret;
    }

    @Override
    public void delete(Menstruacao m) throws SQLException {
        ContentValues cv = getContentValues(m);
        db.delete("menstruacao", "data_inicio = '" + m.getInicio().toString() + "' AND idUsuario = "+m.getUsuario().getId(),
                null);
    }

    @SuppressLint("Range")
    @Override
    public Menstruacao findOne(Menstruacao m) throws SQLException {
        Menstruacao m1 = new Menstruacao();
        String sql = "SELECT u.id, u.login, u.senha, u.nome, " +
                " m.data_inicio, m.data_fim,  m.dias" +
                "  from usuario u, menstruacao m" +
                " WHERE m.idUsuario = u.id AND m.idUsuario = "+m.getUsuario().getId() +"" +
                " AND m.data_inicio = '"+m.getInicio().toString()+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            Usuario u = new Usuario();
            u.setId(cursor.getInt(cursor.getColumnIndex("id")));
            u.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            u.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            u.setNome(cursor.getString(cursor.getColumnIndex("nome")));

            m1.setInicio(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_inicio"))));
            if (cursor.getString(cursor.getColumnIndex("data_fim"))!=null){
                m1.setFim(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_fim"))));
            }
            m1.setDiasDesdeUltimo(cursor.getInt(cursor.getColumnIndex("dias")));
            m1.setUsuario(u);
        }
        cursor.close();
        return m1;
    }

    @SuppressLint("Range")
    public List<Menstruacao> findAllById(int id) throws SQLException{
        List<Menstruacao> menstruacoes = new ArrayList<>();
        String sql = "SELECT u.id, u.login, u.senha, u.nome, " +
                " m.data_inicio, m.data_fim,  m.dias" +
                "  from usuario u, menstruacao m" +
                " WHERE m.idUsuario = u.id AND m.idUsuario = "+id+"" +
                " ORDER BY m.data_inicio";
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
            Menstruacao m = new Menstruacao();

            m.setInicio(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_inicio"))));
            if (cursor.getString(cursor.getColumnIndex("data_fim"))!=null){
                m.setFim(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_fim"))));
            }
            m.setDiasDesdeUltimo(cursor.getInt(cursor.getColumnIndex("dias")));
            m.setUsuario(u);
            menstruacoes.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return menstruacoes;
    }

    private static ContentValues getContentValues (Menstruacao m){
        ContentValues cv = new ContentValues();
        cv.put("data_inicio", m.getInicio().toString());
        if (m.getFim()!=null){
            cv.put("data_fim", m.getFim().toString());
        }
        cv.put("dias", m.getDiasDesdeUltimo());
        cv.put("idUsuario", m.getUsuario().getId());
        return cv;
    }
}
