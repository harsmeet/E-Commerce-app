package com.example.e_commerce.ui.orders.finish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityFinishBinding;
import com.example.e_commerce.ui.home.HomeActivity;


public class FinishActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFinishBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityFinishBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        intent = getIntent();
        updateUi();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_continue) {
            startActivity(new Intent(this, HomeActivity.class));
        }

    }

    private void updateUi() {
        int orderNumber = intent.getIntExtra("order_number", 0);
        String email = intent.getStringExtra("email");
        binding.tvOrderNumber.setText(String.valueOf(orderNumber));
        binding.tvEmail.setText(email);
        binding.btnContinue.setOnClickListener(this);
    }
}