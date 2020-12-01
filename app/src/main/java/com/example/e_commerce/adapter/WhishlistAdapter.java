package com.example.e_commerce.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.ui.details.DetailsActivity;
import com.example.e_commerce.ui.whishlist.WhishlistListener;
import com.example.e_commerce.utils.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;


public class WhishlistAdapter extends RecyclerView.Adapter<WhishlistAdapter.ViewHolder> {


    /**
     * Initialization
     */
    private List<Datum> datumList;
    Context context;
    private AppDatabase mDb;
    WhishlistListener callBack;


    /**
     * Constructor for our FavouriteAdapter
     *
     * @param context is a current context
     */
    public WhishlistAdapter(Context context, WhishlistListener callBack) {
        this.context = context;
        this.callBack = callBack;
    }


    /**
     * Setter for List<Datum>
     *
     * @param datumList is the list of data
     */
    public void setDatumList(List<Datum> datumList) {
        this.datumList = datumList;
        notifyDataSetChanged();
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
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_whishlist, null,
                false);
        // To adjust the size of CardView
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get position of current item
        Datum currentItem = datumList.get(position);

        // Save the name on add to cart button
        mDb = AppDatabase.getInstance(context);
        // Displays values on views
        holder.tvProductName.setText(currentItem.getName());
        holder.tvPrice.setText(currentItem.getPrice());
        String url = currentItem.getImages().get(0).getSrc();
        // Display the image by Picasso library
        Picasso.get()
                .load(url)
                .into(holder.iv_product_Fav);


        // Click listener on add to cart button
        holder.btnAddToCart.setOnClickListener(view -> {
            // Get instance of database
            mDb = AppDatabase.getInstance(context);
            AppExecutors.getInstance().diskIO().execute(() -> {
                // Insert the selected item to the database
                LineItem LineItem = mDb.roomDao().fetchInCart(currentItem.getName());
                // if there's data saved in database.
                if (LineItem != null) {
                    LineItem.setQuantity(1);
                    LineItem.setProduct_id(currentItem.getId());
                    int qty = mDb.roomDao().getSum(LineItem.getQuantity(), LineItem.getProduct_id());
                    LineItem.setQuantity(qty);
                    mDb.roomDao().updateToCart(LineItem);

                    // SnackBar setup
                    Snackbar snackbar =
                            Snackbar.make(holder.constraintLayout, "Item updated", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.CYAN);
                    snackbar.show();

                } else {
                    LineItem = new LineItem();
                    LineItem.setProduct_id(currentItem.getId());
                    LineItem.setName(currentItem.getName());
                    LineItem.setPrice(Integer.parseInt(currentItem.getPrice()));
                    LineItem.setCategory(currentItem.getCategories().get(0).getName());
                    LineItem.setImage(currentItem.getImages().get(0).getSrc());
                    LineItem.setQuantity(1);

                    // Save item to wishlist
                    LineItem finalLineItem = LineItem;
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        // Insert the selected item to the database
                        mDb.roomDao().insertToCart(finalLineItem);
                    });

                    // SnackBar setup
                    Snackbar snackbar =
                            Snackbar.make(holder.constraintLayout, R.string.added_to_cart, Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.CYAN);
                    snackbar.show();
                }
            });
            callBack.updateCartCounter();
        });


        // Click listener on delete button
        holder.ibDelete.setOnClickListener(view -> {
            mDb = AppDatabase.getInstance(context);
            AppExecutors.getInstance().diskIO().execute(() -> {
                // Delete the selected item from the database
                mDb.roomDao().deleteDatum(datumList.get(position));
            });
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
        TextView tvProductName;
        TextView tvPrice;
        Button btnAddToCart;
        ImageView iv_product_Fav;
        ImageButton ibDelete;
        ConstraintLayout constraintLayout;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * views via binding class
         *
         * @param itemView The View that you inflated in
         *                 {@link WhishlistAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Find a reference for the views
            tvProductName = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
            iv_product_Fav = itemView.findViewById(R.id.iv_product_Fav);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }
}