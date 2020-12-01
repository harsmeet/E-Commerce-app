package com.example.e_commerce.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivitySignUpBinding;
import com.example.e_commerce.ui.home.HomeActivity;
import com.example.e_commerce.utils.Constants;

import java.util.HashMap;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Initialization
     */
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialization
        initViews();

        // Observe the changes from live data
        viewModel.getMessage().observe(this, s -> {
            message = s;
            updateUI();
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register) {
            binding.loadingIndicator.setVisibility(View.VISIBLE);
            getUserInput();
        }
    }


    /**
     * Initialize the views
     */
    private void initViews() {
        // Setup for view model
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        // Register click listener
        binding.btnRegister.setOnClickListener(this);
    }


    /**
     * Update ui on screen
     */
    private void updateUI() {
        // Switch on coming message from live data
        switch (message) {
            case Constants.SUCCESS:
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                break;

            case Constants.FAILED:
            case Constants.MISSING:
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                break;
        }
        // Hide visibility for loading indicator
        binding.loadingIndicator.setVisibility(View.INVISIBLE);
    }


    /**
     * Extract data from views to pass it to ViewModel
     */
    private void getUserInput() {
        // Show progress bar indicator
        binding.loadingIndicator.setVisibility(View.VISIBLE);
        // Extract the values from all views
        String email = Objects.requireNonNull(binding.etEmail.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(binding.etUsername.getEditText()).getText().toString().trim();
        String firstName = Objects.requireNonNull(binding.etFirstName.getEditText()).getText().toString().trim();
        String lastName = Objects.requireNonNull(binding.etLastName.getEditText()).getText().toString().trim();

        // Create hashmap object of String
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("first_name", firstName);
        map.put("last_name", lastName);

        // Pass hashmap object to view model
        viewModel.getData(map);
    }
}