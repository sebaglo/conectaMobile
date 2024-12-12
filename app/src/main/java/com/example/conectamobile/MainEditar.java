package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainEditar extends AppCompatActivity {

    private EditText etNombreContactoEditar, etCorreoContactoEditar, etTelefonoContactoEditar;
    private Button btnGuardarCambios;
    private Button btnVolver;
    private ArrayList<Contacto> listaContactos; // Asegúrate de tener la lista en MainEditar también
    private String contactoId; // ID del contacto a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contactos);

        // Vincular vistas con IDs del XML
        etNombreContactoEditar = findViewById(R.id.etNombreContactoEditar);
        etCorreoContactoEditar = findViewById(R.id.etCorreoContactoEditar);
        etTelefonoContactoEditar = findViewById(R.id.etTelefonoContactoEditar);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener el ID del contacto seleccionado
        contactoId = getIntent().getStringExtra("contactoId");

        if (contactoId == null) {
            Toast.makeText(this, "Error: No se encontró el contacto.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Obtener la lista de contactos desde MainContactos (pasada por Intent)
        listaContactos = (ArrayList<Contacto>) getIntent().getSerializableExtra("listaContactos");

        if (listaContactos == null || listaContactos.isEmpty()) {
            Toast.makeText(this, "Error: No se encontraron contactos.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Buscar el contacto por ID
        Contacto contacto = obtenerContactoPorId(contactoId);

        if (contacto == null) {
            Toast.makeText(this, "Error: No se encontró el contacto.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Rellenar los campos con los datos actuales del contacto
        etNombreContactoEditar.setText(contacto.getNombre());
        etCorreoContactoEditar.setText(contacto.getCorreo());
        etTelefonoContactoEditar.setText(contacto.getTelefono());

        // Configurar el botón para guardar los cambios
        btnGuardarCambios.setOnClickListener(v -> guardarCambios(contacto));

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(MainEditar.this, MainContactos.class);
            intent.putExtra("listaContactos", listaContactos); // Pasar la lista actualizada
            startActivity(intent);
            finish(); // Opcional: Cierra esta actividad para evitar que se acumule en el stack
        });
    }

    private Contacto obtenerContactoPorId(String id) {
        for (Contacto contacto : listaContactos) {
            if (contacto.getId().equals(id)) {
                return contacto;
            }
        }
        return null; // Si no se encuentra el contacto
    }

    private void guardarCambios(Contacto contacto) {
        // Obtener los valores de los campos
        String nuevoNombre = etNombreContactoEditar.getText().toString().trim();
        String nuevoCorreo = etCorreoContactoEditar.getText().toString().trim();
        String nuevoTelefono = etTelefonoContactoEditar.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(nuevoNombre) || TextUtils.isEmpty(nuevoCorreo) || TextUtils.isEmpty(nuevoTelefono)) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar el contacto
        contacto.setNombre(nuevoNombre);
        contacto.setCorreo(nuevoCorreo);
        contacto.setTelefono(nuevoTelefono);

        // Volver a MainContactos con los cambios guardados
        Intent intent = new Intent(MainEditar.this, MainContactos.class);
        intent.putExtra("listaContactos", listaContactos); // Pasar la lista actualizada
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }
}
