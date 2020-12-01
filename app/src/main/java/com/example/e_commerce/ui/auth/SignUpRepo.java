package com.example.e_commerce.ui.auth;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.register.Data;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utils.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpRepo extends GlobalRepo {


    private static final String TAG = SignUpRepo.class.getSimpleName();
    /**
     * Initialization
     */
    Data data = new Data();
    private final MutableLiveData<String> signUpResponse = new MutableLiveData<>();
    String email;
    String password;
    String firstName;
    String lastName;


    /**
     * Default constructor
     */
    public SignUpRepo() {
    }

    /**
     * Getter for sign up response
     *
     * @return Mutable live data of String
     */
    public MutableLiveData<String> getSignUpResponse() {
        return signUpResponse;
    }

    /**
     * Upload sign up data to the server
     *
     * @param map is Hash map of String
     */
    public void uploadData(HashMap<String, String> map) {
        // Get and store hash map data
        email = map.get("email");
        password = map.get("password");
        firstName = map.get("first_name");
        lastName = map.get("last_name");

        // Set the values via model class
        data.setEmail(email);
        data.setUsername(password);
        data.setFirstName(firstName);
        data.setLastName(lastName);

        // Validate the login data
        int errorCode = data.validateData();
        if (errorCode == 0) {
            signUpResponse.setValue(Constants.MISSING);

        } else {
            HashMap<String, String> newMap = new HashMap<>();
            newMap.put("email", email);
            newMap.put("password", password);
            newMap.put("first_name", firstName);
            newMap.put("last_name", lastName);

            // Retrieve Callbacks from Retrofit.
            getApiInterface().createCustomer(Constants.CONSUMER_KEY, Constants.SECRET_KEY, newMap)
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                            Data data = response.body();
                            if (response.isSuccessful() && data != null) {
                                signUpResponse.setValue(Constants.SUCCESS);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                            signUpResponse.setValue(Constants.FAILED);
                        }
                    });
        }
    }
}