package com.example.e_commerce.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.ProductAdapter;
import com.example.e_commerce.adapter.ProductAdapterList;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.ActivityHomeBinding;
import com.example.e_commerce.ui.filter.FilterActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = HomeActivity.class.getSimpleName();
    ActivityHomeBinding binding;
    HomeViewModel viewModel;
    ProductAdapter adapter;
    ProductAdapterList adapterList;
    GridLayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    List<Datum> datumList;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Custom action bar
        setSupportActionBar(binding.toolBar);
        // Start shimmer loading screen
        binding.shimmerFrame.startShimmer();
        binding.shimmerFrame.setVisibility(View.VISIBLE);

        // Initialization
        initViews();
        viewModel.getAllProducts();

        // Observe the changes from live data
        viewModel.getDatumList().observe(this, data -> {
            updateUi();
            datumList = data;
            adapter = new ProductAdapter(HomeActivity.this, data);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setAdapter(adapter);
        });

        // Click listener on spinner
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    setupVisibility();
                    viewModel.getAllProducts();

                } else if (i == 2) {
                    setupVisibility();
                    viewModel.getMaxPrice();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_grid:
                displayGrid();
                break;

            case R.id.iv_list:
                displayList();
                break;

            case R.id.tv_filter:
                startActivity(new Intent(HomeActivity.this, FilterActivity.class));
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.profile:
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.info:
                Toast.makeText(getApplicationContext(), "Info", Toast.LENGTH_SHORT).show();
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Initialization and assignment for views
     */
    private void initViews() {

        // Setup for view model
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        datumList = new ArrayList<>();

        // Setup for navigation view
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open_drawable, R.string.close_drawable);
        toggle.syncState();
        binding.navView.bringToFront();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Setup for spinner adapter
        initSpinnerAdapter();

        // Click listener on views
        binding.ivGrid.setOnClickListener(this);
        binding.ivList.setOnClickListener(this);
        binding.tvFilter.setOnClickListener(this);
        binding.drawerLayout.addDrawerListener(toggle);
        binding.navView.setNavigationItemSelectedListener(this);
    }


    /***
     * Populate data on screen
     */
    private void updateUi() {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.shimmerFrame.setVisibility(View.GONE);
        layoutManager = new GridLayoutManager(HomeActivity.this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
    }


    /**
     * Display the products in list view
     */
    private void displayList() {
        linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapterList = new ProductAdapterList(HomeActivity.this, datumList);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapterList);
    }


    /**
     * Display the products in grid view
     */
    private void displayGrid() {
        layoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(HomeActivity.this, datumList);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }


    /**
     * Setup adapter for spinner
     */
    private void initSpinnerAdapter() {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(myAdapter);
    }


    /**
     * Show or hide visibility of views
     */
    private void setupVisibility() {
        binding.shimmerFrame.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.INVISIBLE);
    }
}