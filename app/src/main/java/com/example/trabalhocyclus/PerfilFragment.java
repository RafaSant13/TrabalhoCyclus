package com.example.trabalhocyclus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhocyclus.controller.UsuarioController;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;


public class PerfilFragment extends Fragment {

    private View view;
    private EditText etIdPerfil;
    private EditText etEmailPerfil;
    private EditText etSenhaPerfil;
    private EditText etNomePerfil;
    private Button btnAlterarPerfil;
    private Button btnExcluirPerfil;
    private UsuarioController uc;
    private int id;

    public PerfilFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        etIdPerfil = view.findViewById(R.id.etIdPerfil);
        etEmailPerfil = view.findViewById(R.id.etEmailPerfil);
        etSenhaPerfil = view.findViewById(R.id.etSenhaPerfil);
        etNomePerfil = view.findViewById(R.id.etNomePerfil);
        btnAlterarPerfil = view.findViewById(R.id.btnAlterarPerfil);
        btnExcluirPerfil = view.findViewById(R.id.btnExcluirPerfil);
        uc = new UsuarioController(new UsuarioDao(view.getContext()));
        Usuario u = new Usuario();
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        u.setId(id);
        try {
            u = uc.findOne(u);
            preencheCampos(u);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } finally{
            Usuario finalU = u;
            btnAlterarPerfil.setOnClickListener(op -> {
                try {
                    alterar(finalU);
                } catch (SQLException e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            btnExcluirPerfil.setOnClickListener(op -> {
                try {
                    excluir(finalU);
                } catch (SQLException e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }
    }

    private void alterar(Usuario u) throws SQLException {
        u = montaUsuario(u);
        uc.update(u);
        Toast.makeText(view.getContext(), "Usuário alterado com sucesso", Toast.LENGTH_LONG).show();
        preencheCampos(u);
    }

    private void excluir(Usuario u) throws SQLException {
        u = montaUsuario(u);
        uc.delete(u);
        Toast.makeText(view.getContext(), "Usuário excluído com sucesso", Toast.LENGTH_LONG).show();
        Intent i = new Intent(view.getContext(), MainActivity.class);
        this.startActivity(i);
        getActivity().finish();
    }

    private Usuario montaUsuario(Usuario u) {
        u.setId(Integer.parseInt(etIdPerfil.getText().toString()));
        u.setLogin(etEmailPerfil.getText().toString());
        u.setSenha(etSenhaPerfil.getText().toString());
        u.setNome(etNomePerfil.getText().toString());
        return u;
    }

    private void preencheCampos(Usuario u) {
        etIdPerfil.setText(String.valueOf(u.getId()));
        etEmailPerfil.setText(u.getLogin());
        etSenhaPerfil.setText(u.getSenha());
        etNomePerfil.setText(u.getNome());
    }


}