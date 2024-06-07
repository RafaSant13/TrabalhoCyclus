package com.example.trabalhocyclus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trabalhocyclus.controller.UsuarioController;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;

public class CadastroActivity extends AppCompatActivity {

    private EditText etIdCriar;
    private EditText etEmailCriar;
    private EditText etSenhaCriar;
    private EditText etNomeCriar;
    private Button btnCadastrarCriar;
    private Button btnCancelarCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etIdCriar = findViewById(R.id.etIdCriar);
        etEmailCriar = findViewById(R.id.etEmailCriar);
        etSenhaCriar = findViewById(R.id.etSenhaCriar);
        etNomeCriar = findViewById(R.id.etNomeCriar);

        btnCadastrarCriar = findViewById(R.id.btnCadastrarCriar);
        btnCancelarCriar = findViewById(R.id.btnCancelarCriar);

        btnCadastrarCriar.setOnClickListener(op -> cadastrar());
        btnCancelarCriar.setOnClickListener(op -> voltar());

    }

    private void cadastrar() {
        UsuarioController uc = new UsuarioController(new UsuarioDao(this));
        Usuario u = new Usuario();
        u.setId(Integer.parseInt(etIdCriar.getText().toString()));
        u.setLogin(etEmailCriar.getText().toString());
        u.setSenha(etSenhaCriar.getText().toString());
        u.setNome(etNomeCriar.getText().toString());

        try {
            uc.insert(u);
            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void voltar() {
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }
}