package com.example.conectamobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder> {

    private ArrayList<String> listaContactos;

    // Constructor
    public ContactosAdapter(ArrayList<String> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout para cada item de la lista
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ContactoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactoViewHolder holder, int position) {
        // Asignamos el texto del contacto al TextView
        String contacto = listaContactos.get(position);
        holder.tvContacto.setText(contacto);
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    // Método para actualizar la lista filtrada
    public void actualizarLista(ArrayList<String> nuevaLista) {
        listaContactos = nuevaLista;
        notifyDataSetChanged();  // Notificamos al adaptador que la lista ha cambiado
    }

    // ViewHolder para cada elemento de la lista
    public static class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tvContacto;

        public ContactoViewHolder(View itemView) {
            super(itemView);
            tvContacto = itemView.findViewById(android.R.id.text1);
        }
    }
}
