

package com.example.e_commerce.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CategoryAdapter;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.ActivityDetailsBinding;
import com.example.e_commerce.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailsActivity extends AppCompatActivity {


    /**
     * Initialization
     */
    Datum datum;
    String productImage;
    String title;
    String category;
    String price;
    String caption;
    int categoryId;
    DetailsViewModel viewModel;
    CategoryAdapter adapter;
    LinearLayoutManager layoutManager;
    PhotoViewAttacher photoView;
    private ActivityDetailsBinding binding;
    private List<Datum> datumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize views
        initViews();
        // Update ui
        updateUI();
        viewModel.getAllProducts(String.valueOf(categoryId));

        // Observe the changes from live data
        viewModel.getDatumList().observe(this, data -> {
            datumList = data;
            updateYouLike();
        });
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
        // Setup for intent
        Intent intent = getIntent();
        datum = intent.getParcelableExtra(Constants.INTENT_KEY);

        // Double click to zoom on the photo
        photoView = new PhotoViewAttacher(binding.ivProduct);
        // Setup for view model and data
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        datumList = new ArrayList<>();
    }


    /**
     * Update the views on screen
     */
    private void updateUI() {
        // Store coming data by intent
        assert datum != null;
        productImage = datum.getImages().get(0).getSrc();
        title = datum.getName();
        category = datum.getCategories().get(0).getName();
        price = datum.getPrice();
        caption = datum.getDescription();
        categoryId = datum.getCategories().get(0).getId();

        // Display views
        Picasso.get()
                .load(productImage)
                .into(binding.ivProduct);
        binding.tvTitle.setText(title);
        binding.tvCategory.setText(category);
        binding.tvPrice.setText(price);
        binding.tvProductCaption.setText(caption);
        // Double click to zoom on photo
        photoView.update();
    }


    /**
     * Update you may also like slider via live data
     */
    private void updateYouLike() {
        binding.loadingIndicator.setVisibility(View.INVISIBLE);
        layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(DetailsActivity.this, datumList);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }
}