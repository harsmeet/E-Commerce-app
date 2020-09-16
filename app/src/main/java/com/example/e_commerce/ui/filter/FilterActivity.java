package com.example.e_commerce.ui.filter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityFilterBinding;
import com.example.e_commerce.ui.home.HomeActivity;

import java.util.Objects;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = FilterActivity.class.getSimpleName();
    ActivityFilterBinding binding;
    private static final int MAX_PRICE_110 = 110;
    private static final int MAX_PRICE_150 = 150;
    private static final int MAX_PRICE_200 = 200;

    int maxPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize binding
        binding = com.example.e_commerce.databinding.ActivityFilterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // Custom action bar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.ch110.setOnClickListener(this);
        binding.ch150.setOnClickListener(this);
        binding.ch200.setOnClickListener(this);
        binding.btnViewResults.setOnClickListener(this);


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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ch_110:
                if (binding.ch110.isChecked()) {
                    maxPrice = MAX_PRICE_110;
                }
                break;
            case R.id.ch_150:
                if (binding.ch150.isChecked()) {
                    maxPrice = MAX_PRICE_150;
                }
                break;
            case R.id.ch_200:
                if (binding.ch200.isChecked()) {
                    maxPrice = MAX_PRICE_200;
                }
                break;
            case R.id.btn_view_results:
                passPriceFilter();
                break;
        }
    }


    private void passPriceFilter() {
        Toast.makeText(this, "Demo", Toast.LENGTH_SHORT).show();
    }
}