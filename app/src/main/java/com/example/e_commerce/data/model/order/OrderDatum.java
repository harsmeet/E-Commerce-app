
package com.example.e_commerce.data.model.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.e_commerce.data.model.products.LineItem;

public class OrderDatum implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("discount_total")
    @Expose
    private String discountTotal;
    @SerializedName("discount_tax")
    @Expose
    private String discountTax;
    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;
    @SerializedName("shipping_tax")
    @Expose
    private String shippingTax;
    @SerializedName("cart_tax")
    @Expose
    private String cartTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("prices_include_tax")
    @Expose
    private boolean pricesIncludeTax;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("customer_ip_address")
    @Expose
    private String customerIpAddress;
    @SerializedName("customer_user_agent")
    @Expose
    private String customerUserAgent;
    @SerializedName("customer_note")
    @Expose
    private String customerNote;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItem;
    @SerializedName("tax_lines")
    @Expose
    private List<Object> taxLines = null;
    @SerializedName("fee_lines")
    @Expose
    private List<Object> feeLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<Object> couponLines = null;
    @SerializedName("refunds")
    @Expose
    private List<Object> refunds = null;
    @SerializedName("cart_hash")
    @Expose
    private String cartHash;


    public OrderDatum(Billing billing, Shipping shipping, String paymentMethod,
                      List<LineItem> lineItem, String cartHash) {
        this.billing = billing;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.lineItem = lineItem;
        this.cartHash = cartHash;
    }


    protected OrderDatum(Parcel in) {
        id = in.readInt();
        parentId = in.readInt();
        number = in.readString();
        version = in.readString();
        currency = in.readString();
        dateModifiedGmt = in.readString();
        discountTotal = in.readString();
        discountTax = in.readString();
        shippingTotal = in.readString();
        shippingTax = in.readString();
        cartTax = in.readString();
        total = in.readString();
        totalTax = in.readString();
        pricesIncludeTax = in.readByte() != 0;
        customerId = in.readInt();
        customerIpAddress = in.readString();
        customerUserAgent = in.readString();
        customerNote = in.readString();
        paymentMethod = in.readString();
        paymentMethodTitle = in.readString();
        lineItem = in.createTypedArrayList(LineItem.CREATOR);
        cartHash = in.readString();
    }

    public static final Creator<OrderDatum> CREATOR = new Creator<OrderDatum>() {
        @Override
        public OrderDatum createFromParcel(Parcel in) {
            return new OrderDatum(in);
        }

        @Override
        public OrderDatum[] newArray(int size) {
            return new OrderDatum[size];
        }
    };


    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }



    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }


    public List<LineItem> getLineItem() {
        return lineItem;
    }


    public void setLineItem(List<LineItem> lineItem) {
        this.lineItem = lineItem;
    }


    public String getCartHash() {
        return cartHash;
    }

    public void setCartHash(String cartHash) {
        this.cartHash = cartHash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(parentId);
        dest.writeString(number);
        dest.writeString(version);
        dest.writeString(currency);
        dest.writeString(dateModifiedGmt);
        dest.writeString(discountTotal);
        dest.writeString(discountTax);
        dest.writeString(shippingTotal);
        dest.writeString(shippingTax);
        dest.writeString(cartTax);
        dest.writeString(total);
        dest.writeString(totalTax);
        dest.writeByte((byte) (pricesIncludeTax ? 1 : 0));
        dest.writeInt(customerId);
        dest.writeString(customerIpAddress);
        dest.writeString(customerUserAgent);
        dest.writeString(customerNote);
        dest.writeString(paymentMethod);
        dest.writeString(paymentMethodTitle);
        dest.writeTypedList(lineItem);
        dest.writeString(cartHash);
    }
}