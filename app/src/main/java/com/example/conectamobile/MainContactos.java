package com.example.conectamobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainContactos extends AppCompatActivity {

    private EditText etBarraContacto;
    private RecyclerView rvListaContactos;
    private Button btnAgregarContacto, btnEditarContacto, btnPerfil, btnCerrar;
    private ArrayList<String> listaContactos;
    private ContactosAdapter contactosAdapter;
    private int contactoSeleccionado = -1; // Para rastrear el contacto seleccionado

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // Vincular vistas con los IDs del XML
        etBarraContacto = findViewById(R.id.etBarraContacto);
        rvListaContactos = findViewById(R.id.rvListaContactos);
        btnAgregarContacto = findViewById(R.id.btnAgregarContacto);
        btnEditarContacto = findViewById(R.id.btneditarcontacto);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnCerrar = findViewById(R.id.btnCerrarSesion); // Botón Cerrar Sesión

        // Inicializar la lista de contactos
        listaContactos = new ArrayList<>();
        listaContactos.add("Juan Pérez - juan.perez@gmail.com - 123456789");
        listaContactos.add("Ana Gómez - ana.gomez@gmail.com - 987654321");
        listaContactos.add("Carlos Díaz - carlos.diaz@gmail.com - 456789123");

        // Configurar el RecyclerView
        rvListaContactos.setLayoutManager(new LinearLayoutManager(this));
        contactosAdapter = new ContactosAdapter(listaContactos, position -> contactoSeleccionado = position);
        rvListaContactos.setAdapter(contactosAdapter);

        // Configurar el botón de agregar contacto
        btnAgregarContacto.setOnClickListener(v -> {
            Intent intent = new Intent(MainContactos.this, agregar.class);
            startActivityForResult(intent, 1);
        });

        // Configurar el botón de editar contacto
        btnEditarContacto.setOnClickListener(v -> {
            if (contactoSeleccionado != -1) {
                String[] datosContacto = listaContactos.get(contactoSeleccionado).split(" - ");
                String nombre = datosContacto[0];
                String correo = datosContacto[1];
                String telefono = datosContacto[2];

                Intent intent = new Intent(MainContactos.this, MainEditar.class);
                intent.putExtra("contactoId", "id_placeholder");
                intent.putExtra("nombre", nombre);
                intent.putExtra("correo", correo);
                intent.putExtra("telefono", telefono);
                startActivityForResult(intent, 2);
            } else {
                Toast.makeText(MainContactos.this, "Selecciona un contacto para editar.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar la barra de búsqueda
        etBarraContacto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> contactosFiltrados = new ArrayList<>();
                for (String contacto : listaContactos) {
                    if (contacto.toLowerCase().contains(s.toString().toLowerCase())) {
                        contactosFiltrados.add(contacto);
                    }
                }
                contactosAdapter.actualizarLista(contactosFiltrados);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // Configurar el botón para ver el perfil
        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainContactos.this, MainPerfil.class);
            startActivity(intent);
        });

        // Configurar el botón para cerrar sesión
        btnCerrar.setOnClickListener(v -> cerrarSesion());
    }

    private void cerrarSesion() {
        // Eliminar datos de sesión almacenados (si usas SharedPreferences)
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Borra todos los datos de la sesión
        editor.apply();

        // Redirigir al usuario a la pantalla de inicio de sesión
        Intent intent = new Intent(MainContactos.this, Main_login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia el stack de actividades
        startActivity(intent);

        Toast.makeText(MainContactos.this, "Sesión cerrada correctamente.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String nombre = data.getStringExtra("nombre");
            String correo = data.getStringExtra("correo");
            String telefono = data.getStringExtra("telefono");

            String nuevoContacto = nombre + " - " + correo + " - " + telefono;
            listaContactos.add(nuevoContacto);
            contactosAdapter.notifyItemInserted(listaContactos.size() - 1);
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String nombre = data.getStringExtra("nombre");
            String correo = data.getStringExtra("correo");
            String telefono = data.getStringExtra("telefono");

            String contactoActualizado = nombre + " - " + correo + " - " + telefono;
            listaContactos.set(contactoSeleccionado, contactoActualizado);
            contactosAdapter.notifyItemChanged(contactoSeleccionado);
        }
    }
}
