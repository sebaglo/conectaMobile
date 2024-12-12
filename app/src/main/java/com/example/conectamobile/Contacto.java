package com.example.conectamobile;

import java.io.Serializable;
import java.util.UUID;

public class Contacto implements Serializable {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;

    // Constructor
    public Contacto(String nombre, String correo, String telefono) {
        this.id = UUID.randomUUID().toString();  // Aseguramos que cada contacto tenga un ID único
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre + " - " + correo + " - " + telefono;
    }
}
