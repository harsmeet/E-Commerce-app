package com.example.e_commerce.ui.orders.payment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.order.Billing;
import com.example.e_commerce.data.model.order.OrderDatum;
import com.example.e_commerce.data.model.order.Shipping;
import com.example.e_commerce.data.model.products.LineItem;

import java.util.List;

public class PaymentViewModel extends AndroidViewModel {


    /**
     * Initialization
     */
    PaymentRepo repo;


    /**
     * Constructor for our class
     *
     * @param application is an application context
     */
    public PaymentViewModel(@NonNull Application application) {
        super(application);
        repo = new PaymentRepo(application);
    }


    /**
     * Getter of response message from repo
     *
     * @return a response
     */
    public MutableLiveData<OrderDatum> getResponse() {
        return repo.getResponseDatum();
    }

    /**
     *
     */
    public void passData(Billing billing, Shipping shipping, String paymentMethod,
                         List<LineItem> lineItems,String creditCard) {
        repo.createOrder(billing, shipping, paymentMethod, lineItems,creditCard);
    }
}
