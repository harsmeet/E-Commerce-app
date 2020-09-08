
package com.example.e_commerce.data.model.register;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    public List<Self> self = null;
    @SerializedName("collection")
    @Expose
    public List<Collection> collection = null;

}
