package com.example.e_commerce.utlis;

import java.io.Serializable;

public class SingletonClass implements Serializable {


    private static volatile SingletonClass sSoleInstance;
    private int cartCounter;


    //private constructor.
    private SingletonClass(){

        //Prevent form the reflection api.
        if (sSoleInstance != null){
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

    public int getCartCounter() {
        return cartCounter;
    }

    public void setCartCounter(int cartCounter) {
        this.cartCounter = cartCounter;
    }
}
