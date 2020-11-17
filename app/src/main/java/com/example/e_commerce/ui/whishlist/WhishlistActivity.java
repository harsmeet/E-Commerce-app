package com.example.e_commerce.ui.whishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.WhishlistAdapter;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.databinding.ActivityWhishlistBinding;
import com.example.e_commerce.ui.cart.CartActivity;
import com.example.e_commerce.utils.Constants;
import com.example.e_commerce.utils.SingletonClass;

import java.util.Objects;

public class WhishlistActivity extends AppCompatActivity implements WhishlistListener, View.OnClickListener {


    /**
     * Initialization
     */
    private static final String MyPREFERENCES = "PREFERENCE";

    private WhishlistAdapter adapter;
    LinearLayoutManager layoutManager;
    AppDatabase appDatabase;
    ActivityWhishlistBinding binding;
    WhishlistViewModel viewModel;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    int savedQty;
    SingletonClass singletonClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityWhishlistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize views
        initViews();
        // Update ui
        updateUi();
        // Observe the changes from live data
        viewModel.getDatumList().observe(WhishlistActivity.this, data -> {
            adapter.setDatumList(data);
            editVisibility();
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_cart_icon) {
            startActivity(new Intent(WhishlistActivity.this, CartActivity.class));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (singletonClass.getCartCounter() >= 1) {
            binding.cardView.setVisibility(View.VISIBLE);
            binding.notificationNum.setText(String.valueOf(singletonClass.getCartCounter()));
        }
        if (singletonClass.getCartCounter() == 0) {
            binding.notificationNum.setText("");
            binding.cardView.setVisibility(View.INVISIBLE);
        }
        editor.putInt(Constants.QTY, singletonClass.getCartCounter());
        editor.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void updateCartCounter() {
        // Show counter for cart icon
        binding.cardView.setVisibility(View.VISIBLE);
        // If cart counter is empty
        if (binding.notificationNum.getText().toString().isEmpty()) {
            binding.notificationNum.setText(String.valueOf(1));
            editor.putInt(Constants.QTY, 1);
            singletonClass.setCartCounter(1);

        } else {
            int qtyInCart = Integer.parseInt(binding.notificationNum.getText().toString());
            binding.notificationNum.setText(String.valueOf(1 + qtyInCart));
            editor.putInt(Constants.QTY, 1 + qtyInCart);
            singletonClass.setCartCounter(1 + qtyInCart);
        }
        editor.commit();
    }


    /**
     * Initialize the views
     */
    @SuppressLint("CommitPrefEdits")
    private void initViews() {
        // Custom action bar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Setup for view model
        viewModel = new ViewModelProvider(WhishlistActivity.this).get(WhishlistViewModel.class);
        // Get an instance of database
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        // Setup for shared preferences
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Initialize singleton
        singletonClass = SingletonClass.getInstance();
        binding.ivCartIcon.setOnClickListener(this);
    }


    /**
     * update ui via recyclerview
     */
    private void updateUi() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new WhishlistAdapter(WhishlistActivity.this, this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        // Retrieve data saved in shared preference
        savedQty = preferences.getInt(Constants.QTY, 0);
        if (savedQty >= 1) {
            binding.cardView.setVisibility(View.VISIBLE);
            binding.notificationNum.setText(String.valueOf(savedQty));
        }
    }


    /**
     * Hide or show visibility for views
     */
    private void editVisibility() {
        if (adapter.getItemCount() == 0) {
            binding.tvNoItems.setVisibility(View.VISIBLE);
            binding.ivWhishlist.setVisibility(View.VISIBLE);
        }
    }
}