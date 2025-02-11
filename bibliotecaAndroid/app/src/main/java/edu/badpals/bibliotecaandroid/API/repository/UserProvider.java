package edu.badpals.bibliotecaandroid.API.repository;

import edu.badpals.bibliotecaandroid.API.models.User;

public class UserProvider {

    public static User instance;

    private UserProvider(User usuario) {
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }


}
