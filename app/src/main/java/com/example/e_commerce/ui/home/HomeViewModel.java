package com.example.e_commerce.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.products.Datum;

import java.util.List;

import static android.content.ContentValues.TAG;


public class HomeViewModel extends AndroidViewModel {


    /**
     * Initialization
     */
    HomeRepo repo;

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
     * Get callback response
     *
     * @return mutable live data of Datum list
     */
    public MutableLiveData<List<Datum>> getDatumList() {
        return repo.getListDatumResponse();
    }

    /**
     * Init get request of products
     */
    public void getAllProducts(String maxPrice) {
        repo.getAllProducts(maxPrice);
    }

    /**
     * Init get request by category id
     */
    public void getCategory(String id, String maxPrice) {
        repo.getCategory(id, maxPrice);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.compositeDisposable.clear();
    }
}
