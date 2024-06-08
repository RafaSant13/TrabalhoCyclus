package com.example.trabalhocyclus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    private Fragment fragment;

    public int idUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        idUsr = bundle.getInt("id");
        bundle.remove("id");
        if (bundle.size()>0){
            carregaFragment(bundle);
        } else {
            Bundle bf = new Bundle();
            bf.putInt("id", idUsr);
            fragment = new HomeFragment();
            fragment.setArguments(bf);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment, fragment);
            ft.commit();
        }
    }

    private void carregaFragment(Bundle bundle) {
        String tipo = bundle.getString("tipo");
        if (tipo.equals("calendario")){
            Bundle b = new Bundle();
            b.putInt("id", idUsr);
            Intent i = new Intent(this, CalendarioActivity.class);
            i.putExtras(b);
            this.startActivity(i);
            this.finish();
        }
        else{
            if (tipo.equals("home")){
                fragment = new HomeFragment();
            }
            else if (tipo.equals("perfil")){
                fragment = new PerfilFragment();
            }
            else {
                fragment = new AdicionarFragment();
            }
            Bundle bf = new Bundle();
            bf.putInt("id", idUsr);
            fragment.setArguments(bf);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cyclus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent i = new Intent(this, HomeActivity.class);
        bundle.putInt("id", idUsr);

        if (id == R.id.itemHome){
            bundle.putString("tipo", "home");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        } else if (id == R.id.itemPerfil){
            bundle.putString("tipo", "perfil");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        } else if (id == R.id.itemMenstruacao) {
            bundle.putString("tipo", "menstruacao");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        } else{
            bundle.putString("tipo", "calendario");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }

    }
}