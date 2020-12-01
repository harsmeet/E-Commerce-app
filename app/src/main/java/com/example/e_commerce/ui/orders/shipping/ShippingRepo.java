package com.example.e_commerce.ui.orders.shipping;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.data.model.shipping.ShippingMethod;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utils.Constants;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ShippingRepo extends GlobalRepo {

    private final MutableLiveData<List<ShippingMethod>> shippingMethodsResponse = new MutableLiveData<>();
    List<ShippingMethod> shippingMethodList = new ArrayList<>();


    public MutableLiveData<List<ShippingMethod>> getShippingMethodsResponse() {
        return shippingMethodsResponse;
    }

    /**
     * Get shipping methods
     */
    public void getShippingMethods() {
        Call<List<ShippingMethod>> call = APIClient.getINSTANCE().getApi()
                .getShippingMethods(Constants.CONSUMER_KEY, Constants.SECRET_KEY);
        call.enqueue(new Callback<List<ShippingMethod>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShippingMethod>> call, @NonNull Response<List<ShippingMethod>> response) {
                shippingMethodList = response.body();
                if (response.isSuccessful() && shippingMethodList != null) {
                    shippingMethodsResponse.setValue(shippingMethodList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ShippingMethod>> call, @NonNull Throwable t) {
            }
        });
    }
}
