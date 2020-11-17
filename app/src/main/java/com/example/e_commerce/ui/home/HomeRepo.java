package com.example.e_commerce.ui.home;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utils.Constants;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeRepo extends GlobalRepo {


    /**
     * Initialization
     */
    final Context context;
    private final MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
     * Displays all categories by retrofit via RxJava
     */
    public void getAllProducts(String maxPrice) {
        Single<List<Datum>> observable = APIClient.getINSTANCE()
                .getApi()
                .getProducts(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                        31, maxPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        // Wrap observable with composite disposable
        compositeDisposable.add(observable.subscribe(listDatumResponse::setValue, this::toast));
    }


    /**
     * Displays a specific category by retrofit via RxJava
     */
    public void getCategory(String id, String maxPrice) {
        @NonNull Single<List<Datum>> observable = APIClient.getINSTANCE()
                .getApi()
                .getCategory(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                        id, 30, maxPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        // Wrap observable with composite disposable
        compositeDisposable.add(observable.subscribe(listDatumResponse::setValue, this::toast));
    }

    /**
     * Toast message in case of failure response
     *
     * @param errorMess the error message from observer
     */
    private void toast(Throwable errorMess) {
        Toast.makeText(context, errorMess.getMessage(), Toast.LENGTH_SHORT).show();
    }
}