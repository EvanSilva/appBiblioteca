package edu.badpals.bibliotecaandroid.API.repository;

import android.util.Log;

import java.util.List;

import edu.badpals.bibliotecaandroid.API.retrofit.ApiClient;
import edu.badpals.bibliotecaandroid.API.retrofit.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {

    private ApiService apiService;

    public ImageRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getImage(String imageName, final BookRepository.ApiCallback<ResponseBody> callback){
        apiService.getImage(imageName).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
