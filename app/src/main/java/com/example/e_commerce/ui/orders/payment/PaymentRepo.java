package com.example.e_commerce.ui.orders.payment;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.orderDetails.Billing;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;
import com.example.e_commerce.data.model.orderDetails.Shipping;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.data.network.APIClient;
import com.example.e_commerce.repository.GlobalRepo;
import com.example.e_commerce.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepo extends GlobalRepo {


    /**
     * Initialization
     */
    private static final String TAG = PaymentRepo.class.getSimpleName();
    Context context;
    private final MutableLiveData<OrderDatum> responseDatum = new MutableLiveData<>();
    //    List<LineItem> lineItems;
    OrderDatum orderData;

    /**
     * Our default constructor
     *
     * @param context is a context
     */
    public PaymentRepo(Context context) {
        this.context = context;
    }


    /**
     * Getter of response message
     *
     * @return a message
     */
    public MutableLiveData<OrderDatum> getResponseDatum() {
        return responseDatum;
    }

    /**
     * Create a new order
     */
    public void createOrder(Billing billing, Shipping shipping, String paymentMethod, List<LineItem> lineItems) {
        // Fill data to create an order
        orderData = new OrderDatum(billing, shipping, paymentMethod, lineItems);
        // Call back from retrofit
        Call<OrderDatum> call = APIClient.getINSTANCE().getApi().createOrder(Constants.CONSUMER_KEY,
                Constants.SECRET_KEY, orderData);
        call.enqueue(new Callback<OrderDatum>() {
            @Override
            public void onResponse(@NonNull Call<OrderDatum> call, @NonNull Response<OrderDatum> response) {
                OrderDatum data = response.body();
                if (response.isSuccessful() && data != null) {
                    responseDatum.setValue(data);

                } else {
                    Toast.makeText(context, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderDatum> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
