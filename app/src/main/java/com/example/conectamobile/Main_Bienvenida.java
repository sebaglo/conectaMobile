package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Bienvenida extends AppCompatActivity {

    private Button btnComenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);  // Asegúrate de que el layout sea el correcto

        // Inicialización del botón
        btnComenzar = findViewById(R.id.btnComenzar);

        // Configurar el clic del botón
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en el botón, ir a la pantalla de login o a la siguiente pantalla
                Intent intent = new Intent(Main_Bienvenida.this, Main_login.class);  // O la actividad que corresponda
                startActivity(intent);
                finish();  // Finaliza la actividad de bienvenida para evitar que el usuario regrese a ella
            }
        });
    }
}
