package com.example.e_commerce.ui.home;

import android.content.Context;
import android.widget.Toast;

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
     * Initialization
     */
    private Context context;
    private List<Datum> datumList;
    private MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();


    /**
     * Default constructor
     */
    public HomeRepo(Context context) {
        this.context = context;
    }


    /**
     * Get datum list from on response
     *
     * @return Datum list of mutable live data
     */
    public MutableLiveData<List<Datum>> getListDatumResponse() {
        return listDatumResponse;
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
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Displays max price in products via callback
     */
    public void getCategory(String id) {
        APIClient.getINSTANCE().getApi().getCategory(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                id, 30).enqueue(new Callback<List<Datum>>() {
            @Override
            public void onResponse(Call<List<Datum>> call, Response<List<Datum>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    datumList = response.body();
                    listDatumResponse.setValue(datumList);
                }
            }

            @Override
            public void onFailure(Call<List<Datum>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}