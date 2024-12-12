package com.example.conectamobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder> {

    private ArrayList<Contacto> listaContactos;
    private OnContactoClickListener listener;

    // Constructor
    public ContactosAdapter(ArrayList<Contacto> listaContactos, OnContactoClickListener listener) {
        this.listaContactos = listaContactos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout para cada item de la lista
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ContactoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        // Asignamos el texto del contacto al TextView usando el método toString()
        Contacto contacto = listaContactos.get(position);
        holder.tvContacto.setText(contacto.toString());

        // Configurar clic en cada elemento de la lista
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContactoClick(position); // Notificar el clic
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    // Método para actualizar la lista filtrada
    public void actualizarLista(ArrayList<Contacto> nuevaLista) {
        listaContactos = nuevaLista;
        notifyDataSetChanged();  // Notificamos al adaptador que la lista ha cambiado
    }

    // ViewHolder para cada elemento de la lista
    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tvContacto;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContacto = itemView.findViewById(android.R.id.text1);
        }
    }

    // Interfaz para manejar clics en los contactos
    public interface OnContactoClickListener {
        void onContactoClick(int position);
    }
}
