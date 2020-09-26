package com.example.e_commerce.data.database;

import androidx.room.TypeConverter;

import com.example.e_commerce.data.model.products.Category;
import com.example.e_commerce.data.model.products.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {

    // Two converter methods for Image Class
    @TypeConverter
    public static List<Image> fromStringImage(String value) {
        Type listType = new TypeToken<List<Image>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromClassImage(List<Image> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    // Two converter methods for Category Class
    @TypeConverter
    public static List<Category> fromStringCategory(String value) {
        Type listType = new TypeToken<List<Category>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromClassCategory(List<Category> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
