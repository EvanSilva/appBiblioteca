package edu.badpals.bibliotecaandroid.API.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

    BookRepository bookRepository = new BookRepository();

    public BookViewModel() {
        cargarBooks();
    }

    private void cargarBooks() {
        bookRepository.getBooks(new BookRepository.ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {

                booksLiveData.postValue(result); // Se actualiza LiveData con los datos recibidos
            }

            @Override
            public void onFailure(Throwable t) {
                // Mostrar mensaje de error

            }
        });
    }

}
