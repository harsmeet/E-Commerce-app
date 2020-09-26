
package com.example.e_commerce.data.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {


    /**
     * Initialization
     */
    @SerializedName("href")
    @Expose
    private String href;


    /**
     * Getter and Setter
     *
     * @return values for setter methods
     */
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
