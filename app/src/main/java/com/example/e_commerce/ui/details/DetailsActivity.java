

package com.example.e_commerce.ui.details;

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
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.CategoryAdapter;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.ActivityDetailsBinding;
import com.example.e_commerce.ui.cart.CartActivity;
import com.example.e_commerce.utils.Constants;
import com.example.e_commerce.utils.SingletonClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {


//    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String MyPREFERENCES = "PREFERENCE";

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
    private int qty;
    DetailsViewModel viewModel;
    CategoryAdapter adapter;
    LinearLayoutManager layoutManager;
    PhotoViewAttacher photoView;
    private ActivityDetailsBinding binding;
    private List<Datum> datumList;
    SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    SingletonClass singletonClass;


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
        // Pass data to view model by category id
        viewModel.getAllProducts(String.valueOf(categoryId));

        // Observe the changes from live data
        viewModel.getDatumList().observe(this, data -> {
            datumList = data;
            updateYouLike();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (singletonClass.getCartCounter() >= 1) {
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
        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu, menu);

        if (singletonClass.getCartCounter() >= 1) {
            binding.cardView.setVisibility(View.VISIBLE);
            binding.notificationNum.setText(String.valueOf(singletonClass.getCartCounter()));
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        // Get id from view
        int id = view.getId();

        // Add button
        if (id == R.id.btn_add) {
            editQty("add");

            // Sub button
        } else if (id == R.id.btn_subtract) {
            editQty("sub");
            // Add to cart button
        } else if (id == R.id.btn_add_cart) {
            addToCart();
            // Cart button
        } else if (id == R.id.iv_cart_icon) {
            startActivity(new Intent(DetailsActivity.this, CartActivity.class));
        }
    }

    /**
     * Initialize the views
     */
    @SuppressLint("CommitPrefEdits")
    private void initViews() {
        // Custom action bar
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // Setup for view model and data
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        datumList = new ArrayList<>();

        // Setup for shared preferences
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
        // Setup for intent
        Intent intent = getIntent();
        datum = intent.getParcelableExtra(Constants.INTENT_KEY);
        qty = 1;

        // Reference to singleton class
        singletonClass = SingletonClass.getInstance();
        // Reference to photo view attacher
        photoView = new PhotoViewAttacher(binding.ivProduct);
        // Register click listener
        binding.btnAdd.setOnClickListener(this);
        binding.btnSubtract.setOnClickListener(this);
        binding.btnAddCart.setOnClickListener(this);
        binding.ivCartIcon.setOnClickListener(this);
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
        // Setup for recycler view
        layoutManager = new LinearLayoutManager(DetailsActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        adapter = new CategoryAdapter(DetailsActivity.this, datumList);
        binding.loadingIndicator.setVisibility(View.INVISIBLE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }


    /**
     * Decrease or increase qty of products
     */
    private void editQty(String operation) {
        // Add one qty per time
        if (operation.equals("add")) {
            binding.btnSubtract.setEnabled(true);
            qty++;
            binding.tvQty.setText(String.valueOf(qty));
            if (qty == 2) {
                binding.btnSubtract.setBackgroundResource(R.drawable.btn_subtract);
            }

            // Sub one qty per time
        } else {
            if (qty == 1) {
                binding.btnSubtract.setEnabled(false);
            } else {
                qty--;
                binding.tvQty.setText(String.valueOf(qty));
                if (qty == 1) {
                    binding.btnSubtract.setBackgroundResource(R.drawable.btn_subtract_min);
                }
            }
        }
    }


    /**
     * Add to cart and save to database
     */
    private void addToCart() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(datum.getId()));
        map.put("qty", String.valueOf(qty));
        map.put("title", title);
        map.put("price", price);
        map.put("image", productImage);
        map.put("category", category);
        viewModel.getCartData(map);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();

        // Displays counter for cart icon and save it
        binding.cardView.setVisibility(View.VISIBLE);
        int qty = Integer.parseInt(binding.tvQty.getText().toString());
        if (binding.notificationNum.getText().toString().isEmpty()) {
            binding.notificationNum.setText(String.valueOf(qty));
            editor.putInt(Constants.QTY, qty);

            // Pass counter of cart icon to update it in home activity
            singletonClass.setCartCounter(qty);
        } else {
            int qtyInCart = Integer.parseInt(binding.notificationNum.getText().toString());
            binding.notificationNum.setText(String.valueOf(qty + qtyInCart));
            editor.putInt(Constants.QTY, qty + qtyInCart);

            // Pass counter of cart icon to update it in home activity
            singletonClass.setCartCounter(qty + qtyInCart);
        }
        editor.commit();
    }
}