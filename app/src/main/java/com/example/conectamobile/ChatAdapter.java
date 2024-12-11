package com.example.conectamobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Map<String, String>> messageList;

    // Constantes para tipos de mensajes
    private static final String TYPE_SENT = "sent";
    private static final String TYPE_RECEIVED = "received";

    // Constructor
    public ChatAdapter(List<Map<String, String>> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del mensaje según el tipo
        int layoutId = (viewType == 0)
                ? R.layout.item_message_sent
                : R.layout.item_message_recived;

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // Configurar el contenido del mensaje
        Map<String, String> message = messageList.get(position);
        holder.messageTextView.setText(message.get("content")); // Obtener el contenido del mensaje
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Determinar el tipo del mensaje (enviado o recibido) usando la clave "type"
        String type = messageList.get(position).get("type");
        return TYPE_SENT.equals(type) ? 0 : 1; // Retorna 0 si es enviado, 1 si es recibido
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.tvMessage);
        }
    }
}
