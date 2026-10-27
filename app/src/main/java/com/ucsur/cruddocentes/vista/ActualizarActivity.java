package com.ucsur.cruddocentes.vista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ucsur.cruddocentes.R;
import com.ucsur.cruddocentes.datos.DocenteDao;
import com.ucsur.cruddocentes.modelo.Docente;

public class ActualizarActivity extends AppCompatActivity {
    private TextInputLayout tilNombre, tilPaterno, tilSueldo, tilHijos;
    private EditText txtNombre, txtPaterno, txtMaterno, txtSueldo, txtHijos;
    private TextView tvCodigo;
    private Spinner spSexo;
    private ArrayAdapter<CharSequence> adSexo;
    private DocenteDao dao;
    private int codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        dao = new DocenteDao(this);

        tvCodigo = findViewById(R.id.tvCodigo);
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

        adSexo = ArrayAdapter.createFromResource(
                this, R.array.sexos, android.R.layout.simple_spinner_item);
        adSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adSexo);

        // 1) Leer el código que mandó MainActivity
        codigo = getIntent().getIntExtra("codigo", -1);

        // 2) Buscar el docente y llenar los campos
        Docente d = dao.buscar(codigo);
        if (d == null) {
            Toast.makeText(this, "Docente no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        tvCodigo.setText("Código: " + d.getCodigo());
        txtNombre.setText(d.getNombre());
        txtPaterno.setText(d.getPaterno());
        txtMaterno.setText(d.getMaterno());
        txtSueldo.setText(String.valueOf(d.getSueldo()));
        txtHijos.setText(String.valueOf(d.getHijos()));
        int posicion = adSexo.getPosition(d.getSexo());
        if (posicion >= 0) spSexo.setSelection(posicion);

        findViewById(R.id.btnActualizar).setOnClickListener(v -> actualizar());
        findViewById(R.id.btnEliminar).setOnClickListener(v -> confirmarEliminar());
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

        try {
            double sueldo = Double.parseDouble(texto(txtSueldo));
            if (sueldo <= 0) {
                tilSueldo.setError("Debe ser mayor que 0"); ok = false;
            } else tilSueldo.setError(null);
        } catch (NumberFormatException ex) {
            tilSueldo.setError("Ingresa un sueldo válido"); ok = false;
        }

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

    private void actualizar() {
        if (!validar()) return;
        Docente d = new Docente(codigo, texto(txtNombre), texto(txtPaterno), texto(txtMaterno),
                spSexo.getSelectedItem().toString(),
                Double.parseDouble(texto(txtSueldo)), Integer.parseInt(texto(txtHijos)));
        if (dao.actualizar(d) > 0) {
            Toast.makeText(this, "Docente actualizado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmarEliminar() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar docente")
                .setMessage("¿Seguro que deseas eliminar este registro?")
                .setPositiveButton("Sí", (dlg, w) -> {
                    if (dao.eliminar(codigo) > 0) {
                        Toast.makeText(this, "Docente eliminado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}