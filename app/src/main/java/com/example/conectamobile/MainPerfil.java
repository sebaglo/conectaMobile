package com.example.conectamobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainPerfil extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private Button btnCambiarFoto, btnGuardarPerfil, btnEditarPerfil;
    private EditText etNombreCompleto, etEmail;

    private FirebaseFirestore db;  // Instancia de Firestore
    private FirebaseStorage storage; // Instancia de FirebaseStorage

    private String userId = "usuario_id_placeholder"; // Obtén el ID del usuario desde FirebaseAuth en un caso real
    private static final int IMAGE_PICK_REQUEST = 1; // Código de solicitud para abrir la galería

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario); // Asegúrate de que el XML se llame "activity_perfil_usuario.xml" o el nombre correcto

        // Vinculamos las vistas con los IDs del XML
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerdil);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfilUsuario);
        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etEmail = findViewById(R.id.etEmail);

        // Inicializar Firebase Firestore y Firebase Storage
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        // Acción para cambiar la foto de perfil
        btnCambiarFoto.setOnClickListener(v -> {
            // Abre la galería de imágenes
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_REQUEST);  // El 1 es el código de solicitud
        });

        // Acción para guardar cambios en el perfil
        btnGuardarPerfil.setOnClickListener(v -> {
            String nombreCompleto = etNombreCompleto.getText().toString();
            String email = etEmail.getText().toString();

            if (nombreCompleto.isEmpty() || email.isEmpty()) {
                Toast.makeText(MainPerfil.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar cambios en Firestore
                guardarPerfil(nombreCompleto, email);
            }
        });

        // Acción para permitir editar el perfil
        btnEditarPerfil.setOnClickListener(v -> {
            // Activar los campos EditText para permitir la edición
            etNombreCompleto.setEnabled(true);
            etEmail.setEnabled(true);
            btnGuardarPerfil.setVisibility(View.VISIBLE);  // Mostrar el botón de guardar
        });

        // Cargar los datos del perfil desde Firestore
        cargarPerfil();

        // Configuración del evento de retroceso para Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Lógica personalizada para el retroceso si es necesario
                    finish(); // O lo que desees hacer antes de cerrar la actividad
                }
            });
        }
    }

    // Método para guardar el perfil en Firebase Firestore
    private void guardarPerfil(String nombreCompleto, String email) {
        // Crear un objeto UserProfile con los datos actualizados
        UserProfile userProfile = new UserProfile(nombreCompleto, email);

        // Guardar en Firestore (actualizando el documento del usuario)
        db.collection("usuarios").document(userId)
                .set(userProfile)  // Usamos .set() para sobrescribir el documento con los nuevos datos
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainPerfil.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                    // Desactivar los campos de texto después de guardar
                    etNombreCompleto.setEnabled(false);
                    etEmail.setEnabled(false);
                    btnGuardarPerfil.setVisibility(View.GONE);  // Ocultar el botón de guardar
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainPerfil.this, "Error al guardar cambios", Toast.LENGTH_SHORT).show();
                });
    }

    // Método para cargar el perfil desde Firebase Firestore
    private void cargarPerfil() {
        db.collection("usuarios").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String nombreCompleto = documentSnapshot.getString("nombreCompleto");
                        String email = documentSnapshot.getString("email");
                        String profileImageUrl = documentSnapshot.getString("profileImage");  // Obtener la URL de la imagen

                        etNombreCompleto.setText(nombreCompleto);
                        etEmail.setText(email);

                        // Cargar la imagen de perfil si existe
                        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                            Picasso.get().load(profileImageUrl).into(ivProfilePicture);  // Usando Picasso para cargar la imagen
                        }
                    } else {
                        Toast.makeText(MainPerfil.this, "Perfil no encontrado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainPerfil.this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
                });
    }

    // Manejar la imagen seleccionada desde la galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_REQUEST && resultCode == RESULT_OK && data != null) {
            // Obtener la URI de la imagen seleccionada
            Uri selectedImageUri = data.getData();

            // Mostrar la imagen en el ImageView
            ivProfilePicture.setImageURI(selectedImageUri);

            // Subir la imagen a Firebase Storage
            uploadImageToFirebase(selectedImageUri);
        }
    }

    // Subir la imagen seleccionada a Firebase Storage
    private void uploadImageToFirebase(Uri imageUri) {
        // Obtener una referencia a Firebase Storage
        StorageReference storageRef = storage.getReference();

        // Crear una referencia única para la imagen
        String imageName = "profile_pictures/" + userId + ".jpg";  // Usando el ID del usuario como nombre
        StorageReference imageRef = storageRef.child(imageName);

        // Subir la imagen
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Obtener la URL de la imagen subida
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Actualizar la imagen del perfil en Firestore con la URL
                        updateProfileImageInFirestore(uri.toString());
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainPerfil.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                });
    }

    // Actualizar la URL de la foto de perfil en Firestore
    private void updateProfileImageInFirestore(String imageUrl) {
        db.collection("usuarios").document(userId)
                .update("profileImage", imageUrl)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainPerfil.this, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainPerfil.this, "Error al actualizar la foto de perfil", Toast.LENGTH_SHORT).show();
                });
    }

    // Clase interna para almacenar los datos del perfil del usuario
    public static class UserProfile {
        private String nombreCompleto;
        private String email;

        public UserProfile(String nombreCompleto, String email) {
            this.nombreCompleto = nombreCompleto;
            this.email = email;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public String getEmail() {
            return email;
        }
    }
}
