package edu.badpals.bibliotecaandroid;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.badpals.bibliotecaandroid.API.models.User;
import edu.badpals.bibliotecaandroid.API.repository.UserRepository;


public class MainActivityVM extends ViewModel {

    List<User> users;




    MutableLiveData<User> user;

    public MainActivityVM(){

        user = new MutableLiveData<>();

    }



}
