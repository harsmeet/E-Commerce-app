package com.example.e_commerce.data.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class LineItem implements Parcelable {


    /**
     * Initialization
     */
    @PrimaryKey(autoGenerate = true)
    private int product_id;
    private String name;
    private int price;
    private String image;
    private String category;
    private int quantity;


    /**
     * Default constructor
     */
    public LineItem() {
    }


    /**
     * Constructor for parcel implementation
     *
     * @param in is a parcel
     */
    protected LineItem(Parcel in) {
        product_id = in.readInt();
        name = in.readString();
        price = in.readInt();
        image = in.readString();
        category = in.readString();
        quantity = in.readInt();
    }


    /**
     * Our creator for parcel implementation
     */
    public static final Creator<LineItem> CREATOR = new Creator<LineItem>() {
        @Override
        public LineItem createFromParcel(Parcel in) {
            return new LineItem(in);
        }

        @Override
        public LineItem[] newArray(int size) {
            return new LineItem[size];
        }
    };


    /**
     * Getter and Setter
     */

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
        parcel.writeInt(product_id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeString(image);
        parcel.writeString(category);
        parcel.writeInt(quantity);
    }
}
