package com.ucsur.cruddocentes.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ucsur.cruddocentes.R;
import com.ucsur.cruddocentes.adaptador.AdaptadorDocente;
import com.ucsur.cruddocentes.datos.DocenteDao;

public class MainActivity extends AppCompatActivity {
    private DocenteDao dao;
    private AdaptadorDocente adaptador;
    private EditText txtBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DocenteDao(this);
        txtBuscar = findViewById(R.id.txtBuscar);
        ListView lv = findViewById(R.id.lvDocentes);
        adaptador = new AdaptadorDocente(this, dao.listar(null));
        lv.setAdapter(adaptador);

        findViewById(R.id.btnNuevo).setOnClickListener(v ->
                startActivity(new Intent(this, NuevoActivity.class)));
        findViewById(R.id.btnConsultar).setOnClickListener(v ->
                adaptador.actualizarLista(dao.listar(txtBuscar.getText().toString().trim())));
        lv.setOnItemClickListener((parent, view, pos, id) -> {
            Intent i = new Intent(this, ActualizarActivity.class);
            i.putExtra("codigo", (int) id);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {   // se recarga al volver de otra pantalla
        super.onResume();
        adaptador.actualizarLista(dao.listar(null));
    }
}