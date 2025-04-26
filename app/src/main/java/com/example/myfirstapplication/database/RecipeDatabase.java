package com.example.myfirstapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myfirstapplication.model.RecipeModel;

@Database(entities = {RecipeModel.class},version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();

}
