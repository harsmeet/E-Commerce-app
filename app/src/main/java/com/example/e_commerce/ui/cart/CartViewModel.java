package com.example.e_commerce.ui.cart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.model.products.Cart;

import java.util.List;

public class CartViewModel extends AndroidViewModel {



    /**
     * Wrapping the <list<Cart> with LiveData
     * to avoid requiring the data every time
     **/
    LiveData<List<Cart>> cartList;


    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public CartViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        cartList = dataBase.roomDao().loadAllCart();
    }



    /**
     * Getter for cart list
     *
     * @return cart list
     */
    public LiveData<List<Cart>> getCartList() {
        return cartList;
    }
}
