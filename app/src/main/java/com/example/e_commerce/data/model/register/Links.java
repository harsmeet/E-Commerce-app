
package com.example.e_commerce.data.model.register;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    /**
     * Initialization
     */
    @SerializedName("self")
    @Expose
    private List<Self> self = null;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection = null;
}