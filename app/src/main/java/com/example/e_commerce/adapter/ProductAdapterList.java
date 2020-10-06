package com.example.e_commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.ui.details.DetailsActivity;
import com.example.e_commerce.ui.favourite.FavouriteActivity;
import com.example.e_commerce.utlis.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterList extends RecyclerView.Adapter<ProductAdapterList.ViewHolder> {


    /**
     * Initialization
     */
    private List<Datum> datumList;
    private Context context;
    private AppDatabase mDb;


    /**
     * Constructor for our MainAdapter
     *
     * @param context   is a current context
     * @param datumList is a datum list
     */
    public ProductAdapterList(Context context, List<Datum> datumList) {
        this.datumList = datumList;
        this.context = context;
    }


    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The ViewGroup that these ViewHolders are contained within.
     * @param viewType Id for the list item layout
     * @return A new ViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_products_list, null,
                false);
        // To adjust the size of CardView
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is
     * conveniently passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get position of current item
        Datum currentItem = datumList.get(position);

        // Save the state of checkbox when checked
        mDb = AppDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(() -> {
            Datum datum = mDb.favouriteDao().fetchByName(currentItem.getName());
            // if there's data saved in database. set true on checkbox
            if (datum != null) {
                holder.chFavourite.setChecked(true);
            } else {
                holder.chFavourite.setChecked(false);
            }
        });

        // Displays values on views
        holder.tvProductName.setText(currentItem.getName());
        holder.tvPrice.setText(currentItem.getPrice());
        holder.tvCategory.setText(currentItem.getCategories().get(0).getName());
        // Get url of the image
        String url = currentItem.getImages().get(0).getSrc();
        // Display the image by Picasso library
        Picasso.get()
                .load(url)
                .resize(250, 250)
                .into(holder.ivProduct);


        // Click listener on favourite button
        holder.chFavourite.setOnClickListener(view -> {
            if (holder.chFavourite.isChecked()) {
                // Save item to wishlist
                mDb = AppDatabase.getInstance(context);
                AppExecutors.getInstance().diskIO().execute(() -> {
                    // Insert the selected item to the database
                    mDb.favouriteDao().insertItem(datumList.get(position));
                });
                // SnackBar setup
                Snackbar snackbar =
                        Snackbar.make(holder.constraintLayout, R.string.add_wishlist, Snackbar.LENGTH_LONG)
                                .setAction(R.string.see_list, view1 -> {
                                    Intent intent = new Intent(context, FavouriteActivity.class);
                                    context.startActivity(intent);
                                }).setActionTextColor(Color.CYAN);
                snackbar.show();
            } else {
                // Delete item from Wishlist
                mDb = AppDatabase.getInstance(context);
                AppExecutors.getInstance().diskIO().execute(() -> {
                    // Delete the selected item from the database
                    mDb.favouriteDao().deleteItem(datumList.get(position));
                });
                // SnackBar
                Snackbar snackbar =
                        Snackbar.make(holder.constraintLayout, R.string.removed, Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.CYAN);
                snackbar.show();
            }
        });

        // Click listener on each item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.INTENT_KEY, currentItem);
            context.startActivity(intent);
        });
    }


    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our datum
     */
    @Override
    public int getItemCount() {
        return datumList != null ? datumList.size() : 0;
    }


    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialization
        private TextView tvProductName;
        private TextView tvPrice;
        private TextView tvCategory;
        private ImageView ivProduct;
        private CheckBox chFavourite;
        private ConstraintLayout constraintLayout;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * views via binding class
         *
         * @param itemView The View that you inflated in
         *                 {@link ProductAdapterList#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Find a reference for views
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCategory = itemView.findViewById(R.id.tv_category);
            ivProduct = itemView.findViewById(R.id.iv_product);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
            chFavourite = itemView.findViewById(R.id.ch_favourite);
        }
    }
}