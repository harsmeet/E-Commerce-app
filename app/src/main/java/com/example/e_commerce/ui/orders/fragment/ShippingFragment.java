package com.example.e_commerce.ui.orders.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.databinding.FragmentShippingBinding;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ShippingFragment extends Fragment {

    /**
     * Initialization
     */
    FragmentShippingBinding binding;

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

        getUserInput();
        return binding.getRoot();
    }


    private void getUserInput() {
        // Get user input
        String email = binding.etEmail.getEditText().getText().toString().trim();
        String firstName = binding.etFirstName.getEditText().getText().toString().trim();
        String lastName = binding.etLastName.getEditText().getText().toString().trim();
        String address = binding.etAddress.getEditText().getText().toString().trim();
        String postCode = binding.etPostcode.getEditText().getText().toString().trim();
        String city = binding.etCity.getEditText().getText().toString().trim();
        String country = binding.etCountry.getEditText().getText().toString().trim();
        String phoneNumber = binding.etPhone.getEditText().getText().toString().trim();
    }
}