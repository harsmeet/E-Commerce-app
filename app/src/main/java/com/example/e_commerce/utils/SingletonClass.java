package com.example.e_commerce.utils;

import com.example.e_commerce.data.model.products.LineItem;

import java.io.Serializable;
import java.util.List;

public class SingletonClass implements Serializable {


    private static volatile SingletonClass sSoleInstance;
    private int cartCounter;
    private int billTotal;
    List<LineItem> lineItems;


    //private constructor.
    private SingletonClass() {
        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SingletonClass getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SingletonClass.class) {
                if (sSoleInstance == null) sSoleInstance = new SingletonClass();
            }
        }
        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SingletonClass readResolve() {
        return getInstance();
    }


    /**
     * Getter @ Setter
     */
    public int getCartCounter() {
        return cartCounter;
    }

    public void setCartCounter(int cartCounter) {
        this.cartCounter = cartCounter;
    }


    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public int getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(int billTotal) {
        this.billTotal = billTotal;
    }
}
