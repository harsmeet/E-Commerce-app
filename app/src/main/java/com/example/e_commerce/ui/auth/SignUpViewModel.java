package com.example.e_commerce.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.HashMap;


public class SignUpViewModel extends AndroidViewModel {

    // Object from Repo
    private SignUpRepo repo;


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
     * Get a message of response of sign up
     *
     * @return Mutable live data of String
     */
    public MutableLiveData<String> getMessage() {
        return repo.getSignUpResponse();
    }


    /**
     * Get the data from activity
     *
     * @param map is a hash map of String
     */
    public void getData(HashMap<String, String> map) {
        repo.uploadData(map);
    }
}
