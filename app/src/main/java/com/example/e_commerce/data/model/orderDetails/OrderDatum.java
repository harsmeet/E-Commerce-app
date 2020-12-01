
package com.example.e_commerce.data.model.orderDetails;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.e_commerce.data.model.products.LineItem;

public class OrderDatum {

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
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("shipping_lines")
    @Expose
    private List<Object> shippingLines = null;
    @SerializedName("fee_lines")
    @Expose
    private List<Object> feeLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<Object> couponLines = null;
    @SerializedName("refunds")
    @Expose
    private List<Object> refunds = null;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("_links")
    @Expose
    private Links links;


    public OrderDatum(Billing billing, Shipping shipping, String paymentMethod, List<LineItem> lineItem) {
        this.billing = billing;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.lineItem = lineItem;
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

    public void setLinks(Links links) {
        this.links = links;
    }


    public List<LineItem> getLineItem() {
        return lineItem;
    }


    public void setLineItem(List<LineItem> lineItem) {
        this.lineItem = lineItem;
    }
}