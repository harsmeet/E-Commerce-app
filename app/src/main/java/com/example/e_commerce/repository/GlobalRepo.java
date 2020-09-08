package com.example.e_commerce.repository;

import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.data.network.APIInterface;

public class GlobalRepo {

    private APIInterface apiInterface = APIClient.getINSTANCE().getApi();

    public APIInterface getApiInterface() {
        return apiInterface;
    }
}
