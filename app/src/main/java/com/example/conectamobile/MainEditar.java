package com.example.conectamobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainEditar extends AppCompatActivity {

    private EditText etNombreContactoEditar, etCorreoContactoEditar, etTelefonoContactoEditar;
    private Button btnGuardarCambios;
    private Button btnVolver;
    private FirebaseFirestore mFirestore;
    private String contactoId; // ID del contacto que se está editando

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contactos);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();

        // Vincular vistas con IDs del XML
        etNombreContactoEditar = findViewById(R.id.etNombreContactoEditar);
        etCorreoContactoEditar = findViewById(R.id.etCorreoContactoEditar);
        etTelefonoContactoEditar = findViewById(R.id.etTelefonoContactoEditar);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnVolver = (findViewById(R.id.btnVolver));

        // Obtener los datos del Intent
        contactoId = getIntent().getStringExtra("contactoId");
        if (contactoId == null || contactoId.isEmpty()) {
            Toast.makeText(this, "Error: No se encontró el ID del contacto.", Toast.LENGTH_SHORT).show();
            finish();  // Finaliza la actividad si no se pasó el ID.
            return;
        }

        String nombre = getIntent().getStringExtra("nombre");
        String correo = getIntent().getStringExtra("correo");
        String telefono = getIntent().getStringExtra("telefono");

        // Rellenar los campos con los datos actuales del contacto
        etNombreContactoEditar.setText(nombre);
        etCorreoContactoEditar.setText(correo);
        etTelefonoContactoEditar.setText(telefono);

        // Configurar el botón para guardar los cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        // Configurar el botón "Volver"
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainEditar.this, MainContactos.class);
                startActivity(intent);
                finish(); // Opcional: Cierra esta actividad para evitar que se acumule en el stack
            }
        });
    }

    private void guardarCambios() {
        // Obtener los valores de los campos
        String nuevoNombre = etNombreContactoEditar.getText().toString().trim();
        String nuevoCorreo = etCorreoContactoEditar.getText().toString().trim();
        String nuevoTelefono = etTelefonoContactoEditar.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(nuevoNombre) || TextUtils.isEmpty(nuevoCorreo) || TextUtils.isEmpty(nuevoTelefono)) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que el documento existe antes de actualizarlo
        mFirestore.collection("contactos").document(contactoId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Crear un objeto con los nuevos datos
                        Map<String, Object> contactoActualizado = new HashMap<>();
                        contactoActualizado.put("nombre", nuevoNombre);
                        contactoActualizado.put("correo", nuevoCorreo);
                        contactoActualizado.put("telefono", nuevoTelefono);

                        // Actualizar el contacto en Firebase Firestore
                        mFirestore.collection("contactos").document(contactoId)
                                .update(contactoActualizado)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(MainEditar.this, "Contacto actualizado con éxito.", Toast.LENGTH_SHORT).show();
                                    finish(); // Cerrar la actividad después de guardar los cambios
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainEditar.this, "Error al actualizar el contacto.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(MainEditar.this, "El contacto no existe.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainEditar.this, "Error al obtener el contacto.", Toast.LENGTH_SHORT).show();
                });
    }
}
