
package com.example.e_commerce.data.model.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingMethod {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_links")
    @Expose
    private Links links;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
