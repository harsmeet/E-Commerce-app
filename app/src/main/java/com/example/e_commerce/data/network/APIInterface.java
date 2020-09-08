package com.example.e_commerce.data.network;


import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.model.register.Data;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {


//    on_sale
//    min_price	string
//    max_price	string


    @POST("customers")
    Call<Data> createCustomer(@Body HashMap<String, String> map);

    @GET("products")
    Call<List<Datum>> getProducts(@Query("consumer_key") String key,
                                  @Query("consumer_secret") String secret,
                                  @Query("per_page") int page);

    @GET("products")
    Call<List<Datum>> getMaxPrice(@Query("consumer_key") String key,
                                  @Query("consumer_secret") String secret,
                                  @Query("max_price") int maxPrice);
}
