package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class agregar extends AppCompatActivity {

    private EditText etBarraContacto;
    private RecyclerView rvListaContactos;
    private Button btnAgregarContacto, btnEditarContacto;

    private ArrayList<String> listaContactos;
    private ContactosAdapter contactosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // Vinculamos las vistas con los IDs del XML
        etBarraContacto = findViewById(R.id.etBarraContacto);
        rvListaContactos = findViewById(R.id.rvListaContactos);
        btnAgregarContacto = findViewById(R.id.btnAgregarContacto);
        btnEditarContacto = findViewById(R.id.btneditarcontacto);

        // Inicializar la lista de contactos
        listaContactos = new ArrayList<>();
        listaContactos.add("Juan Pérez");
        listaContactos.add("Ana Gómez");
        listaContactos.add("Carlos Díaz");

        // Configurar el RecyclerView
        rvListaContactos.setLayoutManager(new LinearLayoutManager(this));
        contactosAdapter = new ContactosAdapter(listaContactos);
        rvListaContactos.setAdapter(contactosAdapter);

        // Lógica para el botón de agregar contacto
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar la actividad AgregarContactoActivity
                Intent intent = new Intent(agregar.this, MainContactos.class);
                startActivity(intent);
            }
        });

        // Lógica para el botón de editar contacto
        btnEditarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes abrir una nueva actividad o mostrar un diálogo para editar un contacto
                if (listaContactos.size() > 0) {
                    String contactoEditar = listaContactos.get(0); // Ejemplo de cómo editar el primer contacto
                    listaContactos.set(0, contactoEditar + " (editado)");
                    contactosAdapter.notifyItemChanged(0); // Notificar al adaptador que se ha cambiado un item
                    Toast.makeText(agregar.this, "Contacto editado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(agregar.this, "No hay contactos para editar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lógica para la barra de búsqueda
        etBarraContacto.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filtrar los contactos de acuerdo con el texto ingresado en la barra de búsqueda
                ArrayList<String> contactosFiltrados = new ArrayList<>();
                for (String contacto : listaContactos) {
                    if (contacto.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        contactosFiltrados.add(contacto);
                    }
                }
                contactosAdapter.actualizarLista(contactosFiltrados); // Actualizamos la lista mostrada
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
    }
}
