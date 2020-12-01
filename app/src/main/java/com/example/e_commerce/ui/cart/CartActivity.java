package com.example.e_commerce.ui.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CartAdapter;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.databinding.ActivityCartBinding;
import com.example.e_commerce.ui.orders.OrdersActivity;
import com.example.e_commerce.utils.SingletonClass;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity implements CartListener, View.OnClickListener {


    /**
     * Initialization
     */

    private CartAdapter adapter;
    LinearLayoutManager layoutManager;
    AppDatabase appDatabase;
    ActivityCartBinding binding;
    CartViewModel viewModel;
    SingletonClass singletonClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize views
        initViews();
        // Update ui
        updateUi();
        // Observe the changes from live data
        viewModel.getCartList().observe(CartActivity.this, data -> {
            adapter.setLineItemList(data);
            editVisibility();
            singletonClass.setLineItems(data);
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            super.onBackPressed();
        } else if (id == R.id.btn_to_checkout) {
            // Send cart list to shipping fragment
            Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void setValue(String value) {
        binding.tvBillTotal.setText(value);
        binding.tvSubTotal.setText(value);
    }


    @Override
    public void calcCounter(int counter, String operation) {
        if (operation.equals("add")) {
            singletonClass.setCartCounter(counter + singletonClass.getCartCounter());
        } else {
            singletonClass.setCartCounter(singletonClass.getCartCounter() - counter);
        }
    }


    /**
     * Initialize the views
     */
    @SuppressLint("CommitPrefEdits")
    private void initViews() {
        // Setup for view model
        viewModel = new ViewModelProvider(CartActivity.this).get(CartViewModel.class);
        // Get an instance of database
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        // Reference to singleton class
        singletonClass = SingletonClass.getInstance();
        // Register click listener
        binding.btnBack.setOnClickListener(this);
        binding.btnToCheckout.setOnClickListener(this);
    }


    /**
     * update ui via recyclerview
     */
    private void updateUi() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new CartAdapter(CartActivity.this, this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }


    /**
     * Hide or show visibility for views
     */
    private void editVisibility() {
        if (adapter.getItemCount() == 0) {
            binding.frameLayout.setVisibility(View.GONE);
            binding.btnToCheckout.setVisibility(View.GONE);
            binding.btnBack.setVisibility(View.GONE);
            binding.tvNoItems.setVisibility(View.VISIBLE);
            binding.ivCart.setVisibility(View.VISIBLE);
            binding.tvAddItems.setVisibility(View.VISIBLE);
        } else {
            binding.frameLayout.setVisibility(View.VISIBLE);
            binding.btnToCheckout.setVisibility(View.VISIBLE);
            binding.btnBack.setVisibility(View.VISIBLE);
        }
    }
}