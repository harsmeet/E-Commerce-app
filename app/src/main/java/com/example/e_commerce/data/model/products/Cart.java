package com.example.e_commerce.data.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "cart")
public class Cart implements Parcelable {


    /**
     * Initialization
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String price;
    private String image;
    private String category;
    private int quantity;


    public Cart() {
    }

    /**
     * Constructor for parcel implementation
     *
     * @param in is a parcel
     */
    protected Cart(Parcel in) {
        id = in.readInt();
        title = in.readString();
        price = in.readString();
        image = in.readString();
        category = in.readString();
        quantity = in.readInt();
    }


    /**
     * Our creator for parcel implementation
     */
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };


    /**
     * Describe contents for parcel implementation
     *
     * @return integer
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Writing to parcel
     *
     * @param parcel is a parcel object
     * @param i      is integer
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(image);
        parcel.writeString(category);
        parcel.writeInt(quantity);
    }


    /**
     * Getter and Setter
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
