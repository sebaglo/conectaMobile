package com.example.conectamobile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private EditText etMessage;
    private Button btnSend;
    private ChatAdapter chatAdapter;
    private List<Map<String, String>> messageList; // Lista de mensajes

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Vincular vistas con IDs del XML
        rvChat = findViewById(R.id.rvChat);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Inicializar la lista de mensajes
        messageList = new ArrayList<>();

        // Configurar el RecyclerView
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(messageList); // Asegúrate de que el adaptador esté configurado correctamente
        rvChat.setAdapter(chatAdapter);

        // Configurar el botón de enviar
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(message);
                    etMessage.setText(""); // Limpiar el campo de texto
                } else {
                    Toast.makeText(ChatActivity.this, "Escribe un mensaje primero", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendMessage(String message) {
        // Crear un mensaje como un mapa (puedes adaptarlo según tu backend o Firebase)
        Map<String, String> newMessage = new HashMap<>();
        newMessage.put("sender", "yo"); // Identificador del remitente
        newMessage.put("message", message);

        // Agregar el mensaje a la lista
        messageList.add(newMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);

        // Desplazar el RecyclerView al último mensaje
        rvChat.scrollToPosition(messageList.size() - 1);

        // Aquí puedes integrar el envío real a través de un backend o Firebase
        receiveMessage(); // Simula recibir una respuesta
    }

    private void receiveMessage() {
        // Simulación de recepción de mensaje
        Map<String, String> receivedMessage = new HashMap<>();
        receivedMessage.put("sender", "otro"); // Identificador del remitente
        receivedMessage.put("message", "Este es un mensaje de respuesta");

        // Agregar el mensaje recibido a la lista
        messageList.add(receivedMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);

        // Desplazar el RecyclerView al último mensaje
        rvChat.scrollToPosition(messageList.size() - 1);
    }
}
