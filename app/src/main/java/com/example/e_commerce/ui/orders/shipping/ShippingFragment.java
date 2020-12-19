package com.example.e_commerce.ui.orders.shipping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.FragmentShippingBinding;
import com.example.e_commerce.ui.orders.payment.PaymentFragment;
import com.example.e_commerce.utils.SingletonClass;



/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingFragment extends Fragment implements View.OnClickListener {


    /**
     * Initialization
     */
    private FragmentShippingBinding binding;
    private PaymentFragment paymentFragment;
    SingletonClass singletonClass;
    FragmentTransaction fr;
    String email;
    String firstName;
    String lastName;
    String address;
    String postCode;
    String city;
    String country;
    String phoneNumber;


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

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_to_payment) {
            // Get user input
            getUserInput();
        }
    }

    /**
     * Initialize the views
     */
    private void initViews() {
        // Register click listener
        binding.btnToPayment.setOnClickListener(this);
        paymentFragment = new PaymentFragment();
        singletonClass = SingletonClass.getInstance();
        binding.tvCartDetails.setText(String.valueOf(singletonClass.getBillTotal()));
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

        // Go to payment fragment with data
        paymentFragment.setArguments(bundle);
        fr = getParentFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, paymentFragment);
        fr.commit();
    }
}