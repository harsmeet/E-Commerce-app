package com.example.e_commerce.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.ProductAdapter;
import com.example.e_commerce.databinding.ActivityHomeBinding;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    ActivityHomeBinding binding;
    HomeViewModel viewModel;
    ProductAdapter adapter;
    GridLayoutManager layoutManager;

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
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getAllProducts();

        // Observe the changes on datum list
        viewModel.getDatumList().observe(this, data -> {
            updateUi();
            binding.recyclerView.setVisibility(View.VISIBLE);
            adapter = new ProductAdapter(HomeActivity.this, data);
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setAdapter(adapter);
        });

        // Observe the changes in case of failure response
        viewModel.getError().observe(this, s -> {
            binding.shimmerFrame.setVisibility(View.GONE);
            Toast.makeText(HomeActivity.this, s, Toast.LENGTH_LONG).show();
        });


        // Init Spinner
        initSpinnerAdapter();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

    }


    /***
     * Populate data on screen
     */
    private void updateUi() {
        binding.shimmerFrame.setVisibility(View.GONE);
        layoutManager = new GridLayoutManager(HomeActivity.this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
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