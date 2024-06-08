package com.example.trabalhocyclus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.controller.CalendarioAdapter;
import com.example.trabalhocyclus.controller.CalendarioController;
import com.example.trabalhocyclus.controller.OnItemListener;
import com.example.trabalhocyclus.model.Dia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity implements OnItemListener {

    private TextView tvMesAno;
    private RecyclerView rvCalendario;
    private LocalDate data;
    private Button btnVoltar;
    private Dia dia;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        initWidgets();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        bundle.remove("id");
        bundle.remove("tipo");
        data = LocalDate.now();
        setMesView();
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(op -> Voltar());
    }

    private void initWidgets() {
        rvCalendario = findViewById(R.id.rvCalendario);
        tvMesAno = findViewById(R.id.tvMesAno);
    }

    private void setMesView() {
        tvMesAno.setText(mesAnoFromData(data));
        ArrayList<LocalDate> diasMes = diasNoMesArray(data);
        List<Integer> paleta = new ArrayList<>();
        paleta.add(getResources().getColor(R.color.salmon, getTheme()));
        paleta.add(getResources().getColor(R.color.salmonLight, getTheme()));
        paleta.add(getResources().getColor(R.color.gray, getTheme()));
        CalendarioAdapter ca = new CalendarioAdapter(diasMes, this, paleta, id, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvCalendario.setLayoutManager(lm);
        rvCalendario.setAdapter(ca);

    }

    private ArrayList<LocalDate> diasNoMesArray(LocalDate data) {
        ArrayList<LocalDate> diasNoMesArray = new ArrayList<>();
        YearMonth ym = YearMonth.from(data);

        int diasNoMes = ym.lengthOfMonth();

        LocalDate firstofMonth = data.withDayOfMonth(1);
        int dayOfWeek = firstofMonth.getDayOfWeek().getValue();

        for (int i =1; i<=42; i++){
            if (i<= dayOfWeek || i> diasNoMes + dayOfWeek)
            {
                diasNoMesArray.add(LocalDate.MIN);
            }
            else {
                diasNoMesArray.add(LocalDate.of(data.getYear(), data.getMonthValue(),i-dayOfWeek));
            }
        }
        return diasNoMesArray;
    }

    private String mesAnoFromData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return data.format(formatter);
    }

    public void Passado(View view) {
        data = data.minusMonths(1);
        setMesView();
    }

    public void Proximo(View view) {
        data = data.plusMonths(1);
        setMesView();
    }

    public void Voltar(){
        Bundle b = new Bundle();
        b.putInt("id", id);
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtras(b);
        this.startActivity(i);
        this.finish();
    }

    @Override
    public void onItemClick(int position, String tvDia) {
        CalendarioController cc = new CalendarioController();
        if (!tvDia.equals("")){
            try {
                dia = cc.carregaDia(LocalDate.of(data.getYear(), data.getMonthValue(), Integer.parseInt(tvDia)), id, this);
                Toast.makeText(this, dia.toString(),Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
    }
}