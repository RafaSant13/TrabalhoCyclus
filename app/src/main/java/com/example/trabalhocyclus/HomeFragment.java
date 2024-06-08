package com.example.trabalhocyclus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View view;
    private TextView tvOla;
    private TextView tvProx;
    private int id;

    public HomeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        UsuarioController uc = new UsuarioController(new UsuarioDao(view.getContext()));
        Usuario u = new Usuario();
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        u.setId(id);
        try {
            u = uc.findOne(u);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        tvOla = view.findViewById(R.id.tvOla);
        tvOla.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String res = getString(R.string.ola)+" "+u.getNome()+"!";
        tvOla.setText(res);
        tvProx = view.findViewById(R.id.tvProx);
        tvProx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        try {
            MensController mc = new MensController(new MensDao(view.getContext()), id);
            List<Menstruacao> menstruacoes = mc.findAllById();
            if (menstruacoes.size()<2){
                res = getString(R.string.favor);
                tvProx.setText(res);
            }
            else {
                int dia = mc.calcDia(menstruacoes);
                LocalDate dataInicio = menstruacoes.get(menstruacoes.size()-1).getInicio();
                LocalDate dataProx = dataInicio.plusDays(dia);
                res = getString(R.string.ciclo) + " "+dia+" "+getString(R.string.dias)+"\nDia: "+dataProx.getDayOfMonth()+"/"+dataProx.getMonthValue()+"/"+dataProx.getYear();
                tvProx.setText(res);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return view;
        }
    }

    private void adicionar() {

    }

    private void perfil() {

    }

    private void calendario() {
        Intent i = new Intent(view.getContext(), CalendarioActivity.class);
        this.startActivity(i);
        getActivity().finish();
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle b = new Bundle();
        b.putInt("id", id);
        Intent i = new Intent(view.getContext(), HomeActivity.class);
        i.putExtras(b);
        this.startActivity(i);
        getActivity().finish();
        return super.onOptionsItemSelected(item);
    }*/
}