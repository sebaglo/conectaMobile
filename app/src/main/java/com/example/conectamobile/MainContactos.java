package com.example.conectamobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
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
    private ArrayList<Contacto> listaContactos;
    private ContactosAdapter contactosAdapter;
    private int contactoSeleccionado = -1;

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
        btnCerrar = findViewById(R.id.btnCerrarSesion);

        // Inicializar la lista de contactos
        listaContactos = new ArrayList<>();
        listaContactos.add(new Contacto("Juan Pérez", "juan.perez@gmail.com", "123456789"));
        listaContactos.add(new Contacto("Ana Gómez", "ana.gomez@gmail.com", "987654321"));
        listaContactos.add(new Contacto("Carlos Díaz", "carlos.diaz@gmail.com", "456789123"));

        // Configurar el RecyclerView
        rvListaContactos.setLayoutManager(new LinearLayoutManager(this));
        contactosAdapter = new ContactosAdapter(listaContactos, position -> contactoSeleccionado = position);
        rvListaContactos.setAdapter(contactosAdapter);

        // Configurar el botón de agregar contacto
        btnAgregarContacto.setOnClickListener(v -> {
            Intent intent = new Intent(MainContactos.this, agregar.class);
            startActivityForResult(intent, 1); // Inicia la actividad para agregar un nuevo contacto
        });

        btnEditarContacto.setOnClickListener(v -> {
            if (contactoSeleccionado != -1) {
                Contacto contacto = listaContactos.get(contactoSeleccionado);

                Intent intent = new Intent(MainContactos.this, MainEditar.class);
                intent.putExtra("contactoId", contacto.getId()); // Pasa solo el ID
                startActivityForResult(intent, 2); // Inicia la actividad para editar el contacto
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
                ArrayList<Contacto> contactosFiltrados = new ArrayList<>();
                for (Contacto contacto : listaContactos) {
                    if (contacto.toString().toLowerCase().contains(s.toString().toLowerCase())) {
                        contactosFiltrados.add(contacto);
                    }
                }
                contactosAdapter.actualizarLista(contactosFiltrados);
            }

            @Override
            public void afterTextChanged(Editable s) {}
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
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainContactos.this, Main_login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(MainContactos.this, "Sesión cerrada correctamente.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Cuando se agrega un nuevo contacto
            String nombre = data.getStringExtra("nombre");
            String correo = data.getStringExtra("correo");
            String telefono = data.getStringExtra("telefono");

            listaContactos.add(new Contacto(nombre, correo, telefono));
            contactosAdapter.notifyItemInserted(listaContactos.size() - 1); // Notificar la inserción
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            // Cuando se edita un contacto
            String nombre = data.getStringExtra("nombre");
            String correo = data.getStringExtra("correo");
            String telefono = data.getStringExtra("telefono");

            // Actualizar el contacto en la lista
            Contacto contacto = listaContactos.get(contactoSeleccionado);
            contacto.setNombre(nombre);
            contacto.setCorreo(correo);
            contacto.setTelefono(telefono);

            contactosAdapter.notifyItemChanged(contactoSeleccionado); // Notificar el cambio
        }
    }
}
