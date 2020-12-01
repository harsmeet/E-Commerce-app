package com.example.e_commerce.data.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_commerce.data.model.products.LineItem;
import com.example.e_commerce.data.model.products.Datum;

import java.util.List;


@Dao
public interface RoomDao {

    @Query("SELECT * FROM favourites")
    LiveData<List<Datum>> loadAllResults();

    @Query("SELECT * FROM favourites where name = :name")
    Datum fetchInDatum(String name);

    @Query("SELECT * FROM cart where name = :title")
    LineItem fetchInCart(String title);

    @Query("SELECT * FROM cart")
    LiveData<List<LineItem>> loadAllCart();

    @Query("SELECT Sum (quantity * price) FROM cart")
    int getMulti();

    @Query("SELECT Sum (quantity + :qty) FROM cart WHERE product_id = :id")
    int getSum(int qty, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Datum datum);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToCart(LineItem lineItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateToCart(LineItem lineItems);

    @Delete
    void deleteDatum(Datum datum);

    @Delete
    void deleteCart(LineItem lineItems);
}