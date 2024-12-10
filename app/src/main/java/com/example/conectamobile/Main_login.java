package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main_login extends AppCompatActivity {

    private EditText etLoginEmail, etLoginContraseña;
    private Button btnLogin;
    private TextView tvGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Vincular vistas del XML con el código
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginContraseña = findViewById(R.id.etLoginContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        // Configurar botón de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String contraseña = etLoginContraseña.getText().toString().trim();

                if (email.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(Main_login.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica para autenticar al usuario
                    // Esto puede conectarse a una base de datos o API
                    Toast.makeText(Main_login.this, "Iniciando sesión con: " + email, Toast.LENGTH_SHORT).show();

                    // Redirigir al usuario a otra actividad después de un inicio de sesión exitoso
                    Intent intent = new Intent(Main_login.this,MainContactos .class);
                    startActivity(intent);
                }
            }
        });

        // Configurar texto para redirigir al registro
        tvGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la pantalla de registro
                Intent intent = new Intent(Main_login.this, Main_registrar.class);
                startActivity(intent);
            }
        });
    }
}
