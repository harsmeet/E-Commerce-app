
package com.example.e_commerce.data.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {


    /**
     * Initialization
     */
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alt")
    @Expose
    String alt;


    /**
     * Constructor for parcel implementation
     *
     * @param in is a parcel
     */
    protected Image(Parcel in) {
        id = in.readInt();
        src = in.readString();
        name = in.readString();
        alt = in.readString();
    }


    /**
     * Our creator for parcel implementation
     */
    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
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
        parcel.writeString(src);
        parcel.writeString(name);
        parcel.writeString(alt);
    }


    /**
     * Getter and Setter
     */
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}