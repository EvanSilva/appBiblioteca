package edu.badpals.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.recyclerview.back.TarjetaAdapter;
import edu.badpals.recyclerview.back.TarjetaViewModel;
import edu.badpals.recyclerview.entities.Tarjeta;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ImageView imageView, imageIsVisited;
    TextView textView;
    Button button;

    TarjetaViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(TarjetaViewModel.class);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TarjetaAdapter(vm.getTarjetasLiveData().getValue()));


    }



}