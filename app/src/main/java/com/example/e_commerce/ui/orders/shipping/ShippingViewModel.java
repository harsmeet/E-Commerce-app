package com.example.e_commerce.ui.orders.shipping;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.shipping.ShippingMethod;

import java.util.List;

public class ShippingViewModel extends AndroidViewModel {

    /**
     * Initialization
     */
    ShippingRepo repo;

    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public ShippingViewModel(@NonNull Application application) {
        super(application);
        repo = new ShippingRepo();
    }


    public MutableLiveData<List<ShippingMethod>> getResponse() {
        return repo.getShippingMethodsResponse();
    }


    /**
     * Pass data to repo
     *
     */
    public void passData( ) {
        repo.getShippingMethods();
    }
}
