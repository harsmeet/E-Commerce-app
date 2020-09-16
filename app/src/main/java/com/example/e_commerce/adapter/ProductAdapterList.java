package com.example.e_commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.ui.details.DetailsActivity;
import com.example.e_commerce.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterList extends RecyclerView.Adapter<ProductAdapterList.ViewHolder> {


    private List<Datum> datumList;
    Context context;

    // Constructor for our MainAdapter
    public ProductAdapterList(Context context, List<Datum> datumList) {
        this.datumList = datumList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_products_list, null, false);
        // To adjust the size of CardView
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum currentItem = datumList.get(position);
        holder.tvProductName.setText(currentItem.getName());
        holder.tvPrice.setText(currentItem.getPrice());


        if (currentItem.getCategories().size() == 0) {
            holder.tvCategory.setText("Unnamed");
        } else {
            holder.tvCategory.setText(currentItem.getCategories().get(0).getName());

        }


        if (currentItem.getImages().size() == 0) {
            holder.ivProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.nophoto));
        } else {
            String url = currentItem.getImages().get(0).getSrc();
            // Display the image by Picasso library
            Picasso.get()
                    .load(url)
                    .resize(250, 250)
                    .into(holder.ivProduct);

        }


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

        private TextView tvProductName;
        private TextView tvPrice;
        private TextView tvCategory;
        private ImageView ivProduct;

        ViewHolder(View itemView) {
            super(itemView);
            // Find a reference for the views
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCategory = itemView.findViewById(R.id.tv_category);
            ivProduct = itemView.findViewById(R.id.iv_product);
        }
    }
}
