package com.example.e_commerce.ui.details;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.Cart;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utlis.Constants;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsRepo extends GlobalRepo {


    /**
     * Initialization
     */
    Context context;
    private List<Datum> datumList;
    MutableLiveData<List<Datum>> listDatumResponse = new MutableLiveData<>();
    AppDatabase mDb;

    int id;
    int qty;
    String title;
    String price;
    String image;
    String category;


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


    /**
     * Add data to cart via room database
     *
     * @param map is hash map of cart data
     */
    public void addToCart(HashMap<String, String> map) {
        id = Integer.parseInt(map.get("id"));
        qty = Integer.parseInt(map.get("qty"));
        title = map.get("title");
        price = map.get("price");
        image = map.get("image");
        category = map.get("category");

        mDb = AppDatabase.getInstance(context.getApplicationContext());


        AppExecutors.getInstance().diskIO().execute(() -> {
            Cart cart = mDb.roomDao().fetchInCart(title);
            if (cart != null) {
                cart.setQuantity(qty);
                cart.setId(id);
                int qty = mDb.roomDao().getSum(cart.getQuantity(), cart.getId());
                cart.setQuantity(qty);
                mDb.roomDao().updateToCart(cart);

            } else {
                cart = new Cart();
                cart.setId(id);
                cart.setQuantity(qty);
                cart.setTitle(title);
                cart.setPrice(price);
                cart.setImage(image);
                cart.setCategory(category);
                // Insert the selected item to the database
                mDb.roomDao().insertToCart(cart);
            }
        });
    }
}