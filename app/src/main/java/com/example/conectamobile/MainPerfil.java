package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainPerfil extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private Button btnCambiarFoto, btnGuardarPerfil, btnEditarPerfil;
    private EditText etNombreCompleto, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario); // Asegúrate de que el XML se llame "activity_perfil.xml" o el nombre correcto

        // Vinculamos las vistas con los IDs del XML
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerdil);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfilUsuario);
        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etEmail = findViewById(R.id.etEmail);

        // Acción para cambiar la foto de perfil
        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica para seleccionar una imagen (por ejemplo, usando un Intent para abrir la galería de fotos)
                // Para propósitos de ejemplo, estableceremos una imagen de ejemplo

            }
        });

        // Acción para guardar cambios en el perfil
        btnGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreCompleto = etNombreCompleto.getText().toString();
                String email = etEmail.getText().toString();

                if (nombreCompleto.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainPerfil.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar cambios (puedes guardarlo en una base de datos o en SharedPreferences)
                    // Por ejemplo, solo mostramos un Toast:
                    Toast.makeText(MainPerfil.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción para permitir editar el perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes activar los campos EditText para permitir la edición
                etNombreCompleto.setEnabled(true);
                etEmail.setEnabled(true);
                btnGuardarPerfil.setVisibility(View.VISIBLE);
            }
        });
    }
}
