package com.example.e_commerce.ui.orders.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.data.model.orderDetails.Billing;
import com.example.e_commerce.data.model.orderDetails.OrderDatum;
import com.example.e_commerce.data.model.orderDetails.Shipping;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.databinding.FragmentPaymentBinding;
import com.example.e_commerce.utils.SingletonClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {


    /**
     * Initialization
     */
    FragmentPaymentBinding binding;
    private PaymentViewModel viewModel;
    Bundle bundle;
    String email;
    String firstName;
    String lastName;
    String address;
    String postCode;
    String city;
    String country;
    String phoneNumber;
    String shippingMethod;
    Billing billing;
    Shipping shipping;
    SingletonClass singletonClass;


    /**
     * Default constructor
     */
    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        // Setup for view model
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()))
                .get(PaymentViewModel.class);
        // Update ui
        updateUi();
        // Observe the changes from live data

        viewModel.getResponse().observe(getViewLifecycleOwner(), orderDatum -> {
        });


        // Click listener on payment button
        binding.btnToPayment.setOnClickListener(v ->
                viewModel.passData(billing, shipping, "Cash on delivery",
                        singletonClass.getLineItems()));

        // Return root view
        return binding.getRoot();
    }


    /**
     * Update ui to user
     */
    private void updateUi() {
        singletonClass = SingletonClass.getInstance();
        // Get bundle data
        bundle = this.getArguments();
        if (bundle != null) {
            email = bundle.getString("email");
            firstName = bundle.getString("first_name");
            lastName = bundle.getString("last_name");
            address = bundle.getString("address");
            postCode = bundle.getString("post_code");
            city = bundle.getString("city");
            country = bundle.getString("country");
            phoneNumber = bundle.getString("phone_number");
            shippingMethod = bundle.getString("shipping_method");
        }

//        Billing billing = new Billing(firstName, lastName, address, city, postCode, country, email,
//                phoneNumber);
//        Shipping shipping = new Shipping(firstName,lastName,address,city,postCode,country);

        billing = new Billing("Said", "Ali", "zone19",
                "Cairo", "12345", "Egypt", "mhamod66@gmail.com",
                "01115444987");
        shipping = new Shipping("Said", "Ali", "zone19",
                "Cairo", "12345", "Egypt");
    }
}