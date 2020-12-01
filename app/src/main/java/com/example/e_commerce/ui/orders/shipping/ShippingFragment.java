package com.example.e_commerce.ui.orders.shipping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.data.model.shipping.ShippingMethod;
import com.example.e_commerce.databinding.FragmentShippingBinding;
import com.example.e_commerce.ui.orders.payment.PaymentFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends Fragment implements View.OnClickListener {


    /**
     * Initialization
     */
    private FragmentShippingBinding binding;
    private ShippingViewModel viewModel;
    List<ShippingMethod> shippingMethodList;
    private PaymentFragment paymentFragment;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String city;
    private String country;
    private String phoneNumber;
    private String shippingMethod;
    List<LineItem> lineItems;


    /**
     * Default constructor
     */
    public ShippingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShippingBinding.inflate(inflater, container, false);

        // init views
        initViews();
        // Pass data to view model
        viewModel.passData();
        // Observe the changes from live data
        viewModel.getResponse().observe(getViewLifecycleOwner(), shippingMethods -> {
            shippingMethodList = shippingMethods;
            updateUi();
        });

        // Click listener on radio group
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_flat_rate) {
                // Edit visibility
                binding.description1.setVisibility(View.VISIBLE);
                binding.description2.setVisibility(View.GONE);
                binding.description3.setVisibility(View.GONE);
                shippingMethod = "Flat rate";
            } else if (checkedId == R.id.rb_free_shipping) {
                // Edit visibility
                binding.description2.setVisibility(View.VISIBLE);
                binding.description1.setVisibility(View.GONE);
                binding.description3.setVisibility(View.GONE);
                shippingMethod = "Free shipping";
            } else if (checkedId == R.id.rb_local_pickup) {
                // Edit visibility
                binding.description3.setVisibility(View.VISIBLE);
                binding.description1.setVisibility(View.GONE);
                binding.description2.setVisibility(View.GONE);
                shippingMethod = "Local pickup";
            }
        });

        // Return root view
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_to_payment) {
            // Get user input
            getUserInput();
            // Create a bundle to pass the data
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            bundle.putString("first_name", firstName);
            bundle.putString("last_name", lastName);
            bundle.putString("address", address);
            bundle.putString("post_code", postCode);
            bundle.putString("city", city);
            bundle.putString("country", country);
            bundle.putString("phone_number", phoneNumber);
            bundle.putString("shipping_method", shippingMethod);

            // Go to payment fragment with data
            paymentFragment.setArguments(bundle);
            FragmentTransaction fr = getParentFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container, paymentFragment);
            fr.commit();
        }
    }


    /**
     * Initialize the views
     */
    private void initViews() {
        // Setup for view model
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()))
                .get(ShippingViewModel.class);
        // Register click listener
        binding.btnToPayment.setOnClickListener(this);
        // Reference to list
        shippingMethodList = new ArrayList<>();
        paymentFragment = new PaymentFragment();
        lineItems = new ArrayList<>();
    }


    /**
     * Get user input and pass it to view model
     */
    @SuppressWarnings("ConstantConditions")
    private void getUserInput() {
        // Get user input
        email = binding.etEmail.getEditText().getText().toString().trim();
        firstName = binding.etFirstName.getEditText().getText().toString().trim();
        lastName = binding.etLastName.getEditText().getText().toString().trim();
        address = binding.etAddress.getEditText().getText().toString().trim();
        postCode = binding.etPostcode.getEditText().getText().toString().trim();
        city = binding.etCity.getEditText().getText().toString().trim();
        country = binding.etCountry.getEditText().getText().toString().trim();
        phoneNumber = binding.etPhone.getEditText().getText().toString().trim();
    }


    /**
     * Displays shipping methods
     */
    private void updateUi() {
        // Show visibility
        binding.loadingIndicator.setVisibility(View.INVISIBLE);
        binding.rbFlatRate.setVisibility(View.VISIBLE);
        binding.rbFreeShipping.setVisibility(View.VISIBLE);
        binding.rbLocalPickup.setVisibility(View.VISIBLE);
        // Update radio buttons
        binding.rbFlatRate.setText(shippingMethodList.get(0).getTitle());
        binding.rbFreeShipping.setText(shippingMethodList.get(1).getTitle());
        binding.rbLocalPickup.setText(shippingMethodList.get(2).getTitle());
        // Update descriptions
        binding.description1.setText(shippingMethodList.get(0).getDescription());
        binding.description2.setText(shippingMethodList.get(1).getDescription());
        binding.description3.setText(shippingMethodList.get(2).getDescription());
    }
}