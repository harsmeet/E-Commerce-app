
package com.example.e_commerce.data.model.register;

import android.text.GetChars;
import android.text.TextUtils;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("is_paying_customer")
    @Expose
    private boolean isPayingCustomer;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("meta_data")
    @Expose
    private List<Object> metaData = null;
    @SerializedName("_links")
    @Expose
    private Links links;


    public int getId() {
        return id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public Billing getBilling() {
        return billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public boolean isPayingCustomer() {
        return isPayingCustomer;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public Links getLinks() {
        return links;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Validate SignUp data
     *
     * @return a specific number for every case
     */
    public int validateData() {
        // Check values is empty or not.
        if (TextUtils.isEmpty(getEmail()) || TextUtils.isEmpty(getUsername()) || TextUtils.isEmpty(getFirstName()) || TextUtils.isEmpty(getLastName())) {
            return 0;
        } else {
            return 1;
        }
    }
}
