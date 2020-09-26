
package com.example.e_commerce.data.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Billing {


    /**
     * Initialization
     */
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("address_2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
}