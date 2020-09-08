package com.example.e_commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.LayoutProductsBinding;
import com.example.e_commerce.ui.details.DetailsActivity;
import com.example.e_commerce.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Datum> datumList;
    Context context;

    // Constructor for our MainAdapter
    public ProductAdapter(Context context, List<Datum> datumList) {
        this.datumList = datumList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        return new ViewHolder(LayoutProductsBinding.inflate(li));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum currentItem = datumList.get(position);
        holder.binding.tvProductName.setText(currentItem.getName());
        holder.binding.tvPrice.setText(currentItem.getPrice());
        holder.binding.tvCategory.setText(currentItem.getCategories().get(0).getName());

        String url = currentItem.getImages().get(0).getSrc();
        // Display the image by Picasso library
        Picasso.get()
                .load(url)
                .resize(250,250)
                .into(holder.binding.ivProduct);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.INTENT_KEY, currentItem);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return datumList != null ? datumList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutProductsBinding binding;

        ViewHolder(@NonNull LayoutProductsBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}