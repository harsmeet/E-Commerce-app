package com.example.e_commerce.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.products.Datum;

import java.util.HashMap;
import java.util.List;

public class DetailsViewModel extends AndroidViewModel {


    /**
     * Initialization
     */
    DetailsRepo repo;


    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repo = new DetailsRepo(application);
    }


    /**
     * Get callback response
     *
     * @return mutable live data of Datum list
     */
    public MutableLiveData<List<Datum>> getDatumList() {
        return repo.getListDatumResponse();
    }


    /**
     * Init get request of categories by id
     *
     * @param categoryId is a category id
     */
    public void getAllProducts(String categoryId) {
        repo.getAllProducts(categoryId);
    }


    /**
     * Pass data to repo to add data in cart
     *
     * @param map is hash map of cart data
     */
    public void getCartData(HashMap<String, String> map) {
        repo.addToCart(map);
    }
}