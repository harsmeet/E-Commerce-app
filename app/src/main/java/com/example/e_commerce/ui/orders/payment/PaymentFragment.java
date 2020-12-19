package com.example.e_commerce.ui.orders.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.data.model.order.Billing;
import com.example.e_commerce.data.model.order.Shipping;
import com.example.e_commerce.databinding.FragmentPaymentBinding;
import com.example.e_commerce.ui.orders.review.ReviewFragment;
import com.example.e_commerce.utils.SingletonClass;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements View.OnClickListener {


    /**
     * Initialization
     */
    FragmentPaymentBinding binding;
    private PaymentViewModel viewModel;
    ReviewFragment reviewFragment;
    FragmentTransaction fr;
    private String paymentMethod = "";
    Bundle bundle;
    String email;
    String firstName;
    String lastName;
    String address;
    String postCode;
    String city;
    String country;
    String phoneNumber;
    Billing billing;
    Shipping shipping;
    SingletonClass singletonClass;
    int cashState = 1;


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
        updateUi();
        // Observe the changes from live data
        viewModel.getResponse().observe(getViewLifecycleOwner(), orderDatum -> {
            // Hide loading icon
            binding.loadingIndicator.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Order updated successfully",
                    Toast.LENGTH_SHORT).show();

            // Go to review fragment
            bundle.putParcelable("order_data", orderDatum);
            reviewFragment.setArguments(bundle);
            fr = getParentFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, reviewFragment);
            fr.commit();
        });

        return binding.getRoot();
    }


    /**
     * Update ui to user
     */
    private void updateUi() {
        binding.btnCash.setOnClickListener(this);
        binding.btnToReview.setOnClickListener(this);
        singletonClass = SingletonClass.getInstance();
        reviewFragment = new ReviewFragment();
        // Displays cart details
        binding.tvCartDetails.setText(String.valueOf(singletonClass.getBillTotal()));
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
        }

        // Pass data to constructors
        billing = new Billing(firstName, lastName, address, city, postCode, country, email,
                phoneNumber);
        shipping = new Shipping(firstName, lastName, address, city, postCode, country);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // Cash on delivery button
        if (id == R.id.btn_cash) {
            if (cashState == 2) {
                binding.tvConfirmCash.setVisibility(View.GONE);
                binding.tvCashActivated.setVisibility(View.GONE);
                binding.btnToReview.setBackgroundResource(R.drawable.button_rounded);
                paymentMethod = getString(R.string.stripe);
                cashState = 1;
            } else {
                binding.tvConfirmCash.setVisibility(View.VISIBLE);
                binding.tvCashActivated.setVisibility(View.VISIBLE);
                binding.btnToReview.setBackgroundResource(R.drawable.button_rounded_full);
                paymentMethod = getString(R.string.cash_activated);
                cashState++;
            }
            // Review button
        } else {
            binding.loadingIndicator.setVisibility(View.VISIBLE);
            if (paymentMethod.equals("Cash on delivery")) {
                paymentMethod = getString(R.string.cash_activated);
            } else {
                paymentMethod = getString(R.string.stripe);
            }
            // Pass data to view model.
            // Credit card test mode only.
            viewModel.passData(billing, shipping, paymentMethod,
                    singletonClass.getLineItems(), "4242424242424242");
        }
    }
}