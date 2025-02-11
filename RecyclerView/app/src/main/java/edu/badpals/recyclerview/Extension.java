package edu.badpals.recyclerview;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Extension extends AppCompatActivity {

    TextView tvnombre, tvdescripcion;
    ImageView imgExtension;
    Button btnExtension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);

        Intent intent = getIntent();

        String nombre = intent.getStringExtra("nombre");
        String descripcion = intent.getStringExtra("descripcion");
        int img = intent.getIntExtra("img", 0);

        tvnombre = findViewById(R.id.txtTituloExtension);
        tvdescripcion = findViewById(R.id.txtDescripcionExtension);
        imgExtension = findViewById(R.id.imgExtension);

        tvnombre.setText(nombre);
        tvdescripcion.setText(descripcion);
        imgExtension.setImageResource(img);

        btnExtension = findViewById(R.id.btnRegresarExtension);

        btnExtension.setOnClickListener(view -> {

            Intent iBackToMain = new Intent(view.getContext(), MainActivity.class);

            startActivity(iBackToMain);

        });



    }
}