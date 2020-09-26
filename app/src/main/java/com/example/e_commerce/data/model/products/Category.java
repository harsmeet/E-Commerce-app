
package com.example.e_commerce.data.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {


    /**
     * Initialization
     */
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;


    /**
     * Constructor for our class
     *
     * @param in is a parcel
     */
    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }


    /**
     * Our creator for parcel implementation
     */
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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
        parcel.writeString(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}