
package com.example.e_commerce.data.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "favourites")
public class Datum implements Parcelable {


    /**
     * Initialization
     */
    @PrimaryKey
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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;



    /**
     * Default constructor
     */
    public Datum() {
    }


    /**
     * Constructor for parcel implementation
     *
     * @param in is a parcel
     */
    protected Datum(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        price = in.readString();
        description = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        categories = in.createTypedArrayList(Category.CREATOR);
    }


    /**
     * Our creator for parcel implementation
     */
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
        parcel.writeString(slug);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeTypedList(images);
        parcel.writeTypedList(categories);
    }


    /**
     * Getter and Setter
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

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

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }
}