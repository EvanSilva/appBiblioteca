package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;

public class BookExhibitor extends AppCompatActivity {

    RecyclerView recyclerViewBookExhibitor;
    BookViewModel vm;
    EditText etFltrado;
    BookAdapter bookAdapter;
    Button btnQrSeach;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_exhibitor_bookstore);

        etFltrado = findViewById(R.id.etFiltrado);
        btnQrSeach = findViewById(R.id.btnQrSearch);

        recyclerViewBookExhibitor = findViewById(R.id.rvBookExhibitor);
        recyclerViewBookExhibitor.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(BookViewModel.class);
        vm.cargarBooks();

        bookAdapter = new BookAdapter(new ArrayList<>());
        recyclerViewBookExhibitor.setAdapter(bookAdapter);

        vm.getBooksLiveData().observe(this, books -> {
            if (books != null) {
                bookAdapter = new BookAdapter(books);
                recyclerViewBookExhibitor.setAdapter(bookAdapter);
            }
        });

        etFltrado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filtro = s.toString().toLowerCase();
                List<Book> books = vm.getBooksLiveData().getValue();
                if (books != null) {
                    List<Book> booksFiltrados = books.stream()
                            .filter(book -> book.getTitle().toLowerCase().contains(filtro) ||
                                    book.getAuthor().toLowerCase().contains(filtro))
                            .collect(Collectors.toList());

                    bookAdapter = new BookAdapter(booksFiltrados);
                    recyclerViewBookExhibitor.setAdapter(bookAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



    }


}
