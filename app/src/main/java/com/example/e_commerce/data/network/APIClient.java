package com.example.e_commerce.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "https://re-shop.arabprospect.com/wp-json/wc/v3/";
    private static APIClient INSTANCE;
    private Retrofit retrofit;

    public APIClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized APIClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new APIClient();
        }
        return INSTANCE;
    }

    public APIInterface getApi() {
        return retrofit.create(APIInterface.class);
    }
}
