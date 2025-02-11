package edu.badpals.bibliotecaandroid;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;

public class HallBookstore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        User usuarioLoggeado = UserProvider.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_bookstore);



        Toast.makeText(HallBookstore.this, "Bienvenido, " + usuarioLoggeado.getName() + ", con el ID: " + usuarioLoggeado.getId() , Toast.LENGTH_SHORT).show();

    }
}