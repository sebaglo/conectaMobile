package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main_registrar extends AppCompatActivity {

    private EditText etNombre, etEmail, etContraseña, etConfirmarContraseña;
    private Button btnRegistrar;
    private TextView tvIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar); // Asegúrate de nombrar el XML como "activity_register.xml"

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

                if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                    Toast.makeText(Main_registrar.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!contraseña.equals(confirmarContraseña)) {
                    Toast.makeText(Main_registrar.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí puedes añadir la lógica para registrar al usuario en tu base de datos
                    Toast.makeText(Main_registrar.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    // Redirigir a la pantalla de inicio de sesión
                    Intent intent = new Intent(Main_registrar.this, Main_login.class);
                    startActivity(intent);
                    finish();
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
}
