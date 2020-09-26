package com.example.e_commerce.repository;

import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.data.network.APIInterface;

public class GlobalRepo {


    /**
     * Initialization
     */
    private APIInterface apiInterface = APIClient.getINSTANCE().getApi();


    /**
     * Getter for api interface
     *
     * @return The interface
     */
    public APIInterface getApiInterface() {
        return apiInterface;
    }
}