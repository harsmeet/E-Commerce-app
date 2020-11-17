package com.example.e_commerce.ui.whishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.model.products.Datum;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class WhishlistViewModel extends AndroidViewModel {

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
    public WhishlistViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        datumList = dataBase.roomDao().loadAllResults();
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