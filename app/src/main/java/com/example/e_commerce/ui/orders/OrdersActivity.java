package com.example.e_commerce.ui.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.e_commerce.R;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.ui.orders.shipping.ShippingFragment;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Setup for fragment transaction
        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.add(R.id.fragment_container, new ShippingFragment());
        fr.commit();
    }
}