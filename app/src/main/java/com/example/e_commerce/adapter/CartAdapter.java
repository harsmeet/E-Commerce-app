package com.example.e_commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.data.database.AppDatabase;
import com.example.e_commerce.data.database.AppExecutors;
import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.databinding.LayoutCartBinding;
import com.example.e_commerce.ui.cart.CartListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    /**
     * Initialization
     */
    private List<LineItem> lineItemList;
    Context context;
    private AppDatabase mDb;
    CartListener callBack;
    int billTotal;
    int total;
    int pricePerItem;
    int index = 0;


    /**
     * Constructor for our CartAdapter
     *
     * @param context is a current context
     */
    public CartAdapter(Context context, CartListener callBack) {
        this.context = context;
        this.callBack = callBack;
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
        return new ViewHolder(LayoutCartBinding.inflate(li));
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
        // Initialize Database
        mDb = AppDatabase.getInstance(context);
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

        // Update bill total in database
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (index >= 1) {
                billTotal = mDb.roomDao().getMulti();
            } else {
                billTotal = mDb.roomDao().getMulti();
                callBack.setValue(String.valueOf(billTotal));
            }
            index++;
        });

        // Calculate total price based on quantity
        int qty = Integer.parseInt(holder.binding.tvQty.getText().toString());
        int itemPrice = currentItem.getPrice();
        int totalPrice = qty * itemPrice;
        // Update total price based on quantity
        holder.binding.tvPrice.setText(String.valueOf(totalPrice));
        // Change background of subtract button
        if (qty >= 2) {
            holder.binding.btnSubtract.setBackgroundResource(R.drawable.btn_subtract);
        }


        // Click listener on Add button
        holder.binding.btnAdd.setOnClickListener(view -> {
            // Activate clickable on subtract button
            holder.binding.btnSubtract.setEnabled(true);
            // Get quantity value
            int currentQty = Integer.parseInt(holder.binding.tvQty.getText().toString());
            // Update quantity value
            currentQty++;
            holder.binding.tvQty.setText(String.valueOf(currentQty));
            // Update qty in database
            currentItem.setQuantity(currentQty);
            AppExecutors.getInstance().diskIO().execute(() -> {
                // Update the selected item into database
                mDb.roomDao().updateToCart(currentItem);
            });
            // Change background of subtract button
            if (currentQty >= 2) {
                holder.binding.btnSubtract.setBackgroundResource(R.drawable.btn_subtract);
            }

            // Get total price and price per item from views
            total = Integer.parseInt(holder.binding.tvPrice.getText().toString());
            pricePerItem = currentItem.getPrice();
            // Update total price of current item
            total += pricePerItem;
            holder.binding.tvPrice.setText(String.valueOf(total));
            // Update bill total of all items
            billTotal += pricePerItem;
            callBack.setValue(String.valueOf(billTotal));
            callBack.calcCounter(1, "add");
        });


        // Click listener on Subtract button
        holder.binding.btnSubtract.setOnClickListener(view -> {
            // Get quantity value
            int currentQty = Integer.parseInt(holder.binding.tvQty.getText().toString());
            // Disable clickable on subtract button
            if (currentQty == 1) {
                holder.binding.btnSubtract.setEnabled(false);

            } else {
                // Update quantity value
                currentQty--;
                holder.binding.tvQty.setText(String.valueOf(currentQty));
                // Update qty in database
                currentItem.setQuantity(currentQty);
                AppExecutors.getInstance().diskIO().execute(() -> {
                    // Update the selected item into database
                    mDb.roomDao().updateToCart(currentItem);
                });

                // Change background of subtract button
                if (currentQty == 1) {
                    holder.binding.btnSubtract.setBackgroundResource(R.drawable.btn_subtract_min);
                }
                // Get total price and price per item
                total = Integer.parseInt(holder.binding.tvPrice.getText().toString());
                pricePerItem = currentItem.getPrice();
                // Update total price of current item
                total -= pricePerItem;
                holder.binding.tvPrice.setText(String.valueOf(total));
                // Update bill total of all items
                billTotal -= pricePerItem;
                callBack.setValue(String.valueOf(billTotal));
                callBack.calcCounter(1, "sub");
            }
        });


        // Click listener on Delete button
        holder.binding.ibDelete.setOnClickListener(view -> {
            // Calculate discount percentage from bill total
            int price = currentItem.getPrice();
            int total = price * currentItem.getQuantity();

            // Get instance from Executors
            AppExecutors.getInstance().diskIO().execute(() -> {
                // Get total bill from database
                billTotal = mDb.roomDao().getMulti();
                // Delete the selected item from the database
                mDb.roomDao().deleteCart(lineItemList.get(position));
            });
            callBack.setValue(String.valueOf(billTotal - total));
            callBack.calcCounter(currentItem.getQuantity(), "sub");
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
        return lineItemList != null ? lineItemList.size() : 0;
    }


    /**
     * Cache of the children views for a list item.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialization
        LayoutCartBinding binding;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * views via binding class
         *
         * @param bindingLayout The View that you inflated in
         *                      {@link CartAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public ViewHolder(@NonNull LayoutCartBinding bindingLayout) {
            super(bindingLayout.getRoot());
            binding = bindingLayout;
        }
    }
}