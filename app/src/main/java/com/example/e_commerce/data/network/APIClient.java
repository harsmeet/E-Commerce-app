package com.example.e_commerce.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    /**
     * Initialization
     */
    private static final String BASE_URL = "https://re-shop.arabprospect.com/wp-json/wc/v3/";
    private static APIClient INSTANCE;
    private final Retrofit retrofit;


    /**
     * Constructor for our class
     */
    public APIClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Build base url
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }


    /**
     * Synchronized method for api client
     *
     * @return api client
     */
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