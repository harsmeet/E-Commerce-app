package com.example.e_commerce.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.HomeAdapter;
import com.example.e_commerce.adapter.HomeAdapterList;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.ActivityHomeBinding;
import com.example.e_commerce.ui.auth.SignUpActivity;
import com.example.e_commerce.ui.cart.CartActivity;
import com.example.e_commerce.ui.whishlist.WhishlistActivity;
import com.example.e_commerce.utils.Constants;
import com.example.e_commerce.utils.NetworkChangeReceiver;
import com.example.e_commerce.utils.SingletonClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener,
        HomeListener {


    /**
     * Initialization
     */
    private static final String TAG = HomeActivity.class.getSimpleName();

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
    HomeAdapter adapter;
    HomeAdapterList adapterList;
    GridLayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    List<Datum> datumList;
    Datum datum;

    SingletonClass singletonClass;
    int savedQty;

    BroadcastReceiver br = null;
    IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialization
        initViews();
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
                // Edit visibility
                binding.recyclerView.setVisibility(View.INVISIBLE);
                binding.shimmerFrame.setVisibility(View.VISIBLE);

                if (i == 1) {
                    editor.putString(CATEGORY, ALL_PRODUCTS);
                    viewModel.getAllProducts("");

                } else if (i == 2) {
                    editor.putString(CATEGORY, MEN);
                    viewModel.getCategory("24", "");

                } else if (i == 3) {
                    editor.putString(CATEGORY, WOMEN);
                    viewModel.getCategory("27", "");
                }
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        // Click close listener on search view
        binding.searchView.setOnCloseListener(() -> {
            if (binding.recyclerView.getLayoutManager() == linearLayoutManager) {
                allItems = adapterList.getItemCount();
            } else {
                allItems = adapter.getItemCount();
            }
            binding.tvNumItems.setText(String.valueOf(allItems));
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Check internet connection
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);

        // Update cart counter notification via singleton class.
        // If there's data in singleton.
        if (singletonClass.getCartCounter() >= 1) {
            binding.cardView.setVisibility(View.VISIBLE);
            binding.notificationNum.setText(String.valueOf(singletonClass.getCartCounter()));
            // If there's no data in singleton
        } else {
            binding.notificationNum.setText("");
            binding.cardView.setVisibility(View.INVISIBLE);
        }
        // Save singleton data in shared preferences
        editor.putInt(Constants.QTY, singletonClass.getCartCounter());
        editor.commit();
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu, menu);

        // If there's data saved in shared preference
        if (savedQty >= 1) {
            binding.cardView.setVisibility(View.VISIBLE);
            // Displays qty on cart icon
            binding.notificationNum.setText(String.valueOf(savedQty));
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        // Grid button
        if (id == R.id.ib_grid) {
            displayGrid();
            // List button
        } else if (id == R.id.ib_list) {
            displayList();
        }
        // Cart button
        else if (id == R.id.iv_cart_icon) {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.profile) {
            startActivity(new Intent(HomeActivity.this, SignUpActivity.class));

        } else if (itemId == R.id.info) {
            Toast.makeText(getApplicationContext(), "Info", Toast.LENGTH_SHORT).show();

        } else if (itemId == R.id.wishlist) {
            startActivity(new Intent(HomeActivity.this, WhishlistActivity.class));
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        // Check current layout manager to update items on a specific adapter
        if (binding.recyclerView.getLayoutManager() == linearLayoutManager) {
            adapterList.getFilter().filter(s);
            allItems = adapterList.getItemCount();
        } else {
            adapter.getFilter().filter(s);
            allItems = adapter.getItemCount();
        }
        // Get number for all items shown on screen
        binding.tvNumItems.setText(String.valueOf(allItems));
        return false;
    }


    /**
     * Initialization and assignment for views
     */
    @SuppressLint("CommitPrefEdits")
    private void initViews() {
        // Setup for toolbar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Start shimmer loading screen
        binding.shimmerFrame.startShimmer();
        binding.shimmerFrame.setVisibility(View.VISIBLE);
        // Setup for view model
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        datumList = new ArrayList<>();
        datum = new Datum();

        // Setup for shared preferences
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Setup for navigation view
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open_drawable, R.string.close_drawable);
        toggle.syncState();
        binding.navView.bringToFront();

        // Setup for spinner adapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(myAdapter);
        // init singleton
        singletonClass = SingletonClass.getInstance();
        // Retrieve data saved in shared preference
        savedQty = preferences.getInt(Constants.QTY, 0);
        if (savedQty >= 1) {
            singletonClass.setCartCounter(savedQty);
        }

        // init broad cast receiver and filter
        br = new NetworkChangeReceiver();
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        // Register click listener on views
        binding.ibGrid.setOnClickListener(this);
        binding.ibList.setOnClickListener(this);
        binding.drawerLayout.addDrawerListener(toggle);
        binding.navView.setNavigationItemSelectedListener(this);
        binding.searchView.setOnQueryTextListener(this);
        binding.ivCartIcon.setOnClickListener(this);
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
        binding.ibList.setImageResource(R.drawable.list_selected);
        binding.ibGrid.setImageResource(R.drawable.grid_not_selected);
        // Setup for recycler view
        linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapterList = new HomeAdapterList(HomeActivity.this, datumList, this);
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
        binding.ibGrid.setImageResource(R.drawable.grid_selected);
        binding.ibList.setImageResource(R.drawable.list_not_selected);
        // Setup for recycler view
        layoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(HomeActivity.this, datumList, this);
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
     * Retrieve data from shared preferences
     */
    private void retrieveData() {
        String value = preferences.getString(CATEGORY, "");
        switch (value) {
            case MEN:
                viewModel.getCategory("24", "");
                break;
            case WOMEN:
                viewModel.getCategory("27", "");
                break;
            case ALL_PRODUCTS:
            default:
                viewModel.getAllProducts("");
                break;
        }
    }
}