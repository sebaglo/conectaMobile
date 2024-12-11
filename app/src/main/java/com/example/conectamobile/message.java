package com.example.conectamobile;

public class message {

    public static final int TYPE_SENT = 0;
    public static final int TYPE_RECEIVED = 1;

    private String content;
    private int type;

    // Constructor único
    public void Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    // Métodos para obtener los valores
    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
