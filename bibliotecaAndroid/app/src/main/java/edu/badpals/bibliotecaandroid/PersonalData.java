package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.BookLending;
import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookRepository;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;
import edu.badpals.bibliotecaandroid.API.repository.UserRepository;

public class PersonalData extends AppCompatActivity {

    RecyclerView recyclerViewPersonalData;
    BookViewModel vm;
    BookAdapter bookAdapter;
    Button btnGoBackToHall;
    UserRepository userRepository;
    BookRepository bookRepository;
    PersonalDataViewModel personalDataViewModel;

    User loggedUser = UserProvider.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        recyclerViewPersonalData = findViewById(R.id.rvPersonalData);
        recyclerViewPersonalData.setLayoutManager(new LinearLayoutManager(this));

        personalDataViewModel = new ViewModelProvider(this).get(PersonalDataViewModel.class);

        personalDataViewModel.getUserLiveData().observe(this, user -> {
            if (user == null) {
                Toast.makeText(this, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        personalDataViewModel.getBooksLentLiveData().observe(this, booksLent -> {

            bookAdapter = new BookAdapter(booksLent);
            recyclerViewPersonalData.setAdapter(bookAdapter);

        });

        btnGoBackToHall = findViewById(R.id.goBackToHall);

        btnGoBackToHall.setOnClickListener(v -> finish());

        vm = new ViewModelProvider(this).get(BookViewModel.class);
        userRepository = new UserRepository();
        bookRepository = new BookRepository();

        vm.getBooksLiveData().observe(this, books -> {

            userRepository.getUserById(loggedUser.getId(), new BookRepository.ApiCallback<User>() {

                @Override
                public void onSuccess(User result) {

                    List<BookLending> librosPrestadosAlUser = new ArrayList<>();

                    for (BookLending bookLending : result.getBookLendings()) {
                        if (bookLending.getReturnDate() == null) {
                            librosPrestadosAlUser.add(bookLending);
                        }
                    }

                    if (librosPrestadosAlUser.isEmpty()) {
                        Toast.makeText(PersonalData.this, "No tienes libros prestados", Toast.LENGTH_SHORT).show();
                    }

                    if (librosPrestadosAlUser.size() > 0) {

                        Log.d("LIBROS ALQUILADOS" , String.valueOf(librosPrestadosAlUser.size()));

                        for (BookLending bookLending : librosPrestadosAlUser) {

                            bookRepository.getBookById(bookLending.getBookId(), new BookRepository.ApiCallback<Book>() {

                                @Override
                                public void onSuccess(Book result) {

                                    personalDataViewModel.addBookLent(result);
                                    bookAdapter.notifyDataSetChanged();

                                }
                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                    }
                }
                @Override
                public void onFailure(Throwable t) {

                }
            });
        });

    }
}