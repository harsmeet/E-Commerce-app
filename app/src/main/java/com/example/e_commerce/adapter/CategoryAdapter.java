package com.example.e_commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.databinding.LayoutYouLikeBinding;
import com.example.e_commerce.ui.details.DetailsActivity;
import com.example.e_commerce.utlis.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    /**
     * Initialization
     */
    private List<Datum> datumList;
    private Context context;


    /**
     * Constructor for our MainAdapter
     *
     * @param context   is a current context
     * @param datumList is a datum list
     */
    public CategoryAdapter(Context context, List<Datum> datumList) {
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
        LayoutInflater li = LayoutInflater.from(context);
        return new ViewHolder(LayoutYouLikeBinding.inflate(li));
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
        // Get url of the image
        String url = currentItem.getImages().get(0).getSrc();

        // Display the image by Picasso library
        Picasso.get()
                .load(url)
                .resize(250, 250)
                .into(holder.binding.ivProduct);
        // Displays values on views
        holder.binding.tvProductName.setText(currentItem.getName());
        holder.binding.tvPrice.setText(currentItem.getPrice());

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
        private LayoutYouLikeBinding binding;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * views via binding class
         *
         * @param bindingLayout The View that you inflated in
         *                      {@link CategoryAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        ViewHolder(@NonNull LayoutYouLikeBinding bindingLayout) {
            super(bindingLayout.getRoot());
            binding = bindingLayout;
        }
    }
}