package com.example.e_commerce.ui.auth;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.register.Data;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utlis.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepo extends GlobalRepo {

    // Initialization
    Data data = new Data();
    private MutableLiveData<String> signUpResponse = new MutableLiveData<>();

    // Variables to store data
    String email;
    String userName;
    String firstName;
    String lastName;

    /**
     * Default constructor
     */
    public SignUpRepo() {
    }


    /**
     * Get sign up response
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
        userName = map.get("userName");
        firstName = map.get("firstName");
        lastName = map.get("lastName");

        // Set the values via model class
        data.setEmail(email);
        data.setUsername(userName);
        data.setFirstName(firstName);
        data.setLastName(lastName);

        // Validate the login data
        int errorCode = data.validateData();
        if (errorCode == 0) {
            signUpResponse.setValue("Enter missing fields");

        } else {
            // Create a hash map object to pass it to interface class
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("consumer_key", Constants.CONSUMER_KEY);
            hashMap.put("consumer_secret", Constants.CONSUMER_KEY);
            hashMap.put("email", email);
            hashMap.put("userName", userName);
            hashMap.put("firstName", firstName);
            hashMap.put("lastName", lastName);

            // Retrieve Callbacks from Retrofit.
            getApiInterface().createCustomer(hashMap).enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    Data data = response.body();
                    assert data != null;
                    signUpResponse.setValue("Registered Successfully");
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    signUpResponse.setValue("Registered Failed");
                }
            });
        }
    }
}



