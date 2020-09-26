package com.example.e_commerce.ui.details;

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

public class DetailsRepo extends GlobalRepo {


    /**
     * Initialization
     */
    private Context context;
    private List<Datum> datumList;
    private MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();


    /**
     * Default constructor
     */
    public DetailsRepo(Context context) {
        this.context = context;
    }


    /**
     * Getter of datum list
     *
     * @return Datum list of mutable live data
     */
    public MutableLiveData<List<Datum>> getListDatumResponse() {
        return listDatumResponse;
    }


    /**
     * Displays data via callback
     *
     * @param categoryId is a category id
     */
    public void getAllProducts(String categoryId) {
        APIClient.getINSTANCE().getApi().getCategory(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                categoryId, 30).enqueue(new Callback<List<Datum>>() {
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