package com.example.e_commerce.data.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.e_commerce.data.model.products.Datum;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    LiveData<List<Datum>> loadAllResults();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Datum datum);

    @Delete
    void deleteItem(Datum datum);
}