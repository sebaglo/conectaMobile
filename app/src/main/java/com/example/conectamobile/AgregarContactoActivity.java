package com.example.conectamobile;

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

public class AgregarContactoActivity extends AppCompatActivity {

    private EditText etNombreContactoG, etCorreoContacto, etTelefonoContacto;
    private Button btnGuardarContacto;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();

        // Vincular vistas del XML
        etNombreContactoG = findViewById(R.id.txtNombreContactoG);
        etCorreoContacto = findViewById(R.id.etCorreoContacto);
        etTelefonoContacto = findViewById(R.id.etTelefonoContacto);
        btnGuardarContacto = findViewById(R.id.btnGuardarContacto);

        // Configurar botón Guardar
        btnGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarContacto();
            }
        });
    }

    private void agregarContacto() {
        // Obtener valores de los campos
        String nombre = etNombreContactoG.getText().toString().trim();
        String correo = etCorreoContacto.getText().toString().trim();
        String telefono = etTelefonoContacto.getText().toString().trim();

        // Validar campos vacíos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(telefono)) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto para almacenar en Firebase
        Map<String, Object> contacto = new HashMap<>();
        contacto.put("nombre", nombre);
        contacto.put("correo", correo);
        contacto.put("telefono", telefono);

        // Guardar en Firebase Firestore
        mFirestore.collection("contactos")
                .add(contacto)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Contacto guardado con éxito.", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos
                    etNombreContactoG.setText("");
                    etCorreoContacto.setText("");
                    etTelefonoContacto.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar el contacto.", Toast.LENGTH_SHORT).show();
                });
    }
}
