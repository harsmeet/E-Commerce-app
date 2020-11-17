package com.example.e_commerce.data.network;


import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.model.register.Data;


import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {


//    @POST("customers")
//    Call<Data> createCustomer(@Body HashMap<String, String> map);


    @POST("customers")
    Call<Data> createCustomer(@Query("consumer_key") String key,
                              @Query("consumer_secret") String secret,
                              @Query("password") String password,
                              @Query("email") String email,
                              @Query("first_name") String firstName,
                              @Query("last_name") String lastName);


    @GET("products")
    Single<List<Datum>> getProducts(@Query("consumer_key") String key,
                                    @Query("consumer_secret") String secret,
                                    @Query("per_page") int page,
                                    @Query("max_price") String maxPrice);


    @GET("products")
    Single<List<Datum>> getCategory(@Query("consumer_key") String key,
                                    @Query("consumer_secret") String secret,
                                    @Query("category") String category,
                                    @Query("per_page") int page,
                                    @Query("max_price") String maxPrice);
}