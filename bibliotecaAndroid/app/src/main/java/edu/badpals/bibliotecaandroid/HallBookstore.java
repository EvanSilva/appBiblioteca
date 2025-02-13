package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;

public class HallBookstore extends AppCompatActivity {

    Button btnGoToBookExhibitor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        User usuarioLoggeado = UserProvider.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_bookstore);
        Toast.makeText(HallBookstore.this, "Bienvenido, " + usuarioLoggeado.getName() + ", con el ID: " + usuarioLoggeado.getId() , Toast.LENGTH_SHORT).show();
        btnGoToBookExhibitor = findViewById(R.id.btnGotoBookExhibitor);

        btnGoToBookExhibitor.setOnClickListener(view -> {

            Intent bookExhibitor = new Intent(view.getContext(), BookExhibitor.class);
            startActivity(bookExhibitor);

        });

    }
}