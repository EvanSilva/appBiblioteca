package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;

public class BookExhibitor extends AppCompatActivity {

    RecyclerView recyclerViewBookExhibitor;
    BookViewModel vm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_exhibitor_bookstore);

        vm = new ViewModelProvider(this).get(BookViewModel.class);

        vm.cargarBooks();


        vm.getBooksLiveData().observe(this, books -> {
            recyclerViewBookExhibitor.setAdapter(new BookAdapter(books));
        });

        recyclerViewBookExhibitor = findViewById(R.id.rvBookExhibitor);
        recyclerViewBookExhibitor.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBookExhibitor.setAdapter(new BookAdapter(vm.getBooksLiveData().getValue()));

    }

}