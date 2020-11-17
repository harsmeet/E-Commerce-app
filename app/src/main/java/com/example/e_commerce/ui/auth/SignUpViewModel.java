package com.example.e_commerce.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.HashMap;


public class SignUpViewModel extends AndroidViewModel {


    /**
     * Initialization
     */
    private final SignUpRepo repo;


    /**
     * Constructor of our class
     *
     * @param application is an application context
     */
    public SignUpViewModel(@NonNull Application application) {
        super(application);
        repo = new SignUpRepo();
    }


    /**
     * Get a message of coming response
     *
     * @return Mutable live data of String
     */
    public MutableLiveData<String> getMessage() {
        return repo.getSignUpResponse();
    }


    /**
     * Get data from activity to pass it to repo
     *
     * @param map is a hash map of String
     */
    public void getData(HashMap<String, String> map) {
        repo.uploadData(map);
    }
}