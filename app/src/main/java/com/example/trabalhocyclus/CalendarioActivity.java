package com.example.trabalhocyclus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhocyclus.controller.CalendarioAdapter;
import com.example.trabalhocyclus.controller.OnItemListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarioActivity extends AppCompatActivity implements OnItemListener {

    private TextView tvMesAno;
    private RecyclerView rvCalendario;
    private LocalDate data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        initWidgets();
        data = LocalDate.now();
        setMesView();
    }

    private void initWidgets() {
        rvCalendario = findViewById(R.id.rvCalendario);
        tvMesAno = findViewById(R.id.tvMesAno);
    }

    private void setMesView() {
        tvMesAno.setText(mesAnoFromData(data));
        ArrayList<String> diasMes = diasNoMesArray(data);

        CalendarioAdapter ca = new CalendarioAdapter(diasMes, this);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getApplicationContext(), 7);
        rvCalendario.setLayoutManager(lm);
        rvCalendario.setAdapter(ca);

    }

    private ArrayList<String> diasNoMesArray(LocalDate data) {
        ArrayList<String> diasNoMesArray = new ArrayList<>();
        YearMonth ym = YearMonth.from(data);

        int diasNoMes = ym.lengthOfMonth();

        LocalDate firstofMonth = data.withDayOfMonth(1);
        int dayOfWeek = firstofMonth.getDayOfWeek().getValue();

        for (int i =1; i<=42; i++){
            if (i<= dayOfWeek || i> diasNoMes + dayOfWeek)
            {
                diasNoMesArray.add("");
            }
            else {
                diasNoMesArray.add(String.valueOf((i-dayOfWeek)));
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

    @Override
    public void onItemClick(int position, String tvDia) {
        if (tvDia.equals("")){
            Toast.makeText(this, tvDia+ " "+mesAnoFromData(data),Toast.LENGTH_LONG).show();
        }
    }
}