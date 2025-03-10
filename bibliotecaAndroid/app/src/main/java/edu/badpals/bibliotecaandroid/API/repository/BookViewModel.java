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

    public void cargarBooks() {

        bookRepository.getBooks(new BookRepository.ApiCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> result) {
                booksLiveData.setValue(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

}
