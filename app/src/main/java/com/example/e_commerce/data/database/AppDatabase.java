package com.example.e_commerce.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.e_commerce.data.model.products.Datum;
import com.example.e_commerce.data.model.products.LineItem;


@Database(entities = {Datum.class, LineItem.class}, version = 5, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "PRODUCTS";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RoomDao roomDao();
}
