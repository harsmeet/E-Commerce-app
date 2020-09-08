package com.example.e_commerce.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.ActivityDetailsBinding;
import com.example.e_commerce.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {


    Datum datum;
    String productImage;
    String title;
    String category;
    String price;
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Custom action bar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Setup for intent
        Intent intent = getIntent();
        datum = intent.getParcelableExtra(Constants.INTENT_KEY);

        // Store coming data by intent
        assert datum != null;
        productImage = datum.getImages().get(0).getSrc();
        title = datum.getName();
        category = datum.getCategories().get(0).getName();
        price = datum.getPrice();

        // Update the views on screen
        Picasso.get()
                .load(productImage)
                .into(binding.ivProduct);
        binding.tvTitle.setText(title);
        binding.tvCategory.setText(category);
        binding.tvPrice.setText(price);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}