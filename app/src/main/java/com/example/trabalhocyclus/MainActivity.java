package com.example.trabalhocyclus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.trabalhocyclus.controller.UsuarioController;
import com.example.trabalhocyclus.model.Usuario;
import com.example.trabalhocyclus.persistance.UsuarioDao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etEmailAcesso;
    private EditText etSenhaAcesso;
    private Button btnEntrar;
    private Button btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etEmailAcesso = findViewById(R.id.etEmailAcesso);
        etSenhaAcesso = findViewById(R.id.etSenhaAcesso);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(op -> entrar());
        btnCriar = findViewById(R.id.btnCriar);
        btnCriar.setOnClickListener(op -> criar());

    }

    private void entrar() {
        Usuario usuario = new Usuario();
        usuario.setLogin(etEmailAcesso.getText().toString());
        usuario.setSenha(etSenhaAcesso.getText().toString());
        UsuarioController uc = new UsuarioController(new UsuarioDao(this));
        boolean temCadastro = false;
        try {
            Usuario u = uc.findOneByLogin(usuario);
            if (usuario.getSenha().equals(u.getSenha())){
                novaTela(u.getId());
            } else {
                Toast.makeText(this, "Senha incorreta", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void criar() {
        Intent i = new Intent(this, CadastroActivity.class);
        this.startActivity(i);
        this.finish();
    }

    private void novaTela(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtras(bundle);
        this.startActivity(i);
        this.finish();
    }

}