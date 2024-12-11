package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class Main_registrar extends AppCompatActivity {

    private EditText etNombre, etEmail, etContraseña, etConfirmarContraseña;
    private Button btnRegistrar;
    private TextView tvIniciarSesion;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar); // Asegúrate de nombrar el XML como "activity_register.xml"

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();

        // Vincular vistas con IDs del XML
        etNombre = findViewById(R.id.Nombre);
        etEmail = findViewById(R.id.Email);
        etContraseña = findViewById(R.id.Contraseña);
        etConfirmarContraseña = findViewById(R.id.ConfirmarContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvIniciarSesion = findViewById(R.id.Registrar);

        // Configurar botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String contraseña = etContraseña.getText().toString().trim();
                String confirmarContraseña = etConfirmarContraseña.getText().toString().trim();

                // Validaciones
                if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                    Toast.makeText(Main_registrar.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!contraseña.equals(confirmarContraseña)) {
                    Toast.makeText(Main_registrar.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamar al método para guardar el usuario en Firestore
                    guardarUsuarioEnFirestore(nombre, email, contraseña);
                }
            }
        });

        // Configurar texto para redirigir al inicio de sesión
        tvIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la pantalla de inicio de sesión
                Intent intent = new Intent(Main_registrar.this, Main_login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Método para guardar usuario en Firestore
    private void guardarUsuarioEnFirestore(String nombre, String email, String contraseña) {
        // Crear un mapa con los datos del usuario
        Usuario nuevoUsuario = new Usuario(nombre, email, contraseña);

        // Agregar el usuario a la colección "usuarios" en Firestore
        mFirestore.collection("usuarios")
                .add(nuevoUsuario)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Main_registrar.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla de inicio de sesión
                    Intent intent = new Intent(Main_registrar.this, Main_login.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Main_registrar.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                });
    }
}
