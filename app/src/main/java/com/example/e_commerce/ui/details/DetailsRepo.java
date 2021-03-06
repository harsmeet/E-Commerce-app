package com.example.e_commerce.ui.details;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsRepo extends GlobalRepo {


    /**
     * Initialization
     */
    private final Context context;
    private final MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AppDatabase mDb;

    private int id;
    private int qty;
    private String title;
    private int price;
    private String image;
    private String category;

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
     * Displays data by retrofit via RxJava
     *
     * @param categoryId is a category id
     */
    public void getAllProducts(String categoryId) {
        @NonNull Single<List<Datum>> observable = APIClient.getINSTANCE()
                .getApi()
                .getCategory(Constants.CONSUMER_KEY, Constants.SECRET_KEY,
                        categoryId, 30, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        // Wrap observable with composite disposable
        compositeDisposable.add(observable.subscribe(listDatumResponse::setValue, this::toast));
    }


    /**
     * Add data to cart via room database
     *
     * @param map is hash map of cart data
     */
    public void addToCart(HashMap<String, String> map) {
        // Get map values and set it to variables
        id = Integer.parseInt(Objects.requireNonNull(map.get("id")));
        qty = Integer.parseInt(Objects.requireNonNull(map.get("qty")));
        title = map.get("title");
        price = Integer.parseInt(map.get("price"));
        image = map.get("image");
        category = map.get("category");

        // Get instance of database
        mDb = AppDatabase.getInstance(context.getApplicationContext());
        // Do operations in database in background thread
        AppExecutors.getInstance().diskIO().execute(() -> {
            LineItem LineItem = mDb.roomDao().fetchInCart(title);
            if (LineItem != null) {
                LineItem.setQuantity(qty);
                LineItem.setProduct_id(id);
                int qty = mDb.roomDao().getSum(LineItem.getQuantity(), LineItem.getProduct_id());
                LineItem.setQuantity(qty);
                mDb.roomDao().updateToCart(LineItem);

            } else {
                LineItem = new LineItem();
                LineItem.setProduct_id(id);
                LineItem.setQuantity(qty);
                LineItem.setName(title);
                LineItem.setPrice(price);
                LineItem.setImage(image);
                LineItem.setCategory(category);
                // Insert the selected item to the database
                mDb.roomDao().insertToCart(LineItem);
            }
        });
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