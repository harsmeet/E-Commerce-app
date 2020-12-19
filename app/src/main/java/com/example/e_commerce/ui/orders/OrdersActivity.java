package com.example.e_commerce.ui.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.e_commerce.R;
import com.example.e_commerce.ui.orders.shipping.ShippingFragment;


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