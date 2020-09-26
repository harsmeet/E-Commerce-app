package com.example.e_commerce.data.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce.data.model.products.Datum;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    /**
     * Wrapping the <list<Datum> with LiveData
     * to avoid requiring the data every time
     **/
    LiveData<List<Datum>> datumList;


    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        datumList = dataBase.favouriteDao().loadAllResults();
    }


    /**
     * Getter for datum list
     *
     * @return datum list
     */
    public LiveData<List<Datum>> getDatumList() {
        return datumList;
    }
}