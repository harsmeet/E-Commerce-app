package com.example.e_commerce.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.e_commerce.databinding.LayoutProductsBinding;
import com.example.e_commerce.ui.auth.SignUpActivity;
import com.example.e_commerce.ui.favourite.FavouriteActivity;
import com.example.e_commerce.ui.filter.FilterActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    /**
     * Initialization
     */
    private static final String MyPREFERENCES = "PREFERENCE";
    private static final String CATEGORY = "CATEGORY";
    private static final String ALL_PRODUCTS = "ALL_PRODUCTS";
    private static final String MEN = "MEN";
    private static final String WOMEN = "WOMEN";
    private static final String GRID = "GRID";
    private static final String LIST = "LIST";
    private static final String VIEW_TYPE = "VIEW_TYPE";

    private ActionBarDrawerToggle toggle;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int allItems;

    ActivityHomeBinding binding;
    HomeViewModel viewModel;
    ProductAdapter adapter;
    ProductAdapterList adapterList;
    GridLayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    List<Datum> datumList;
    Datum datum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialization
        initViews();

        // Start shimmer loading screen
        binding.shimmerFrame.startShimmer();
        binding.shimmerFrame.setVisibility(View.VISIBLE);


        // Retrieve data
        retrieveData();

        // Observe the changes from live data
        viewModel.getDatumList().observe(this, data -> {
            datumList = data;
            updateUi();
        });


        // Click listener on spinner
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    setupVisibility();
                    editor.putString(CATEGORY, ALL_PRODUCTS);
                    viewModel.getAllProducts();

                } else if (i == 2) {
                    setupVisibility();
                    editor.putString(CATEGORY, MEN);
                    viewModel.getCategory("24");

                } else if (i == 3) {
                    setupVisibility();
                    editor.putString(CATEGORY, WOMEN);
                    viewModel.getCategory("27");
                }
                editor.commit();
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
                startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
                break;

            case R.id.info:
                Toast.makeText(getApplicationContext(), "Info", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wishlist:
                startActivity(new Intent(HomeActivity.this, FavouriteActivity.class));
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Initialization and assignment for views
     */
    private void initViews() {
        // Setup for toolbar
        setSupportActionBar(binding.toolBar);
        // Setup for view model
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        datumList = new ArrayList<>();
        datum = new Datum();

        // Setup for shared preferences and Room Database
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Setup for navigation view
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open_drawable, R.string.close_drawable);
        toggle.syncState();
        binding.navView.bringToFront();
        // Custom action bar
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
        String view = preferences.getString(VIEW_TYPE, "");

        if (view.equals(LIST)) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.shimmerFrame.setVisibility(View.GONE);
            displayList();

        } else if (view.equals(GRID)) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.shimmerFrame.setVisibility(View.GONE);
            displayGrid();

        } else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.shimmerFrame.setVisibility(View.GONE);
            displayGrid();
        }
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

        // Get number for all items shown on screen
        allItems = adapterList.getItemCount();
        binding.tvNumItems.setText(String.valueOf(allItems));

        // Save in shared preferences
        editor.putString(VIEW_TYPE, LIST);
        editor.commit();
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

        // Get number for all items shown on screen
        allItems = adapter.getItemCount();
        binding.tvNumItems.setText(String.valueOf(allItems));

        // Save in shared preferences
        editor.putString(VIEW_TYPE, GRID);
        editor.commit();
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


    /**
     * Retrieve data from shared preferences
     */
    private void retrieveData() {
        String value = preferences.getString(CATEGORY, "");
        switch (value) {
            case MEN:
                viewModel.getCategory("24");
                break;
            case WOMEN:
                viewModel.getCategory("27");
                break;
            case ALL_PRODUCTS:
            default:
                viewModel.getAllProducts();
                break;
        }
    }
}