package com.example.e_commerce.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.products.Datum;

import java.util.List;


public class HomeViewModel extends AndroidViewModel {

    // Object from Repo class
    private HomeRepo repo;


    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
        repo = new HomeRepo(application);
    }


    /**
     * Get callback response from repo
     *
     * @return mutable live data of Datum list
     */
    public MutableLiveData<List<Datum>> getDatumList() {
        return repo.getListDatumResponse();
    }


    /**
     * Init retrofit request of products
     */
    public void getAllProducts() {
        repo.getAllProducts();
    }


    /**
     * Init retrofit request of max price
     */
    public void getMaxPrice() {
        repo.getMaxPrice();
    }
}
