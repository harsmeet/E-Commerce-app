package com.example.e_commerce.ui.favourite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.FavouriteAdapter;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.FavouriteViewModel;
import com.example.e_commerce.databinding.ActivityFavouriteBinding;

import java.util.Objects;

public class FavouriteActivity extends AppCompatActivity {


    /**
     * Initialization
     */
    private FavouriteAdapter adapter;
    LinearLayoutManager layoutManager;
    AppDatabase appDatabase;
    ActivityFavouriteBinding binding;
    FavouriteViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize views
        initViews();
        // Update ui
        setupRecyclerView();

        // Observe the changes from live data
        viewModel.getDatumList().observe(FavouriteActivity.this, data ->
                adapter.setDatumList(data));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.cart) {
            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Initialize the views
     */
    private void initViews() {
        // Custom action bar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Setup for view model
        viewModel = new ViewModelProvider(FavouriteActivity.this).get(FavouriteViewModel.class);
        // Get an instance of database
        appDatabase = AppDatabase.getInstance(getApplicationContext());
    }


    /**
     * update ui via recyclerview
     */
    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new FavouriteAdapter(FavouriteActivity.this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }
}