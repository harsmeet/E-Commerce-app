
package com.example.e_commerce.data.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;


    protected Datum(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        price = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public List<Category> getCategories() {
        return categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public List<Image> getImages() {
        return images;
    }


    public String getPrice() {
        return price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(slug);
        parcel.writeString(price);
        parcel.writeTypedList(images);
        parcel.writeTypedList(categories);
    }
}
