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

import java.util.HashMap;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Initialization
    private ActivitySignUpBinding binding;
    SignUpViewModel viewModel;
    private static final String SUCCESS = "Registered Successfully";
    private static final String FAILED = "Registered Failed";
    private static final String MISSING = "Enter missing fields";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialization
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Observe the changes on coming data
        viewModel.getMessage().observe(this, s -> {
            // Switch on coming message
            switch (s) {
                case SUCCESS:
                    Toast.makeText(SignUpActivity.this, s, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    break;

                case FAILED:
                case MISSING:
                    Toast.makeText(SignUpActivity.this, s, Toast.LENGTH_SHORT).show();
                    break;
            }
            binding.loadingIndicator.setVisibility(View.INVISIBLE);
        });

        // Register click listener on Register Button
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                binding.loadingIndicator.setVisibility(View.VISIBLE);
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
//                extractData();
                break;
        }
    }


    /**
     * Extract data from views to pass it to ViewModel
     */
    private void extractData() {
        // Show progress bar indicator
        binding.loadingIndicator.setVisibility(View.VISIBLE);

        // Extract the values from all views
        String email = Objects.requireNonNull(binding.etEmail.getEditText()).getText().toString().trim();
        String userName = Objects.requireNonNull(binding.etUsername.getEditText()).getText().toString().trim();
        String firstName = Objects.requireNonNull(binding.etFirstName.getEditText()).getText().toString().trim();
        String lastName = Objects.requireNonNull(binding.etLastName.getEditText()).getText().toString().trim();

        // Create hashmap object of String
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("userName", userName);
        map.put("firstName", firstName);
        map.put("lastName", lastName);

        // Pass hashmap object to the method
        viewModel.getData(map);
    }
}