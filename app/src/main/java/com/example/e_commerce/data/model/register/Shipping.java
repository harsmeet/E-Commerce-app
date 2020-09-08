
package com.example.e_commerce.data.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("address_1")
    @Expose
    public String address1;
    @SerializedName("address_2")
    @Expose
    public String address2;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("postcode")
    @Expose
    public String postcode;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("state")
    @Expose
    public String state;

}
