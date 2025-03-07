package edu.badpals.bibliotecaandroid.API.repository;

import android.util.Log;

import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.BookLending;
import edu.badpals.bibliotecaandroid.API.models.BookLendingForm;
import edu.badpals.bibliotecaandroid.API.retrofit.ApiClient;
import edu.badpals.bibliotecaandroid.API.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookLendingRepository {
    private ApiService apiService;

    public BookLendingRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getAllLendings(final BookRepository.ApiCallback<List<BookLending>> callback) {
        apiService.getLendings().enqueue(new Callback<List<BookLending>>() {
            @Override
            public void onResponse(Call<List<BookLending>> call, Response<List<BookLending>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<BookLending>> call, Throwable t) {
                Log.e("BookLendingRepository", "Error fetching lendings", t);
                callback.onFailure(t);
            }
        });
    }

    public void lendBook(Integer userId, Integer bookId, final BookRepository.ApiCallback<Boolean> callback) {
        apiService.lendBook(userId, bookId).enqueue(new Callback<BookLending>() {
            @Override
            public void onResponse(Call<BookLending> call, Response<BookLending> response) {
                callback.onSuccess(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<BookLending> call, Throwable t) {
                Log.e("BookLendingRepository", "Error lending book", t);
                callback.onFailure(t);
            }
        });
    }

    public void returnBook(int id, final BookRepository.ApiCallback<Boolean> callback) {
        apiService.returnBook(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("BookLendingRepository", "Error returning book", t);
                callback.onFailure(t);
            }
        });
    }
}

