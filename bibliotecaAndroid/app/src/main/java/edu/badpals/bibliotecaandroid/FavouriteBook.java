package edu.badpals.bibliotecaandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookRepository;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;

public class FavouriteBook extends AppCompatActivity {

    RecyclerView recyclerViewFavouriteBook;
    Button btnGoBackToHall;
    BookViewModel vm;

    BookRepository bookRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookRepository = new BookRepository();

        btnGoBackToHall = findViewById(R.id.btnBackToMainFromFavorite);
        btnGoBackToHall.setOnClickListener(view -> {
            finish();
        });

        recyclerViewFavouriteBook = findViewById(R.id.rvLibroFavorito);
        recyclerViewFavouriteBook.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(BookViewModel.class);
        vm.cargarBooks();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Integer id = sp.getInt("idLibroPreferido", 1);

        bookRepository.getBookById(id, new BookRepository.ApiCallback<Book>() {
            @Override
            public void onSuccess(Book result) {

                List<Book> libroFavorito = List.of(result);

                recyclerViewFavouriteBook.setAdapter(new BookAdapter(libroFavorito));

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

















    }
}