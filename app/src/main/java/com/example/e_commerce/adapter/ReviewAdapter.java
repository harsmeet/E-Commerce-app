package com.example.e_commerce.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.databinding.LayoutReviewBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    /**
     * Initialization
     */
    private List<LineItem> lineItemList;
    Context context;


    /**
     * Constructor for our CartAdapter
     *
     * @param context is a current context
     */
    public ReviewAdapter(Context context) {
        this.context = context;
    }


    /**
     * Setter for List<Cart>
     *
     * @param lineItemList is the list of data
     */
    public void setLineItemList(List<LineItem> lineItemList) {
        this.lineItemList = lineItemList;
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
        LayoutInflater li = LayoutInflater.from(context);
        return new ViewHolder(LayoutReviewBinding.inflate(li));
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
        LineItem currentItem = lineItemList.get(position);
        // Displays values on views
        holder.binding.tvTitle.setText(currentItem.getName());
        holder.binding.tvPrice.setText(String.valueOf(currentItem.getPrice()));
        holder.binding.tvCategoryCart.setText(currentItem.getCategory());
        holder.binding.tvQty.setText(String.valueOf(currentItem.getQuantity()));
        // Get url of the image
        String url = currentItem.getImage();
        // Display the image by Picasso library
        Picasso.get()
                .load(url)
                .into(holder.binding.ivProduct);
    }


    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our datum
     */
    @Override
    public int getItemCount() {
        return lineItemList != null ? lineItemList.size() : 0;
    }


    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialization
        LayoutReviewBinding binding;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * views via binding class
         *
         * @param bindingLayout The View that you inflated in
         *                      {@link ReviewAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public ViewHolder(@NonNull LayoutReviewBinding bindingLayout) {
            super(bindingLayout.getRoot());
            binding = bindingLayout;
        }
    }
}
