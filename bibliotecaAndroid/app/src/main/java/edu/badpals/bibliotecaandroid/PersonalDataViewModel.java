package edu.badpals.bibliotecaandroid;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.Book;
import edu.badpals.bibliotecaandroid.API.models.User;

public class PersonalDataViewModel extends androidx.lifecycle.ViewModel {
    MutableLiveData<User> userLiveData = new MutableLiveData<>();
    MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<List<Book>> getBooksLentLiveData() {
        return booksLiveData;
    }

    public void addBookLent(Book result) {
        List<Book> books = this.booksLiveData.getValue();

        if (books == null) {
            books = new ArrayList<>();
        }

        books.add(result);
        this.booksLiveData.setValue(books);
    }

}
