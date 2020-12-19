package com.example.e_commerce.ui.orders.review;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.ReviewAdapter;
import com.example.e_commerce.data.model.order.OrderDatum;
import com.example.e_commerce.databinding.FragmentReviewBinding;
import com.example.e_commerce.ui.orders.finish.FinishActivity;
import com.example.e_commerce.utils.SingletonClass;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment implements View.OnClickListener {


    /**
     * Initialization
     */
    private FragmentReviewBinding binding;
    Bundle bundle;
    private OrderDatum orderDatum;
    ReviewAdapter adapter;
    LinearLayoutManager layoutManager;
    private SingletonClass singletonClass;
    int orderNumber;
    String email;


    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        initViews();
        updateUi();

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_to_finish) {
            Intent intent = new Intent(getContext(), FinishActivity.class);
            intent.putExtra("order_number", orderNumber);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }


    /**
     * Assignment views
     */
    private void initViews() {
        bundle = this.getArguments();
        orderDatum = Objects.requireNonNull(bundle).getParcelable("order_data");
        singletonClass = SingletonClass.getInstance();
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ReviewAdapter(getActivity());
        binding.btnToFinish.setOnClickListener(this);
    }


    /**
     * Displays bill and shipping data
     */
    private void updateUi() {
        String firstName = orderDatum.getBilling().getFirstName();
        String lastName = orderDatum.getBilling().getLastName();
        email = orderDatum.getBilling().getEmail();
        orderNumber = orderDatum.getId();
        binding.tvEmail.setText(email);
        binding.tvName.setText(String.format("%s %s", firstName, lastName));
        binding.tvCity.setText(orderDatum.getBilling().getCity());
        binding.tvAddress.setText(orderDatum.getBilling().getAddress1());
        binding.tvZipCode.setText(orderDatum.getBilling().getPostcode());
        binding.tvPhone.setText(orderDatum.getBilling().getPhone());
        binding.tvPaymentMethod.setText(orderDatum.getPaymentMethod());
        binding.tvSubTotal.setText(orderDatum.getTotal());
        binding.tvTaxes.setText(orderDatum.getTotalTax());
        binding.tvTotal.setText(orderDatum.getTotal());
        // Setup for recycler view
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        adapter.setLineItemList(singletonClass.getLineItems());
    }
}