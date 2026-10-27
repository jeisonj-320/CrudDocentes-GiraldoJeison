package com.ucsur.cruddocentes.vista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ucsur.cruddocentes.R;
import com.ucsur.cruddocentes.datos.DocenteDao;
import com.ucsur.cruddocentes.modelo.Docente;

public class NuevoActivity extends AppCompatActivity {
    private TextInputLayout tilNombre, tilPaterno, tilSueldo, tilHijos;
    private EditText txtNombre, txtPaterno, txtMaterno, txtSueldo, txtHijos;
    private Spinner spSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        tilNombre = findViewById(R.id.tilNombre);
        tilPaterno = findViewById(R.id.tilPaterno);
        tilSueldo = findViewById(R.id.tilSueldo);
        tilHijos = findViewById(R.id.tilHijos);
        txtNombre = findViewById(R.id.txtNombre);
        txtPaterno = findViewById(R.id.txtPaterno);
        txtMaterno = findViewById(R.id.txtMaterno);
        txtSueldo = findViewById(R.id.txtSueldo);
        txtHijos = findViewById(R.id.txtHijos);
        spSexo = findViewById(R.id.spSexo);

        ArrayAdapter<CharSequence> adSexo = ArrayAdapter.createFromResource(
                this, R.array.sexos, android.R.layout.simple_spinner_item);
        adSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adSexo);

        findViewById(R.id.btnGrabar).setOnClickListener(v -> grabar());
        findViewById(R.id.btnRegresar).setOnClickListener(v -> finish());
    }

    private String texto(EditText e) {
        return e.getText().toString().trim();
    }

    private boolean validar() {
        boolean ok = true;
        if (texto(txtNombre).isEmpty()) {
            tilNombre.setError("Campo obligatorio"); ok = false;
        } else tilNombre.setError(null);

        if (texto(txtPaterno).isEmpty()) {
            tilPaterno.setError("Campo obligatorio"); ok = false;
        } else tilPaterno.setError(null);

        // Sueldo: obligatorio y mayor que 0
        try {
            double sueldo = Double.parseDouble(texto(txtSueldo));
            if (sueldo <= 0) {
                tilSueldo.setError("Debe ser mayor que 0"); ok = false;
            } else tilSueldo.setError(null);
        } catch (NumberFormatException ex) {
            tilSueldo.setError("Ingresa un sueldo válido"); ok = false;
        }

        // Hijos: obligatorio y mayor o igual que 0
        try {
            int hijos = Integer.parseInt(texto(txtHijos));
            if (hijos < 0) {
                tilHijos.setError("No puede ser negativo"); ok = false;
            } else tilHijos.setError(null);
        } catch (NumberFormatException ex) {
            tilHijos.setError("Ingresa un número válido"); ok = false;
        }
        return ok;
    }

    private void grabar() {
        if (!validar()) return;
        Docente d = new Docente(0, texto(txtNombre), texto(txtPaterno), texto(txtMaterno),
                spSexo.getSelectedItem().toString(),
                Double.parseDouble(texto(txtSueldo)), Integer.parseInt(texto(txtHijos)));
        long id = new DocenteDao(this).insertar(d);
        Toast.makeText(this, id > 0 ? "Docente registrado" : "Error al registrar",
                Toast.LENGTH_SHORT).show();
        if (id > 0) finish();
    }
}