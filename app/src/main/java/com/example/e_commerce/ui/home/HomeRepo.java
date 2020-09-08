package com.example.e_commerce.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utlis.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepo extends GlobalRepo {


    /**
     * Default constructor
     */
    public HomeRepo() {
    }


    // Initialization
    List<Datum> datumList;
    MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();
    MutableLiveData<String> failureResponse = new MutableLiveData<>();


    /**
     * Get datum list from retrofit callback
     *
     * @return Datum list of mutable live data
     */
    public MutableLiveData<List<Datum>> getListDatumResponse() {
        return listDatumResponse;
    }


    public MutableLiveData<String> getFailureResponse() {
        return failureResponse;
    }


    /**
     * Displays data via callback
     */
    public void getAllProducts() {
        APIClient.getINSTANCE().getApi().getProducts(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                31).enqueue(new Callback<List<Datum>>() {
            @Override
            public void onResponse(Call<List<Datum>> call, Response<List<Datum>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    datumList = response.body();
                    listDatumResponse.setValue(datumList);
                }
            }

            @Override
            public void onFailure(Call<List<Datum>> call, Throwable t) {
                failureResponse.setValue(t.toString());
            }
        });
    }


    /**
     * Displays max price in products via callback
     */
    public void getMaxPrice() {
        APIClient.getINSTANCE().getApi().getMaxPrice(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                 150).enqueue(new Callback<List<Datum>>() {
            @Override
            public void onResponse(Call<List<Datum>> call, Response<List<Datum>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    datumList = response.body();
                    listDatumResponse.setValue(datumList);
                }
            }

            @Override
            public void onFailure(Call<List<Datum>> call, Throwable t) {
                failureResponse.setValue(t.toString());
            }
        });
    }
}
