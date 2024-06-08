package com.example.trabalhocyclus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalhocyclus.controller.MensController;
import com.example.trabalhocyclus.controller.UsuarioController;
import com.example.trabalhocyclus.model.Menstruacao;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.MensDao;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdicionarFragment extends Fragment {

    private View view;
    private EditText etInicio;
    private EditText etFim;
    private CheckBox cbAteHoje;
    private Button btnAdicionar;
    private Button btnAlterarAdicionar;
    private Button btnExcluirAdicionar;
    private Button btnBuscarAdicionar;
    private Button btnListarAdicionar;
    private TextView tvAdicionarFim;
    private TextView tvListar;
    private int id;
    private MensController mc;
    private List<Menstruacao> menstruacoes;

    public AdicionarFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_adicionar, container, false);
        etInicio = view.findViewById(R.id.etInicio);
        etFim = view.findViewById(R.id.etFim);
        cbAteHoje = view.findViewById(R.id.cbAteHoje);
        btnAdicionar = view.findViewById(R.id.btnAdicionar);
        btnAlterarAdicionar = view.findViewById(R.id.btnAlterarAdicionar);
        btnExcluirAdicionar = view.findViewById(R.id.btnExcluirAdicionar);
        btnBuscarAdicionar = view.findViewById(R.id.btnBuscarAdicionar);
        btnListarAdicionar = view.findViewById(R.id.btnListarAdicionar);
        tvAdicionarFim = view.findViewById(R.id.tvAdicionarFim);
        tvListar = view.findViewById(R.id.tvListar);
        tvListar.setMovementMethod(new ScrollingMovementMethod());
        cbAteHoje.setOnCheckedChangeListener((buttonView, isChecked) -> atualizaFragment());
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        try {
            mc = new MensController(new MensDao(view.getContext()), id);
            menstruacoes = mc.findAllById();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        btnAdicionar.setOnClickListener(op -> {
            try {
                adicionar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnAlterarAdicionar.setOnClickListener(op -> {
            try {
                alterar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnExcluirAdicionar.setOnClickListener(op -> {
            try {
                excluir();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnBuscarAdicionar.setOnClickListener(op -> {
            try {
                buscar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnListarAdicionar.setOnClickListener(op -> {
            try {
                listar();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        checaUltima();
        return view;
    }

    private void checaUltima() {
        Menstruacao ultima = menstruacoes.get(menstruacoes.size()-1);
        if (ultima.getFim()==null){
            tvAdicionarFim.setVisibility(View.VISIBLE);
            etInicio.setVisibility(View.INVISIBLE);
            btnBuscarAdicionar.setVisibility(View.INVISIBLE);
            btnAdicionar.setVisibility(View.INVISIBLE);
            btnListarAdicionar.setVisibility(View.INVISIBLE);
            cbAteHoje.setVisibility(View.INVISIBLE);
            etInicio.setText(ultima.getInicio().toString());
            tvAdicionarFim.setText(getString(R.string.adicionarFim)+" Data início: "+ultima.getInicio().toString());
        } else{
            tvAdicionarFim.setVisibility(View.INVISIBLE);
            etInicio.setVisibility(View.VISIBLE);
            btnBuscarAdicionar.setVisibility(View.VISIBLE);
            btnAdicionar.setVisibility(View.VISIBLE);
            btnListarAdicionar.setVisibility(View.VISIBLE);
            cbAteHoje.setVisibility(View.VISIBLE);
        }
    }


    private void adicionar() throws SQLException {
        Menstruacao m = montaMenstruacao();
        mc.insert(m);
        Toast.makeText(view.getContext(), "Menstruação adicionada com sucesso", Toast.LENGTH_LONG).show();
        limpaCampos();
        mc.atualizaDias();
        menstruacoes = mc.findAllById();
        checaUltima();
    }

    private void alterar() throws SQLException {
        Menstruacao m = montaMenstruacao();
        mc.update(m);
        Toast.makeText(view.getContext(), "Menstruação alterada com sucesso", Toast.LENGTH_LONG).show();
        limpaCampos();
        mc.atualizaDias();
        menstruacoes = mc.findAllById();
        checaUltima();
    }

    private void excluir() throws SQLException {
        Menstruacao m = montaMenstruacao();
        mc.delete(m);
        Toast.makeText(view.getContext(), "Menstruação excluída com sucesso", Toast.LENGTH_LONG).show();
        limpaCampos();
        mc.atualizaDias();
        menstruacoes = mc.findAllById();
        checaUltima();
    }

    private void buscar() throws SQLException {
        Menstruacao m = montaMenstruacao();
        Menstruacao m1 = mc.findOne(m);
        preencheCampos(m1);
    }


    private void listar() throws SQLException {
        StringBuffer buffer = new StringBuffer();
        for (Menstruacao m : menstruacoes){
            buffer.append(m.toString());
        }
        tvListar.setText(buffer.toString());

    }

    private void atualizaFragment() {
        if (cbAteHoje.isChecked()){
            etFim.setText("");
            etFim.setVisibility(View.INVISIBLE);
        } else {
            etFim.setVisibility(View.VISIBLE);
        }

    }

    private Menstruacao montaMenstruacao() {
        UsuarioController uc = new UsuarioController(new UsuarioDao(view.getContext()));
        Usuario u = new Usuario();
        Menstruacao m = new Menstruacao();
        u.setId(id);
        try {
            u = uc.findOne(u);
            m.setInicio(LocalDate.parse(etInicio.getText().toString()));
            if (etFim.getText().toString().equals("")) {
                m.setFim(null);
            } else {
                m.setFim(LocalDate.parse(etFim.getText().toString()));
            }
            m.setDiasDesdeUltimo(mc.getDiasDesde(m));
            m.setUsuario(u);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            return m;
        }
    }

    private void limpaCampos() {
        etInicio.setText("");
        etFim.setText("");
    }

    private void preencheCampos(Menstruacao m) {
        etInicio.setText(m.getInicio().toString());
        if (m.getFim()==null){
            cbAteHoje.setChecked(true);
        } else {
            cbAteHoje.setChecked(false);
            etFim.setText(m.getFim().toString());
        }
    }

}