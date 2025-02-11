package edu.badpals.bibliotecaandroid;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.BookRepository;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;
import edu.badpals.bibliotecaandroid.API.repository.UserRepository;

public class MainActivity extends AppCompatActivity {

    UserRepository userRepository = new UserRepository();

    Button btnLogin;
    EditText email, password;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.txtInputEmail);
        password = findViewById(R.id.txtInputPassword);

        List<User> users = new ArrayList<>();

        btnLogin.setOnClickListener(view -> {

            userRepository.getUsers(new BookRepository.ApiCallback<List<User>>() {
                @Override
                public void onSuccess(List<User> result) {

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });


            userRepository.getUsers(new BookRepository.ApiCallback<List<User>>() {

                @Override
                public void onSuccess(List<User> result) {

                    Boolean usuarioencontrado = false;

                    for (User user : result) {

                        if (user.getEmail().equals(email.getText().toString()) && user.getPasswordHash().equals(password.getText().toString())) {

                            User usuarioLoggeado = UserProvider.getInstance();

                            usuarioLoggeado.setId(user.getId());
                            usuarioLoggeado.setName(user.getName());
                            usuarioLoggeado.setEmail(user.getEmail());
                            usuarioLoggeado.setPasswordHash(user.getPasswordHash());
                            usuarioLoggeado.setDateJoined(user.getDateJoined());
                            usuarioLoggeado.setBookLendings(user.getBookLendings());
                            usuarioLoggeado.setProfilePicture(user.getProfilePicture());

                            Intent intent = new Intent(MainActivity.this, HallBookstore.class);
                            usuarioencontrado = true;
                            startActivity(intent);
                            finish();

                        } else {

                            Log.d(TAG, "USUARIO METIDO: " + email.getText().toString());
                            Log.d(TAG, "CONTRASEÑA METIDA: " + password.getText().toString());

                        }
                    }

                    if (usuarioencontrado == false) {

                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(MainActivity.this, "Conexion con la base de datos fallida", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}