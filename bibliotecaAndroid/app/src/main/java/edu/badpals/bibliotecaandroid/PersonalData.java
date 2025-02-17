package edu.badpals.bibliotecaandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.BookLending;
import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.BookAdapter;
import edu.badpals.bibliotecaandroid.API.repository.BookViewModel;
import edu.badpals.bibliotecaandroid.API.repository.UserProvider;

public class PersonalData extends AppCompatActivity {

    RecyclerView recyclerViewPersonalData;
    BookViewModel vm;
    BookAdapter bookAdapter;
    Button btnGoBackToHall;

    User user = UserProvider.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);


        recyclerViewPersonalData = findViewById(R.id.rvPersonalData);
        recyclerViewPersonalData.setLayoutManager(new LinearLayoutManager(this));

        vm = new ViewModelProvider(this).get(BookViewModel.class);

        vm.getBooksLiveData().observe(this, books -> {

            // REHACER CODIGO PARA BUSCAR DIRECTAMENTE DESDE BOOKLENDINGS


            if (books != null && !books.isEmpty()) {

                List<Book> lentBooksForThisUser = new ArrayList<>();
                for (Book book : books) {

                    List<BookLending> bookLendings = book.getBookLendings();
                    if (bookLendings != null && !bookLendings.isEmpty()) {

                        BookLending lastLending = bookLendings.get(bookLendings.size() - 1);
                        if (lastLending.getUserId() == user.getId()) {
                            lentBooksForThisUser.add(book);
                        }
                    }
                }
                bookAdapter = new BookAdapter(lentBooksForThisUser);
                recyclerViewPersonalData.setAdapter(bookAdapter);

            } else {
                Toast.makeText(this, "No hay libros prestados para este usuario", Toast.LENGTH_SHORT).show();
            }
        });




    }
}